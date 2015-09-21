/* Particle System */

function ParticleSystem(svg) {
    // start with empty particle list
    this.particles = [];
    this.fields = [];
    this.svg = svg;
    this.particleCounter = 0;
};

ParticleSystem.prototype = {

    emit : function(particle) {

        particle.id = 'p_' + this.particleCounter;

        // add particle
        var el = svg.append("circle")
            .attr('id', particle.id)
            .attr('class', 'particle')
            .attr("cx", particle.pos.x)
            .attr("cy", particle.pos.y)
            .attr("r", particle.rad)
            .style("fill", colors[this.particleCounter%3]);
        
        particle.el = el;
        this.particles.push(particle);

        this.particleCounter++;
    },

    addField : function(field) {

        var el = svg.append('circle')
            .attr('id', field.id)
            .attr("cx", field.pos.x)
            .attr("cy", field.pos.y)
            .attr('r', 30)
            .style("fill", field.mass < 0 ? "#ff0000" : "#00ff00")
            .style("fill-opacity", 0.2)
            .attr("stroke-width", 0)

        field.el = el;
        this.fields.push(field);
    },

    moveField: function(i,pos) {

        var field = this.fields[i]

        field.pos = pos;

        field.el
            .attr("cx", field.pos.x)
            .attr("cy", field.pos.y);
    },

    update : function(deltaT) {

        var self = this;

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
                            50,
                            1.0
                        )
                    );
            }
            particle.force(particleFields);

        }

        //move and draw
        this.particles.forEach(function(particle) {

            particle.move();

            particle.el
                .attr("cx", particle.pos.x)
                .attr("cy", particle.pos.y);
        });
    },

};