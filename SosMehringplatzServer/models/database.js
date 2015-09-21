var Nedb = require('nedb');
var fs = require('fs');

var config = require('../config');

var Database = function() {
	//saves the data
	this.submissions = new Nedb({ filename: config.databaseFile, autoload: true });

	//saves last id for auto increment
	var exists = fs.existsSync(config.databaseCounterFile);
    if(exists) //file already exists
		this.counters = new Nedb({ filename: config.databaseCounterFile, autoload: true }); 
	else { //if not create it with initial value
		this.counters = new Nedb({ filename: config.databaseCounterFile, autoload: true }); 
		this.counters.insert({
		    _id: "submissionId",
		    seq: 0
		});
	}
}

Database.prototype.submissions = function() {
	return this.submissions;
};

Database.prototype.getNextSequence = function(name,callback) {

	var self = this;

	this.counters.findOne({ _id : name }, function(err,doc) {
		console.log(doc);
		self.counters.update({ _id : name }, { seq : doc.seq + 1 }); 
		callback(doc.seq);
	});
};

module.exports = Database;