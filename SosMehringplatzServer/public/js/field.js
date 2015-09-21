/* Field */

function Field(point, mass, power, id) {
    this.pos = point;
    this.mass = mass || 0.1;
    this.power = power || 0.5;
    this.id = id || 'none';
}

