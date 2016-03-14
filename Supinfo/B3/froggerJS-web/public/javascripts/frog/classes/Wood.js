/*jslint browser : true devel : true*/
/*globals getXMLHttpRequest, Tileset, DIRECTION, player, map*/

function Wood(url, x, y, direction) {
    "use strict";

    this.x = x;
    this.y = y;
    this.direction = direction;

    this.image = new Image();
    this.image.ref = this;
    this.image.onload = function () {
        if (!this.complete) {
            throw "Erreur de chargement du sprite nommé \"" + url + "\".";
        }
        this.ref.largeur = this.width;
        this.ref.hauteur = this.height / 2;
    };
    this.image.src = "/img/frog/sprites/" + url;
}

Wood.prototype.drawWood = function (context) {
    "use strict";
    context.drawImage(
        this.image,
        0,
        this.hauteur,
        this.largeur,
        this.hauteur,
        (this.x * 32),
        (this.y * 32),
        64,
        32
    );
};

Wood.prototype.move = function (direction, map, key) {
    "use strict";
    var way, next;
    if (direction === 2) {
        way = 1;
    } else {
        way = -1;
    }

    map.forest[key].direction = direction;

    next = map.forest[key].nextCase(direction);

    map.forest[key].x = next.x;
    map.forest[key].y = next.y;

    if (next.x === -2) {
        map.forest[key].x = 19;
    }
    if (next.x === 20) {
        map.forest[key].x = -1;
    }

    if (player.y === map.forest[key].y) {

        if (player.x === map.forest[key].x || (player.x + way) === map.forest[key].x) {
            player.x += way;
        }
    }



    return true;
};

Wood.prototype.nextCase = function (direction) {
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

Wood.prototype.moveAll = function (map) {
    "use strict";
    var key;

    for (key in map.forest) {
        if (map.forest.hasOwnProperty(key)) {
            this.move(map.forest[key].direction, map, key);
            if (this.sink(map.forest[key].direction)) {
                //player.die();
            }
        }
    }
};

Wood.prototype.sink = function (direction) {
    "use strict";
    var key, way, result = [], end = 0;
    if (direction === 2) {
        way = 1;
    } else {
        way = -1;
    }

    if (player.y > 2 && player.y < 8) {
        for (key in map.forest) {
            if (map.forest.hasOwnProperty(key)) {
                if (player.y === map.forest[key].y) {
                    end++;
                    if (player.x === map.forest[key].x || (player.x + way) === map.forest[key].x) {
                        result.push(false);
                    } else {
                        result.push(true);
                    }

                    if (end === 2) {
                        if (!(result[0] && result[1])) {
                            return false;
                        } else {
                            return true;
                        }

                    }
                }
            }
        }
    }
};