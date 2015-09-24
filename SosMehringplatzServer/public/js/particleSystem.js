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

        particle.id = 'p_' + particle.id

        // add particle
        var el = svg.append("circle")
            .attr('id', particle.id)
            .attr('class', 'particle')
        
        particle.el = el;
        this.particles.push(particle);

        //draw particle
        particle.draw();
    },

    addField : function(field) {

        /*var el = svg.append('circle')
            .attr("cx", field.pos.x)
            .attr("cy", field.pos.y)
            .attr('r', 30)
            .style("fill", field.mass < 0 ? "#ff0000" : "#00ff00")
            .style("fill-opacity", 0.2)
            .attr("stroke-width", 0)

        field.el = el;*/
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

    openPopup: function(particleIndex, callback) {

        this.closePopup();

        var particle = this.particles[particleIndex];
        this.poppedUpParticle = particle;

        particle.fixed = true;
        var timer = setInterval(function() {
            particle.rad += 10;
            if (particle.rad >= Config.bigCircleRadius) {
                particle.rad == Config.bigCircleRadius;
                clearInterval(timer);
                callback(particle);
            }
        },10);
    },

    closePopup: function() {
        if (this.poppedUpParticle) {
            var particle = this.poppedUpParticle;

            var timer = setInterval(function() {
                particle.rad -= 10;
                if (particle.rad <= particle.startRad) {
                    clearInterval(timer);
                    particle.rad = particle.startRad;
                    particle.fixed = false;
                }
            },10);

            this.poppedUpParticle = false;
        }
    }

};