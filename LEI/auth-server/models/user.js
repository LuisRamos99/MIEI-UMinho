var mongoose = require('mongoose')


var userSchema = new mongoose.Schema({
    username: { type:  String, required: true },
    email: { type:  String, required: true },
    password: { type:  String, required: true },
    googleId:{type:String},
    createdAt: {type: Date, default: Date.now},
    updatedAt: {type: Date, default: Date.now},
    level:{type:String, default:"consumer"}    
})


module.exports = mongoose.model('utilizadore' , userSchema)