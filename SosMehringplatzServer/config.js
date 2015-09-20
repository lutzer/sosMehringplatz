/* CONFIG FILE */

// Parser constructor.
var Config = {
	
	uploadPath : __dirname + "/public/uploads",
	uploadTmpPath : __dirname + "/tmp",

	databaseFile : __dirname + "/data/submissions.db"

};

module.exports = Config;