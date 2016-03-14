/*jslint browser : true devel : true*/
/*globals getXMLHttpRequest, Tileset, DIRECTION, map, player*/

function Lily(x, y) {
    "use strict";

    this.x = x;
    this.y = y;
    this.state = false;
    this.lilies = [];
}

Lily.prototype.addLily = function (lily) {
    "use strict";
    this.lilies.push(lily);
};

Lily.prototype.addLily = function (lily) {
    "use strict";
    this.lilies.push(lily);
};

Lily.prototype.getState = function (x, y) {
    "use strict";
    var key, change = false;

    for (key in map.lilies) {
        if (map.lilies.hasOwnProperty(key)) {
            if (map.lilies[key].x === x && map.lilies[key].y === y) {
                if (map.lilies[key].state) {
                    return true;
                }
                map.lilies[key].state = true;
                DATA.lilies++;
                if(map.timer > DATA.best) {
                    DATA.best = 60 - map.timer;
                }
                player.x = 7;
                player.y = 14;
                map.timer = 60;
                map.ground[y][x] = 2;
                map.win++;
            }
        }
    }
    return change;
};