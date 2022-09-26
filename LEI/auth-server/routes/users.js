var express = require('express');
var router = express.Router();
var passport = require('passport')
var UserControl = require('../controllers/user')
var jwt = require('jsonwebtoken');
const user = require('../models/user');
var cors = require('cors');
var fs = require('fs')

var corsOptions = {
  origin: 'http://localhost:8080',
  optionsSuccessStatus: 200 // For legacy browser support
}
function auth(req,res,next){
  passport.authenticate('local', function(err, user, info) {
    if (err) return next(err);
    if (!user) {
      
       res.status(401).json(info);
       return;
      
    // Register failed, flash message is in info
    } 
    req.logIn(user, function(err) {
      if (err) return next(err);
      return next()
    });
    
  })(req, res, next);
}

router.post('/login',cors(), auth,  function(req,res,next){
  var privateKey = fs.readFileSync(__dirname + '/../keys/mykey.pem')
  jwt.sign({username: req.user.username, level: req.user.level, id: req.user.id,
            sub:'Trabalho de LEI2021'},
            privateKey,
            {algorithm:'RS256'}
            ,
            function(e,token){
              if(e) res.status(500).jsonp({error: "Erro na geração do token: " + e})
              else res.status(201).jsonp({token: token,username: req.user.username, level: req.user.level, id: req.user.id})
  });
})

router.post('/google/login',cors(), passport.authenticate('google-local'), function(req,res){
  var privateKey = fs.readFileSync(__dirname + '/../keys/mykey.pem')
  jwt.sign({username: req.user.username, level: req.user.level, 
            sub:'Trabalho de LEI2021'},
            privateKey,
            {algorithm:'RS256'}
            ,
            function(e,token){
              if(e) res.status(500).jsonp({error: "Erro na geração do token: " + e})
              else res.status(201).jsonp({token: token,username: req.user.username, level: req.user.level, id: req.user.id})
  });
})

router.post('/register',cors(),  function(req,res,next){
  UserControl.lookUp(req.body.username)
    .then(da => {
      if(da == null){
        if(req.body.googleId){
          console.log(req.body.googleId)
          UserControl.google(req.body.googleId)
            .then(data => { 
              if(data == null) {
                UserControl.insert(req.body)
                  .then(d => { 
                    res.status(201).jsonp({username: d.username})
                  })
                  .catch(err => {
                    res.status(409).jsonp({message: 'Email já registado!\n'})
                  })
              }
              else 
                res.status(409).jsonp({message:"Conta Google já registada!\n"})
            })
            .catch(err => err => res.status(500).jsonp(err))
        }
        else{
          UserControl.insert(req.body)
          .then(data => { 
            res.status(201).jsonp({username: data.username})
          })
          .catch(err => {
            res.status(409).jsonp({message: 'Email já registado!\n'})
          })
        }
        
      }
      else {
        res.status(409).jsonp({message:"Username já registado!\n"})
      }
    })
    .catch(err => res.status(500).jsonp(err))
})







router.options('/*',  cors())


module.exports = router;
