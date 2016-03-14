/*jslint browser : true devel : true*/
/*globals Map, Character, DIRECTION, Car, Lily, Wood*/

var map = new Map();

var DATA = {
    "result"    : 0,
    "lilies"    : 0,
    "time"      : 0,
    "best"      : 0
};

var player = new Character("frog.png", 7, 14, DIRECTION.UP);
var car = new Car("car.png", 1, 13, DIRECTION.LEFT);
var car2 = new Car("car.png", 0, 12, DIRECTION.RIGHT);
var car3 = new Car("car.png", 19, 11, DIRECTION.LEFT);
var car4 = new Car("car.png", 0, 10, DIRECTION.RIGHT);
var car5 = new Car("car.png", 19, 9, DIRECTION.LEFT);
var car6 = new Car("car.png", 6, 13, DIRECTION.LEFT);
var car7 = new Car("car.png", 5, 12, DIRECTION.RIGHT);
var car8 = new Car("car.png", 3, 11, DIRECTION.LEFT);
var car9 = new Car("car.png", 4, 10, DIRECTION.RIGHT);
var car10 = new Car("car.png", 11, 9, DIRECTION.LEFT);

map.addCar(car);
map.addCar(car2);
map.addCar(car3);
map.addCar(car4);
map.addCar(car5);
map.addCar(car6);
map.addCar(car7);
map.addCar(car8);
map.addCar(car9);
map.addCar(car10);

var lily = new Lily(1, 2);
var lily2 = new Lily(5, 2);
var lily3 = new Lily(9, 2);
var lily4 = new Lily(13, 2);
var lily5 = new Lily(18, 2);

map.addLily(lily);
map.addLily(lily2);
map.addLily(lily3);
map.addLily(lily4);
map.addLily(lily5);

var wood = new Wood("wood.png", 1, 3, DIRECTION.LEFT);
var wood2 = new Wood("wood.png", 3, 4, DIRECTION.RIGHT);
var wood3 = new Wood("wood.png", 5, 5, DIRECTION.LEFT);
var wood4 = new Wood("wood.png", 7, 6, DIRECTION.RIGHT);
var wood5 = new Wood("wood.png", 9, 7, DIRECTION.LEFT);
var wood6 = new Wood("wood.png", 11, 3, DIRECTION.LEFT);
var wood7 = new Wood("wood.png", 13, 4, DIRECTION.RIGHT);
var wood8 = new Wood("wood.png", 15, 5, DIRECTION.LEFT);
var wood9 = new Wood("wood.png", 17, 6, DIRECTION.RIGHT);
var wood10 = new Wood("wood.png", 19, 7, DIRECTION.LEFT);

map.addWood(wood);
map.addWood(wood2);
map.addWood(wood3);
map.addWood(wood4);
map.addWood(wood5);
map.addWood(wood6);
map.addWood(wood7);
map.addWood(wood8);
map.addWood(wood9);
map.addWood(wood10);

window.onload = function () {
    "use strict";
    var canvas, ctx, game, count = 0, timer, swim = 0;
	canvas = document.getElementById('canvas');
	ctx = canvas.getContext('2d');

	canvas.width  = map.getWidth() * 32;
	canvas.height = map.getHeight() * 32;
	game = setInterval(function () {
        if (player.life >= 0) {
            if (map.win === 5) {
                DATA.result = "win";
                data(DATA);
                alert("you win");
                clearInterval(game);
            }
            map.drawMap(ctx);
            if (count === 1) {
                car.moveAll(map);
                if (swim > 20) {
                    wood.moveAll(map);
                    swim = 0;
                }
                count -= 2;
            }
            if (map.time()) {
                player.die();
            }
            count++;
            swim++;
        } else {
            DATA.result = "loose";
            data(DATA);
            clearInterval(game);
            alert("Game Over");
        }
	}, 40);

    timer = setInterval(function () {
        map.timer--;
        DATA.time++;
    }, 1000);

	window.onkeydown = function (event) {
        var e, key;
		e = event || window.event;
		key = e.which || e.keyCode;

		switch (key) {
        case 38:
        case 122:
        case 119:
        case 90:
        case 87: // Flèche haut, z, w, Z, W
			player.move(DIRECTION.UP, map);
			break;
		case 40:
        case 115:
        case 83: // Flèche bas, s, S
			player.move(DIRECTION.DOWN, map);
			break;
		case 37:
        case 113:
        case 97:
        case 81:
        case 65: // Flèche gauche, q, a, Q, A
			player.move(DIRECTION.LEFT, map);
			break;
		case 39:
        case 100:
        case 68: // Flèche droite, d, D
			player.move(DIRECTION.RIGHT, map);
			break;
		default:
			return true;
		}
		return false;
    };
};

function data(data) {
    document.getElementById('result').value = data.result
    document.getElementById('lilies').value = data.lilies;
    document.getElementById('time').value = data.time;
    document.getElementById('best').value = data.best;

    document.getElementById('form').style.display = "block";
};