/* CONFIG FILE */

var Config = {
	
	uploadPath : __dirname + "/public/uploads", //chmod this path 777
	uploadTmpPath : __dirname + "/tmp", //chmod this path 777
	databaseFile : __dirname + "/data", //chmod this path 777

	baseUrl : '/',
	servePublicDir : true,
	hostname : false, // 127.0.0.1 = private, false = public
	port : '8081'

};

module.exports = Config;