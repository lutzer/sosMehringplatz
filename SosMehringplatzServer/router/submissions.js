var express = require('express');
var fs = require('fs');
var _ = require('underscore');

var config = require('../config.js');
var submissions = require('../models/submissions');
var events = require('../utils/events.js');

var router = express.Router();

var multipart = require('multiparty');

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
 * POST /api/submissions/
 */ 
router.post('/', function (req, res) {

    console.log('Received new Submission');

    //parse file uploads
    var form = new multipart.Form({ uploadDir: config.uploadTmpPath});
    form.parse(req, function(err, fields, files) {

        //parse data
        var body;
        if (!_.isEmpty(req.body))
            body = req.body;
        else
            body = _.mapObject(fields,function(val, key) {
                return val[0];
            });

        if (typeof(files) === 'undefined')
            files = {}

        //create submission data
        var submissionData = {
            type : body.type,
            author : {
                name : body.author_name,
            },
            content : {
                text : body.content_text
            },
            files : []
        };

        //insert file names into data object
        _.mapObject(files, function(val, key) {
            var filename = val[0].originalFilename;
            if (key == "content_recording")
                submissionData.content.recording = filename;
            else if (key == "author_image")
                submissionData.author.image = filename;
            submissionData.files.push(filename);
        });

        //insert in db
        submissions.create(submissionData, function(err, object) {
            
            var objectId = object._id;

            if (!_.isEmpty(files)) {
                 //create directory in upload folder
                var targetDir = config.uploadPath + '/' + objectId;
                var exists = fs.existsSync(targetDir);
                if (!exists)
                    fs.mkdirSync(targetDir);

                //copy file to uploads folder
                _.mapObject(files, function(val, key) {
                    file = val[0];
                    var targetPath = targetDir + '/' + file.originalFilename;
                    fs.renameSync(file.path,targetPath);
                });
            }
           
            console.log('New Submission inserted: '+objectId);

            //send model has been added
            events.emit('submissions:new',objectId);

            res.writeHead(200);
            res.end();
        });
       
    }); 
    
});

module.exports = router;