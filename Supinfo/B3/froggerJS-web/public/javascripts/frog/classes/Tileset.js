/*jslint browser : true devel : true*/
/*globals Image*/
function Tileset(url) {
    "use strict";
	this.image = new Image();
	this.image.referenceDuTileset = this;
	this.image.onload = function () {
		if (!this.complete) {
			throw new Error("Erreur de chargement du tileset nommé \"" + url + "\".");
        }
		this.referenceDuTileset.largeur = this.width / 32;
	};
	this.image.src = "/img/frog/tilesets/" + url;
}

Tileset.prototype.drawTile = function (numero, context, xDestination, yDestination) {
    "use strict";
	var xSourceEnTiles = numero % this.largeur;
	if (xSourceEnTiles === 0) {
        xSourceEnTiles = this.largeur;
    }
	var ySourceEnTiles = Math.ceil(numero / this.largeur);

	var xSource = (xSourceEnTiles - 1) * 32;
	var ySource = (ySourceEnTiles - 1) * 32;

	context.drawImage(this.image, xSource, ySource, 32, 32, xDestination, yDestination, 32, 32);
};
