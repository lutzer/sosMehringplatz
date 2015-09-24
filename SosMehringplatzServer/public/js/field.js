/* Field */

var maxFieldStrength = 99999999;

function Field(point, mass, power, type) {
    this.pos = point;
    this.mass = mass || 0.1;
    this.power = power || 0.5;
    this.type = type || 'exponential'
}

Field.prototype.getAcceleration = function(point) {


	if (this.type == 'exponential') {
		// get distance & calculate exponential force m/d^p
		var diff = this.pos.clone().substract(point);
	    var force = this.mass / Math.pow(diff.x*diff.x+diff.y*diff.y, this.power);
	    /*if (this.power == 1) {
	    	console.log(force);
	    	console.log(diff.length());
	    }*/
		
	    return diff.norm().scale(force);
	} else if (this.type == 'constant') {
		var diff = this.pos.clone().substract(point);
		var force = this.power;
		return diff.norm().scale(power);
	}
}

