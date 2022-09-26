const { v4: uuidv4 } = require('uuid');
var express = require('express');
var router = express.Router();
var gdb = require("../utils/graphdb");
var jwt = require('jsonwebtoken')

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
    var query = `select ?img ?s ?d ?da ?di ?tc ?tp ?n (GROUP_CONCAT(distinct ?ig;SEPARATOR="&") AS ?igs) ?t (GROUP_CONCAT(distinct ?g;SEPARATOR="&") AS ?gs) where { 
        ?s rdf:type :Receita.
        ?s :descricao ?d.
        ?s :data ?da.
        ?s :dificuldade ?di.
        ?s :ingrediente ?ig.
   		?s :titulo ?t.
        ?s :tipoCozinha ?tc.
        ?s :tipoPrato ?tp.
        ?s :CriadaPor ?a.
        ?a :nome ?n.
        ?s :imgPath ?img.
        OPTIONAL {?s :éGostadoPor ?g.}`
           
    if(req.query.titulo){
        query+=`\n?s :titulo ?r.
        FILTER regex (str(?r), "${req.query.titulo}", "i").`
    }
    if(req.query.tipoCozinha){
        query+="\n?s :tipoCozinha \""+req.query.tipoCozinha+"\"."
    }
    if(req.query.tipoPrato){
        query+="\n?s :tipoPrato \""+req.query.tipoPrato+"\"."
    } 
    if(req.query.ingrediente){
        query+=`\n?s :ingrediente ?i.
        FILTER regex (str(?i), "${req.query.ingrediente}", "i").`
    }

    if(req.query.autor){
        while(req.query.autor.includes('_')) {
            req.query.autor = req.query.autor.replace('_',' ')
        }
        query+=`\n?s :CriadaPor ?c.
    	?c :nome "${req.query.autor}".`
    }
    query+="} group by ?s ?d ?da ?di ?t ?tc ?tp ?n ?img"

    var result =await gdb.execQuery(query)

    var arr = []
    result.results.bindings.forEach(a => {
        var ing=[]
        if(a.igs.value){
            a.igs.value.split('&').forEach(i=>{
                ing.push(i)
            })
        }
        var gostos = []
        if(a.gs.value){
            a.gs.value.split('&').forEach(g=>{
               gostos.push(g.split('#')[1])
            })
        }
        var obj = {
            "rec_id": a.s.value.split('#')[1],
            "descricao": a.d.value,
            "ingredientes": ing,
            "titulo": a.t.value,
            "dificuldade": a.di.value,
            "gostos": gostos,
            "data": a.da.value,
            "tipoCozinha": a.tc.value,
            "tipoPrato": a.tp.value,
            "autor": a.n.value,
            "imgPath": a.img.value
        }
        arr.push(obj)
    });
    res.status(200).jsonp({receitas:arr})
});

router.get('/recentes', async function(req, res, next) {
    var query = `select ?img ?d ?s ?da ?di ?tc ?tp ?a ?n (GROUP_CONCAT(distinct ?ig;SEPARATOR="&") AS ?igs) ?t (GROUP_CONCAT(distinct ?g;SEPARATOR="&") AS ?gs) where {  ?s rdf:type :Receita.
        ?s :descricao ?d.
        ?s :data ?da.
        ?s :dificuldade ?di.
        ?s :ingrediente ?ig.
   		?s :titulo ?t.
        ?s :tipoCozinha ?tc.
        ?s :tipoPrato ?tp.
        ?s :CriadaPor ?a.
        ?a :nome ?n.
        ?s :imgPath ?img.
        OPTIONAL {?s :éGostadoPor ?g.}
    } 
group by ?d ?s ?da ?di ?t ?tc ?tp ?a ?n ?img
order by desc(?da)
limit 6`
    var result =await gdb.execQuery(query)
   
    if(result.results.bindings[0]){
        var arr = []
        result.results.bindings.forEach(a => {
            var ing=[]
            if(a.igs.value){
                a.igs.value.split('&').forEach(i=>{
                    ing.push(i)
                })
            }
            var gostos = []
            if(a.gs.value){
                a.gs.value.split('&').forEach(g=>{
                   gostos.push(g.split('#')[1])
                })
            }
            var obj = {
                "rec_id": a.s.value.split('#')[1],
                "descricao": a.d.value,
                "ingredientes": ing,
                "titulo": a.t.value,
                "dificuldade": a.di.value,
                "gostos": gostos,
                "data": a.da.value,
                "tipoCozinha": a.tc.value,
                "tipoPrato": a.tp.value,
                "autor": a.n.value,
                "imgPath": a.img.value
            }
            arr.push(obj)
        });
        res.status(200).jsonp({receitas:arr})
    }
    else{
        res.status(404).jsonp({message:"Receitas não existem!"})
    }
});



