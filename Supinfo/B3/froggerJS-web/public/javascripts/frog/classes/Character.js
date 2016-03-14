/*jslint browser : true devel : true*/
/*globals Image, lily, player, map, wood*/
var DIRECTION = {
	"DOWN"    : 0,
	"LEFT" : 1,
	"RIGHT" : 2,
	"UP"   : 3
};

var animation = 4;
var ttm = 4;

function Character(url, x, y, direction) {
    "use strict";

	this.x = x;
	this.y = y;
    this.life = 3;
	this.direction = direction;
	this.animationState = 0;

	this.image = new Image();
	this.image.ref = this;
	this.image.onload = function () {
		if (!this.complete) {
			throw "Erreur de chargement du sprite nommé \"" + url + "\".";
        }
		this.ref.largeur = this.width / 4;
		this.ref.hauteur = this.height / 4;
	};
	this.image.src = "/img/frog/sprites/" + url;
}

Character.prototype.drawCharacter = function (context) {
    "use strict";
	var frame = 0, moveX = 0, moveY = 0, pixel;
	if (this.animationState >= ttm) {
		this.animationState = -1;
	} else if (this.animationState >= 0) {
		frame = Math.floor(this.animationState / animation);
		if (frame > 3) {
			frame %= 4;
		}

		pixel = 32 - (32 * (this.animationState / ttm));

		if (this.direction === DIRECTION.UP) {
			moveY = pixel;
		} else if (this.direction === DIRECTION.DOWN) {
			moveY = -pixel;
		} else if (this.direction === DIRECTION.LEFT) {
			moveX = pixel;
		} else if (this.direction === DIRECTION.RIGHT) {
			moveX = -pixel;
		}

		this.animationState += 1;
	}

	context.drawImage(
		this.image,
		this.largeur * frame,
        this.direction * this.hauteur,
		this.largeur,
        this.hauteur,
		(this.x * 32) - (this.largeur / 2) + 16 + moveX,
        (this.y * 32) - this.hauteur + 32 + moveY,
		this.largeur,
        this.hauteur
	);
};

Character.prototype.nextCase = function (direction) {
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

Character.prototype.move = function (direction, map) {
    "use strict";
	if (this.animationState >= 0) {
		return false;
	}

	this.direction = direction;

	var next = this.nextCase(direction);
	if (next.x < 0 || next.y < 0 || next.x >= map.getWidth() || next.y >= map.getHeight()) {
		return false;
	}

	this.animationState = 1;

	this.x = next.x;
	this.y = next.y;

    if (lily.getState(this.x, this.y)) {
        player.die();
    }

    if (map.grass(map)) {
        player.die();
    }

    if (wood.sink(map)) {
        player.die();
    }


	return true;
};

Character.prototype.die = function () {
    "use strict";
    this.life -= 1;
    player.x = 7;
    player.y = 14;
    map.timer = 60;
    alert("you loose a life");
};