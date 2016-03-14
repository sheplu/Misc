/*jslint browser : true devel : true*/
/*globals getXMLHttpRequest, Tileset, player, map*/

function Map() {
    "use strict";
    var xhr, mapJsonData, mapData, nom;

    nom = "map";
	xhr = getXMLHttpRequest();

	xhr.open("GET", './maps/' + nom + '.json', false);
	xhr.send(null);
	if (xhr.readyState !== 4 || (xhr.status !== 200 && xhr.status !== 0)) {
		throw new Error("Impossible de charger la carte nommée \"" + nom + "\" (code HTTP : " + xhr.status + ").");
    }
	mapJsonData = xhr.responseText;

	mapData = JSON.parse(mapJsonData);
	this.tileset = new Tileset(mapData.tileset);
	this.ground = mapData.ground;

    this.trucks = [];
    this.lilies = [];
    this.forest = [];
    this.timer = 60;
    this.win = 0;
}

Map.prototype.getHeight = function () {
    "use strict";
	return this.ground.length;
};

Map.prototype.getWidth = function () {
    "use strict";
	return this.ground[0].length;
};

Map.prototype.drawMap = function (context) {
    "use strict";
    var i, l, ligne, y, j, k;
	for (i = 0, l = this.ground.length; i < l; i += 1) {
		ligne = this.ground[i];
		y = i * 32;

		for (j = 0, k = ligne.length; j < k; j += 1) {
			this.tileset.drawTile(ligne[j], context, j * 32, y);
		}
	}
    for (i = 0, l = this.forest.length; i < l; i += 1) {
        this.forest[i].drawWood(context);
    }
	player.drawCharacter(context);

    for (i = 0, l = this.trucks.length; i < l; i += 1) {
        this.trucks[i].drawCar(context);
    }

    this.drawLife(context);
    this.drawTimer(context);
};

Map.prototype.addCar = function (car) {
    "use strict";
    this.trucks.push(car);
};

Map.prototype.collide = function (car) {
    "use strict";
    if (player.x === car.x && player.y === car.y) {
        player.die();
    }
};

Map.prototype.addLily = function (lily) {
    "use strict";
    this.lilies.push(lily);
};

Map.prototype.drawLife = function (ctx) {
    "use strict";
    ctx.fillStyle = "white";
    ctx.font = "20px Calibri";
    ctx.fillText("Life: " + player.life, 20, 20);
};

Map.prototype.time = function () {
    "use strict";
    if (this.timer === 0) {
        return true;
    }
};

Map.prototype.drawTimer = function (ctx) {
    "use strict";
    ctx.fillStyle = "white";
    ctx.font = "20px Calibri";
    ctx.fillText("Time: " + map.timer, 500, 20);
};

Map.prototype.addWood = function (wood) {
    "use strict";
    this.forest.push(wood);
};

Map.prototype.grass = function () {
    "use strict";
    var key;
    if (player.y > 0 && player.y < 3) {
        for (key in map.lilies) {
            if (map.lilies.hasOwnProperty(key)) {
                if (player.x !== map.lilies[key].x) {
                    return true;
                }
            }
        }
    }
};






