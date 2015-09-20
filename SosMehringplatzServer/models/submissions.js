var Database = require('./database.js');

var Submission = {

	create : function(data,callback) {
	
		var submission = {
			type : data.type || false,
			content : data.content || false,
			author : data.author || false,
			files : data.files || []
		};

		var db = new Database();
		db.submissions.insert(submission,callback);
	},

	get : function(id,callback) {

		var db = new Database();
		db.submissions.findOne({ _id : id }, callback);
	},

	list : function(callback) {

		var db = new Database();
		db.submissions.find({},callback);
	}

}

module.exports = Submission;