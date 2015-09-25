var Database = require('./database');

var Submission = {

	create : function(data,callback) {

		var db = new Database();

		var submission = {
				type : data.type || false,
				content : data.content || false,
				author : data.author || false,
				files : data.files || [],
		};


		db.submissions.insert(submission,callback)
	},

	get : function(id,callback) {

		var db = new Database();
		db.submissions.findOne({ _id : id }, callback);
	},

	list : function(callback) {

		var db = new Database();
		db.submissions.find({}, function(err, cursor) {
			cursor.toArray(callback);
		});
	},

	remove : function(id,callback) {
		var db = new Database();
		db.submissions.remove({_id: id},callback);
	}

}

module.exports = Submission;