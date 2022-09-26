var mongoose = require('mongoose')
var User = require('../models/user')

// Listar os users
module.exports.list = ()=> {
    return User.find().exec()
}

// Procurar o user id
module.exports.lookUp = id => {
    return User.findOne({username: id}).exec()
}

module.exports.google = id => {
    return User.findOne({googleId: id}).exec()
}

// Inserir o user u
module.exports.insert = u => {
    var newUser = new User(u)
    return newUser.save()
}

// Remover o user id
module.exports.remove = id => {
    return User.deleteOne({_id : id})
}

// Editar o user id para u
module.exports.edit = (id,u) => {
    return User.findOneAndUpdate({username:id}, u, {new: true})
}
