var socketio = require('socket.io');
var submissions = require('../models/submissions');

var events = require('../utils/events');

module.exports = function (http) {

	var io = socketio(http);

	io.on('connection', function(socket){

	    console.log('Socket: User connected');

	    // Server Events 

	    function submissionAddHandler(id) {
	    	submissions.get(id,function(err,doc) {
		        socket.emit('submissions:new',{data: doc});
		    });
	    }
	    
		events.on('submissions:new', submissionAddHandler);

	    // Event handlers

	    socket.on('submissions:list', function(err) {
	    	submissions.list(function(err,docs) {
		        socket.emit('submissions:list',{data: docs});
		    });
	    });

	    socket.on('submissions:get', function(id) {
	    	submissions.get(id,function(err,doc) {
		        socket.emit('submissions:get',{data: doc});
		    });
	    });

		socket.on('error', function(err) {
	    	console.log(err);
		});

	    // Clean up after disconnect

	    socket.on('disconnect', function(){
	        console.log('Socket: User disconnected');

	        events.removeListener('submissions:new',submissionAddHandler);
	    });

	});

};