router.get('/tiposCozinha', async function(req, res, next) {
    var query = `select distinct ?p where{ ?s :tipoCozinha ?p.}`
    var result =await gdb.execQuery(query)
   
    var arr = []
    result.results.bindings.forEach(a => {
        arr.push(a.p.value)
    });
    res.status(200).jsonp({tipos:arr})
});



router.get('/tiposPrato', async function(req, res, next) {
    var query = `select distinct ?p where{ ?s :tipoPrato ?p.}`
    var result =await gdb.execQuery(query)
   
    var arr = []
    result.results.bindings.forEach(a => {
        arr.push(a.p.value)
    });
    res.status(200).jsonp({tipos:arr})
});


router.get('/autores', async function(req, res, next) {
    var query = `select distinct ?n where{ 
        ?p rdf:type :Receita.
        ?s :Criou ?p. 
        ?s :nome ?n.}`
    var result =await gdb.execQuery(query)
   
    var arr = []
    result.results.bindings.forEach(a => {
        arr.push(a.n.value)
    });
    res.status(200).jsonp({tipos:arr})
});



router.get('/:id', async function(req, res, next) {
    var id = '<http://www.di.uminho.pt/prc2021/PRC2021_Tp#'+req.params.id+'>'
    var query = `select ?img ?d ?da ?di ?tc ?tp ?a ?n (GROUP_CONCAT(distinct ?ig;SEPARATOR="&") AS ?igs) ?t (GROUP_CONCAT(distinct ?g;SEPARATOR="&") AS ?gs) where {  ?s rdf:type :Receita.
        ${id} :descricao ?d.
        ${id} :data ?da.
        ${id} :dificuldade ?di.
        ${id} :ingrediente ?ig.
   		${id} :titulo ?t.
        ${id} :tipoCozinha ?tc.
        ${id} :tipoPrato ?tp.
        ${id} :CriadaPor ?a.
        ${id} :imgPath ?img.
        ?a :nome ?n.
        OPTIONAL {${id} :éGostadoPor ?g.}
    } group by ?d ?da ?di ?t ?tc ?tp ?a ?n ?img`
           

    var result =await gdb.execQuery(query)
    if(result.results.bindings[0]){


        var ing=[]
        if(result.results.bindings[0].igs.value){
            result.results.bindings[0].igs.value.split('&').forEach(i=>{
                ing.push(i)
            })
        }
        var gostos = []
        if(result.results.bindings[0].gs.value){
            result.results.bindings[0].gs.value.split('&').forEach(g=>{
               gostos.push(g.split('#')[1])
            })
        }
        var obj = {
            "rec_id": req.params.id,
            "descricao": result.results.bindings[0].d.value,
            "ingredientes": ing,
            "titulo": result.results.bindings[0].t.value,
            "dificuldade": result.results.bindings[0].di.value,
            "gostos": gostos,
            "data": result.results.bindings[0].da.value,
            "tipoCozinha": result.results.bindings[0].tc.value,
            "tipoPrato": result.results.bindings[0].tp.value,
            "autor": result.results.bindings[0].n.value,
            "imgPath": result.results.bindings[0].img.value,
            "autor_id": result.results.bindings[0].a.value.split('#')[1],
        }
        
    
        res.status(200).jsonp({receita:obj})
    }else{
        res.status(404).jsonp({message:"Receita não existe!"})

    }
}); 

