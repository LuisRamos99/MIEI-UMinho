var express = require('express');
var router = express.Router();
var jwt = require('jsonwebtoken')
var gdb = require("../utils/graphdb");
const { v4: uuidv4 } = require('uuid');


function verifyToken(token){  
  var t = null;
  jwt.verify(token,'PRC2021',function(e,decoded){
    if(e){
      t = null
    }
    else return t = decoded
  })

  return t
}


router.get('/', async function(req, res, next) {
    var query = `select ?img ?r ?s ?d ?da ?t ?tr ?a ?n where {  ?s rdf:type :Publicacao.
        ?s :RelativaA ?r.
        ?s :descricao ?d.
        ?s :data ?da.
   		?s :titulo ?t.
        ?r :titulo ?tr.
        ?r :imgPath ?img.
   		?s :CriadaPor ?a.
        ?a :nome ?n.
       
       `
           
    if(req.query.tituloP){
        query+=`\nFILTER regex (str(?t), "${req.query.tituloP}", "i").`
    }
    if(req.query.tituloR){
        query+=`\nFILTER regex (str(?tr), "${req.query.tituloR}", "i").`
    }
    query+="}"

    var result =await gdb.execQuery(query)

    var arr = []
    
    result.results.bindings.forEach(a => {
        var obj = {
            "pub_id": a.s.value.split('#')[1],
            "descricao": a.d.value,
            "titulo": a.t.value,
            "data": a.da.value,
            "autor_id": a.a.value.split('#')[1],
            "autor": a.n.value,
            "titulo_receita": a.tr.value,
            "imgPath": a.img.value,
            "rec_id": a.r.value.split('#')[1]
        }      
        arr.push(obj)
    });   
    res.status(200).jsonp({publis:arr})  
    
});

router.get('/receita/:id', async function(req, res, next) {
    var query = `select ?img ?s ?d ?da ?t ?tr ?a ?n where {  ?s rdf:type :Publicacao.
        ?s :RelativaA :${req.params.id}.
        ?s :descricao ?d.
        ?s :data ?da.
   		?s :titulo ?t.
        :${req.params.id}  :titulo ?tr.
        :${req.params.id}  :imgPath ?img.
   		?s :CriadaPor ?a.
        ?a :nome ?n.
       }
       `

    var result =await gdb.execQuery(query)

    var arr = []
    if(result.results.bindings[0]){
        result.results.bindings.forEach(a => {
            var obj = {
                "pub_id": a.s.value.split('#')[1],
                "descricao": a.d.value,
                "titulo": a.t.value,
                "data": a.da.value,
                "titulo_receita": a.tr.value,
                "autor_id": a.a.value.split('#')[1],
                "autor": a.n.value,
                "imgPath": a.img.value,
                "rec_id": req.params.id
            }      
            arr.push(obj)
        });   
        res.status(200).jsonp({publis:arr})
    }else{
        res.status(404).jsonp({message:"Não existem publicações!"})

    }
});


router.get('/recentes', async function(req, res, next) {
    var query = `select ?img ?r ?s ?d ?da ?t ?tr ?a ?n where {  ?s rdf:type :Publicacao.
        ?s :RelativaA ?r.
        ?r :titulo ?tr.
        ?r :imgPath ?img.
        ?s :descricao ?d.
        ?s :data ?da.
   		?s :titulo ?t.
   		?s :CriadaPor ?a.
        ?a :nome ?n.
        }
        order by desc(?da)
        limit 3
       
       ` 
    var result =await gdb.execQuery(query)
   
    var arr = []
    if(result.results.bindings[0]){
        result.results.bindings.forEach(a => {
            var obj = {
                "pub_id": a.s.value.split('#')[1],
                "descricao": a.d.value,
                "titulo": a.t.value,
                "data": a.da.value,
                "titulo_receita": a.tr.value,
                "rec_id": a.r.value.split('#')[1],
                "autor_id": a.a.value.split('#')[1],
                "autor": a.n.value,
                "imgPath": a.img.value
            }      
            arr.push(obj)
        });   
        res.status(200).jsonp({publis:arr})
    }else{
        res.status(404).jsonp({message:"Não existem publicações!"})

    }
});

router.post('/remover', async function(req, res, next) {
    var token = verifyToken(req.headers.authorization)
   
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{
        var query = `DELETE where {  :${req.body.idPub} rdf:type :Publicacao.
            :${req.body.idPub} rdf:type owl:NamedIndividual.
            :${req.body.idPub} :CriadaPor ?a.
            ?a :Criou :${req.body.idPub}.
            :${req.body.idPub} :RelativaA ?r.
            :${req.body.idPub} :data ?da.
            :${req.body.idPub} :descricao ?d.
            :${req.body.idPub} :titulo ?tr.
            }       
           ` 
        try {
            await gdb.execTransaction(query)
            res.status(200).jsonp({message:"Publicação removida com sucesso! "})
        } catch (error) {
            res.status(500).jsonp({message:"Erro na remoção da publicação! "+ error})
        }
    }
});



router.post('/', async function(req, res, next) {
    var token = verifyToken(req.headers.authorization)
   
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{

        var pub_id = uuidv4()
        
        var query = `INSERT DATA
        { 
               :${pub_id} rdf:type :Publicacao, owl:NamedIndividual;
                        :titulo "${req.body.titulo}" ;
                        :data "${req.body.data}" ;
                        :descricao "${req.body.descricao}".
                       
        }`
        var queryRel = `INSERT 
        {
            :${pub_id} :CriadaPor ?p.
            ?p :Criou  :${pub_id}.
            :${pub_id} :RelativaA ?r.
          

        } 
        where{
            ?p rdf:type :Utilizador .
            FILTER regex (str(?p), "${req.body.idUser}").
            ?r rdf:type :Receita .
            FILTER regex (str(?r), "${req.body.idReceita}").
        }`

        try {
            await gdb.execTransaction(query)

            await gdb.execTransaction(queryRel)

            
            res.status(201).jsonp({message:"Publicação registada com sucesso!", idPub: pub_id})
        } catch (error) {
            res.status(500).jsonp({message:"Erro no registo da receita! "+ error})
        }
    }

});



module.exports = router;
