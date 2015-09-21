/* Vector */

function Vector(x, y) {
    this.x = x || 0;
    this.y = y || 0;
}


Vector.prototype = {
    // Add a vector to another
    add : function(vector) {
        this.x += vector.x;
        this.y += vector.y;
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

    scale : function(val) {
        this.x = this.x * val;
        this.y = this.y * val;
    }
};