router.post('/remover', async function(req, res, next) {

    var token = verifyToken(req.headers.authorization)
    
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{
        var query1 =`select ?idpub where{
            ?idpub :RelativaA :${req.body.idReceita}.
        }`
        var result =await gdb.execQuery(query1) 
        //apagar pubs se tiver
        if(result.results.bindings[0]){
            result.results.bindings.forEach(async (e) => {
                var queryAux = `DELETE where {  :${e.idpub.value.split('#')[1]} rdf:type :Publicacao.
                :${e.idpub.value.split('#')[1]} rdf:type owl:NamedIndividual.
                :${e.idpub.value.split('#')[1]} :CriadaPor ?a.
                ?a :Criou :${e.idpub.value.split('#')[1]}.
                :${e.idpub.value.split('#')[1]} :RelativaA ?r.
                :${e.idpub.value.split('#')[1]} :data ?da.
                :${e.idpub.value.split('#')[1]} :descricao ?d.
                :${e.idpub.value.split('#')[1]} :titulo ?tr.
                }       
               ` 
                await gdb.execTransaction(queryAux)
                
            });
        }

        var query = `DELETE WHERE
        {
            :${req.body.idReceita} :CriadaPor <http://www.di.uminho.pt/prc2021/PRC2021_Tp#${req.body.idUser}>.
            <http://www.di.uminho.pt/prc2021/PRC2021_Tp#${req.body.idUser}> :Criou  :${req.body.idReceita}.
            :${req.body.idReceita} :data ?d.
            :${req.body.idReceita} :descricao ?dr.
            :${req.body.idReceita} :dificuldade ?df.
            :${req.body.idReceita} :ingrediente ?i.
            :${req.body.idReceita} :tipoCozinha ?tc.
            :${req.body.idReceita} :tipoPrato ?tp.
            :${req.body.idReceita} :titulo ?t.
            :${req.body.idReceita} :imgPath ?img.
            :${req.body.idReceita} rdf:type :Receita.
            :${req.body.idReceita} rdf:type owl:NamedIndividual.
            
        `

        var query2 =`select ?g where{
            :${req.body.idReceita} :éGostadoPor ?g.
        }`

        var result2 =await gdb.execQuery(query2) 

        if(result2.results.bindings[0]){
            query+=`:${req.body.idReceita} :éGostadoPor ?p.
            ?p :GostaDe :${req.body.idReceita}.`
        }
        query+="}"

        try {
            await gdb.execTransaction(query)
            res.status(200).jsonp({message:"Receita removida com sucesso!"})
        } catch (error) {
            res.status(500).jsonp({message:"Erro na remoção da receita! "+ error})
        }
    }

});

router.post('/desgostar', async function(req, res, next) {
    var token = verifyToken(req.headers.authorization)
    
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{
       
        var query = `DELETE DATA
        {
            :${req.body.idReceita} :éGostadoPor <http://www.di.uminho.pt/prc2021/PRC2021_Tp#${req.body.idUser}>.
            <http://www.di.uminho.pt/prc2021/PRC2021_Tp#${req.body.idUser}> :GostaDe  :${req.body.idReceita}.
        }`
        try {
            await gdb.execTransaction(query)            
            res.status(200).jsonp({message:"Gosto removida com sucesso!"})
        } catch (error) {
            res.status(500).jsonp({message:"Erro na remoção do gosto! "+ error})
        }
    }

});

router.post('/gostar', async function(req, res, next) {
    var token = verifyToken(req.headers.authorization)
    
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{

        var query = `INSERT 
        {
            :${req.body.idReceita} :éGostadoPor ?p.
            ?p :GostaDe  :${req.body.idReceita}.
        } 
        where{
            ?p rdf:type :Utilizador .
            FILTER regex (str(?p), "${req.body.idUser}").
    
        }`

        try {
            await gdb.execTransaction(query)
            res.status(201).jsonp({message:"Gosto registado com sucesso!"})
        } catch (error) {
            res.status(500).jsonp({message:"Erro no registo do gosto! "+ error})
        }
    }

});

var multer  = require('multer')
var fileName;
var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, './public/uploads/')
  },
  filename: function (req, file, cb) {
      fileName = Date.now() + '-' + file.originalname
      cb(null, fileName)
  }
})
 
var upload = multer({ storage: storage })

router.post('/', upload.single('file'), async function(req, res, next) {
    var token = verifyToken(req.headers.authorization)
    if(!token || token.email != req.body.idUser) {res.status(403).jsonp({erro: "Não tem acesso à operação."})}
    else{
        var rec_id = uuidv4()
        var ing = ""
        var ingredientes = req.body.ingredientes.split(",")
        var poped =  ingredientes.pop()
        ingredientes.forEach(i => {
            ing+='"'+i+'"'+",\n"
        });
        ing+='"'+poped+'"'+";\n"
    
        var query = `INSERT DATA
        { 
               :${rec_id} rdf:type :Receita, owl:NamedIndividual;
                        :titulo "${req.body.titulo}" ;
                        :data "${req.body.data}" ;
                        :descricao "${req.body.descricao}";
                        :ingrediente ${ing}
                        :dificuldade "${req.body.dificuldade}";
                        :tipoCozinha "${req.body.tipoCozinha}";
                        :imgPath "${fileName}";
                        :tipoPrato "${req.body.tipoPrato}".
        }`
        var queryRel = `INSERT 
        {
            :${rec_id} :CriadaPor ?p.
            ?p :Criou  :${rec_id}.
        } 
        where{
            ?p rdf:type :Utilizador .
            FILTER regex (str(?p), "${req.body.idUser}").
        }`
        try {
            await gdb.execTransaction(query)
            await gdb.execTransaction(queryRel)  
            res.status(201).jsonp({message:"Receita registada com sucesso!", idRec: rec_id})
        } catch (error) {
            res.status(500).jsonp({message:"Erro no registo da receita! ", error})
        }
    }

});

module.exports = router;
