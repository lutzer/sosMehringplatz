var express = require('express');
var fs = require('fs');
var _ = require('underscore');
var multer = require('multer');

var config = require('../config.js');
var submissions = require('../models/submissions');

var events = require('../utils/events.js');

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
    submissions.get(req.params.id, function(err,doc) {
        res.send(doc);
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
        console.log(err);
        
        var objectId = object._id;

        //create directory in upload folder
        var targetDir = config.uploadPath + '/' + objectId;
        var exists = fs.existsSync(targetDir);
        if (!exists)
            fs.mkdirSync(targetDir);

        //copy file to uploads folder
        req.files.forEach(function(file) {
            var targetPath = targetDir + '/' + file.originalname;
            fs.renameSync(file.path,targetPath);
        });
        console.log('New Submission inserted:');

        //send model has been added
        events.emit('submissions:add',objectId);
    });

    
});

module.exports = router;