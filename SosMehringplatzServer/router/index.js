var express = require('express');

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

    app.use('/api/submissions', require('./submissions'));
};