var express = require('express');
var fs = require('fs');
var _ = require('underscore');
var multer = require('multer');

var config = require('../config.js');
var submissions = require('../models/submissions');

var router = express.Router();

/* configure multer */

var upload = multer({ 
    dest: config.uploadTmpPath
});

/*
 * GET /api/submissions/
 */ 
router.get('/',function(req,res){
    submissions.list(function(err,docs) {
        res.send(docs);
    });
});

/*
 * GET /api/submissions/:id
 */ 
router.get('/:id',function(req,res){
    submissions.get(req.params.id, function(err,docs) {
        res.send(docs);
    });
});

/*
 * POST /api/submissions/:id
 */ 
router.post('/', upload.array('files[]'), function (req, res, next) {

    var submission = {
        type: req.body.submission_type,
        content: req.body.submission_content,
        author: req.body.submission_author,
        files : _.map(req.files, function(element) {
            return element.originalname;
        })
    };

    //insert in db
    submissions.create(submission, function(err, object) {
        var objectId = object._id;

        //create directory in upload folder
        fs.mkdirSync(config.uploadPath + '/' + objectId);

        //copy file to uploads folder
        req.files.forEach(function(file) {
            var target_path = config.uploadPath + '/' + objectId + '/' + file.originalname;
            fs.renameSync(file.path,target_path);
        });
        console.log('New Submission inserted:');
        console.log(object);  

        //send new model message over socket
        //io.sockets.emit('submission', {data: {hallo: 'hallo', hallo2: 'test'}});
    });

    
});

module.exports = router;