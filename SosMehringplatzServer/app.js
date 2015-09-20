/*Define dependencies.*/

var express = require('express');
var socket = require('socket.io');

var app = express();

var http = require('http').Server(app);
var io = socket(http);

/* configure socket io */

io.on('connection', function(socket){
    console.log('user connected');
    socket.on('disconnect', function(){
        console.log('user disconnected');
    });
});

/*
//show uploaded files
app.use('/uploads', express.static(__dirname + config.uploadPath));

// show form
app.get('/form',function(req,res){
      res.sendFile("www/form.html",options);
});

// show client
app.use('/',express.static('www',options));
*/


/* Load Router */

var router = require('./router')(app);

/* Error Handling */

app.use(function(err, req, res, next) {
    res.status(err.status || 500);
});

/* Run the server */

http.listen(3000,function(){
    console.log("Node Server listening on port 3000");
});