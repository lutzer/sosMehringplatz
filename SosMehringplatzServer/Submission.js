// Parser constructor.
var Submission = function(text) {
	this.text = text;
};

//Outputs the model
Submission.prototype.toString = function() {
	return this.text;
};

// Export the Parser constructor from this module.
module.exports = Submission;