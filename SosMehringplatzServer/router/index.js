var express = require('express');
var bodyParser = require('body-parser');

/* configure express */

var options = {
    root: __dirname,
    dotfiles: 'deny',
    headers: {
        'x-timestamp': Date.now(),
        'x-sent': true
	}
};

module.exports = function (app) {

	// serve static content from public directory
	app.use('/',express.static('public',options));

	// parse application/x-www-form-urlencoded
	app.use('/api/submissions', bodyParser.urlencoded({ extended: false }));
    app.use('/api/submissions', require('./submissions'));
};