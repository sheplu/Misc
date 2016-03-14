var mongoose = require( 'mongoose' );
var Schema   = mongoose.Schema;
 
var User = new Schema({
    mail    	: { type: String, index: { unique : true}},
    password	: String,
    isAdmin		: Boolean,
    updated_at 	: Date
});
 
mongoose.model( 'User', User );
 
mongoose.connect( 'localhost','FroggerJS' );