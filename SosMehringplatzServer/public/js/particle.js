/* Particle */

function Particle(point, radius, velocity, acceleration) {
    this.pos = point || new Vector(0, 0);
    this.rad = radius || 10;
    this.vel = velocity || new Vector(0, 0);
    this.acc = acceleration || new Vector(0, 0);
};

Particle.prototype = { 

    move : function () {
        // Add our current acceleration to our current velocity
        this.vel.add(this.acc);

        //add resistance force
        //var speed = this.vel.length();
        /*var force = - 0.005 * speed * speed
        if (!isNaN(force))
            this.vel.scale(1.0+force);*/
        this.vel.scale(0.95);

        // Add our current velocity to our position
        this.pos.add(this.vel);
    },

    force : function(fields) {
        // starting acceleration this frame
        var totalAccelerationX = 0;
        var totalAccelerationY = 0;

        // for each passed field
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];

            // find the distance between the particle and the field and calculate the force
            var vectorX = field.pos.x - this.pos.x;
            var vectorY = field.pos.y - this.pos.y;
            var force = field.mass / Math.pow(vectorX*vectorX+vectorY*vectorY, field.power);

            // add to the total acceleration the force adjusted by distance
            totalAccelerationX += vectorX * force;
            totalAccelerationY += vectorY * force;
        }

        // update our particle's acceleration
        this.acc = new Vector(totalAccelerationX, totalAccelerationY);
    },

    checkCollision : function(particle) {

        if (this.pos.distanceTo(particle.pos) < (this.rad + particle.rad) * 1.5)
            return true;
        else
            return false;
    }
}