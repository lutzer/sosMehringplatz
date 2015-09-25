/* Field */

var PLATEAU_BOUNDS = 10;


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
	    return diff.norm().scale(force);
	} else if (this.type == 'plateau') {
		var diff = this.pos.clone().substract(point);
	    var force = this.mass / Math.pow(diff.x*diff.x+diff.y*diff.y, this.power);
	    if (Math.abs(force) > PLATEAU_BOUNDS)
	    	force = Math.max(PLATEAU_BOUNDS,Math.max(-PLATEAU_BOUNDS,force))
		return diff.norm().scale(force);
	}
}

