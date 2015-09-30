/* Particle System */

function ParticleSystem(svg) {
    // start with empty particle list
    this.particles = [];
    this.fields = [];
    this.svg = svg;

    this.poppedUpParticle = false;
};

ParticleSystem.prototype = {

    emit : function(particle) {

        var self = this;

        particle.id = 'p_' + particle.id

        // add particle
        var el = svg.append("circle")
            .attr('id', particle.id)
            .attr('class', 'particle')
            .on("mouseover", self.onMouseEnter)
            .on("mouseleave", self.onMouseLeave);
        
        particle.el = el;
        this.particles.push(particle);

        //draw particle
        particle.draw();
    },

    addField : function(field) {
        this.fields.push(field);
    },

    moveField: function(i,pos) {

        var field = this.fields[i]

        field.pos = pos;

        //redraw particle
        /*field.el
            .attr("cx", field.pos.x)
            .attr("cy", field.pos.y)*/
    },

    update : function(deltaT) {

        var self = this;

        var sqrt2 = Math.sqrt(2);

        //apply force
        for (var i=0; i<this.particles.length; i++) {

            var particle = this.particles[i];

            var particleFields = [];
            Array.prototype.push.apply(particleFields,self.fields);

            for (var j=0; j<this.particles.length; j++) {
                if (i!=j)
                    particleFields.push(
                        new Field(
                            self.particles[j].pos,
                            -Utils.getCurve(particle.rad + self.particles[j].rad),
                            1.15
                        )
                    );
            }

            particle.applyForces(particleFields);

            // update particle
            particle.update();
            particle.move();

            //redraw particle
            particle.draw();
        }
    },

    // check if there is a particle at the pointed location and return its index
    findParticle: function(x,y) {


        var mousePos = new Vector(x,y);

        for (var i=0; i<this.particles.length; i++) {
            var particlePos = this.particles[i].pos;
            var particleRad = this.particles[i].rad;

            if (mousePos.distanceTo(particlePos) < particleRad)
                return i;
        }

        return false;
    },

    onMouseEnter: function() {

        //clear all others
        $(this).attr('class','particle hover');
    },

    onMouseLeave: function() {
        $(this).attr('class','particle');
    },

    openPopup: function(particleIndex, callback) {

        if (this.poppedUpParticle)
            this.closePopup(this.poppedUpParticle);

        var particle = this.particles[particleIndex];
        this.poppedUpParticle = particle;

        particle.fixed = true;
        var timer = setInterval(function() {
            particle.rad += 10;
            if (particle.rad >= Config.bigCircleRadius) {
                particle.rad = Config.bigCircleRadius;
                clearInterval(timer);
                callback(particle);
            }
        },10);
    },

    closePopup: function(callback) {;
        if (!this.poppedUpParticle)
            return;

        particle = this.poppedUpParticle;
        particle.fixed = false;
        var timer = setInterval(function() {
            particle.rad -= 10;
            if (particle.rad <= particle.startRad) {
                clearInterval(timer);
                particle.rad = particle.startRad;
                if (typeof(callback) === 'function')
                    callback();
            }
        },10);
    }

};