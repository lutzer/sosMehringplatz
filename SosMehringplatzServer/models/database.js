var Tingodb = require('tingodb')();
var fs = require('fs');

var config = require('../config');

var Database = function() {
	//saves the data
	var db = new Tingodb.Db(config.databaseFile, {});
	this.submissions = db.collection("submissions.db");
}

Database.prototype.submissions = function() {
	return this.submissions;
};

module.exports = Database;