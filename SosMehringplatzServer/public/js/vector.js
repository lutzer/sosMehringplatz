/* Vector */

function Vector(x, y) {
    this.x = x || 0;
    this.y = y || 0;
}


Vector.prototype = {

    // creates a copy of the vector
    clone : function() {
        return new Vector(this.x,this.y);
    },

    // Add a vector to another
    add : function(vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    },

    substract : function(vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    },
 
    // Gets the length of the vector
    distanceTo : function (vector) {
        var diffX = vector.x - this.x;
        var diffY = vector.y - this.y;

        return Math.sqrt(diffX*diffX+diffY*diffY);
    },

    length : function () {
        return Math.sqrt(this.x*this.x+this.y*this.y);
    },

    scale : function(scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
        return this;
    },

    getOrthagonalVector : function() {
        return new Vector(this.y,-this.x);
    },

    norm: function() {
        return this.scale(1/this.length());
    },

    dotProduct: function(vector) {
        return (this.x*vector.x+this.y*vector.y)
    }


};