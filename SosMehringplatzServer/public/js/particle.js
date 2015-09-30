/* Particle */

function Particle(options) {
    this.pos = options.position || new Vector(0, 0);
    this.rad = options.radius || 10;
    this.vel = options.velocity || new Vector(0, 0);
    this.acc = options.acceleration || new Vector(0, 0);
    this.id = options.id;
    this.data = options.data;

    this.fixed = false;
    this.startRad = this.rad;
    this.hovered = false
};

Particle.prototype = { 

    move : function () {

        // Add our current velocity to our position
        this.pos.add(this.vel);
    },

    hovered : function() {
        console.log("bla");
    },

    //updates the current velocity
    update : function() {

        if (this.fixed) {
            this.vel = new Vector(0,0);
            return;
        }

        // Add our current acceleration to our current velocity
        this.vel.add(this.acc);

        //add resistance force
        this.vel.scale(0.95);
    },

    draw : function() {

        if (this.el) {
            this.el
                .attr("cx", this.pos.x)
                .attr("cy", this.pos.y)
                .attr("r", this.rad)
                .style("fill", colors[Utils.typeToInt(this.data.type)]);
        }
    },

    getNextPos : function() {
        return this.pos.clone().add(this.vel);
    },

    applyForces : function(fields) {
        // starting acceleration this frame
        var totalAcc = new Vector(0,0);

        // for each passed field
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];

            var fieldAcc = field.getAcceleration(this.pos);

            totalAcc.add(fieldAcc);
        }

        // update our particle's acceleration
        this.acc = totalAcc;
    },

    checkCollision : function(particle) {

        if (this.pos.distanceTo(particle.pos) < (this.rad + particle.rad) * 1.1)
            return true;
        else
            return false;
    }
}