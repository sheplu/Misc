/*jslint browser : true devel : true*/
/*globals getXMLHttpRequest, Tileset, DIRECTION*/

function Car(url, x, y, direction) {
    "use strict";

    this.x = x;
    this.y = y;
    this.direction = direction;

    this.image = new Image();
    this.image.reference = this;
    this.image.onload = function () {
        if (!this.complete) {
            throw "Erreur de chargement du sprite nommé \"" + url + "\".";
        }
        this.reference.largeur = this.width;
        this.reference.hauteur = this.height / 2;
    };
    this.image.src = "/img/frog/sprites/" + url;
}

Car.prototype.drawCar = function (context) {
    "use strict";
    context.drawImage(
        this.image,
        0,
        this.hauteur,
        this.largeur,
        this.hauteur,
        (this.x * 32),
        (this.y * 32),
        32,
        32
    );
};

Car.prototype.move = function (direction, map, key) {
    "use strict";
    map.trucks[key].direction = direction;

    var prochaineCase = map.trucks[key].nextCase(direction);

    map.trucks[key].x = prochaineCase.x;
    map.trucks[key].y = prochaineCase.y;

    if (prochaineCase.x === -2) {
        map.trucks[key].x = 19;
    }
    if (prochaineCase.x === 20) {
        map.trucks[key].x = -1;
    }

    return true;
};

Car.prototype.nextCase = function (direction) {
    "use strict";
    var pos = {'x' : this.x, 'y' : this.y};
    switch (direction) {
    case DIRECTION.DOWN:
        pos.y += 1;
        break;
    case DIRECTION.LEFT:
        pos.x -= 1;
        break;
    case DIRECTION.RIGHT:
        pos.x += 1;
        break;
    case DIRECTION.UP:
        pos.y -= 1;
        break;
    }
    return pos;
};

Car.prototype.moveAll = function (map) {
    "use strict";
    var key;

    for (key in map.trucks) {
        if (map.trucks.hasOwnProperty(key)) {
            this.move(map.trucks[key].direction, map, key);
            map.collide(map.trucks[key]);
        }
    }
};












