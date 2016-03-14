var mongoose = require( 'mongoose' );
var Schema   = mongoose.Schema;
 
var Statistic = new Schema({
    result    	: String,
    lilies		: String,
    time		: String,
    best		: String,
    userId		: String,
    updated_at 	: Date
});
 
mongoose.model( 'Statistic', Statistic );
 
//mongoose.connect( 'localhost','FroggerJS' );