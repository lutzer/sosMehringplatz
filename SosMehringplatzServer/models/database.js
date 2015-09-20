var Nedb = require('nedb');

var config = require('../config.js');

var Database = function() {
	this.submissions = new Nedb({ filename: config.databaseFile, autoload: true });
}

Database.prototype.submissions = function() {
	return this.submissions;
}

module.exports = Database;