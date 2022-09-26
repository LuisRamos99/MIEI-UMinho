var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var cors = require('cors')

var indexRouter = require('./routes/index');
var utilizadorRouter = require('./routes/utilizador');
var receitaRouter = require('./routes/receita');
var publicacaoRouter = require('./routes/publicacao');

var app = express();

app.use(cors());
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


app.use('/publicacao', publicacaoRouter);
app.use('/utilizador', utilizadorRouter);
app.use('/receita', receitaRouter);
app.use('/', indexRouter);

module.exports = app;
