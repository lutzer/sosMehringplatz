/* Utility functions */
var Utils = {

	limitTo : function(v, min, max) {
    	return (Math.min(max, Math.max(min, v)));
	},

	getCurve: function(dist) {
		// found coefficients from fitting curve here http://mycurvefit.com/ for exponent 1
		//var a = 2.5
		//var b = 1.4
		var a = 0.13
		var b = 2.7
		return (a*Math.pow(dist,b))
	},

	typeToInt : function(type) {
		if (type == "QUESTION")
			return 0;
		else if (type =="IDEA")
			return 1;
		else
			return 2;
	},

	convertType : function(type) {
		if (type == "QUESTION")
			return "Frage";
		else if (type =="IDEA")
			return "Idee";
		else
			return "Beschwerde";
	}

}            
