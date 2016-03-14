
/*
 * GET home page.
 */
var mongoose = require( 'mongoose' );
var ObjectId = mongoose.Types.ObjectId; 
var User = mongoose.model( 'User' );
var Statistic = mongoose.model( 'Statistic' );
var utils = require( 'connect' ).utils;
var crypto = require('crypto');

exports.indexShow = function ( req, res, next ){
  res.render( 'index', {
      title : 'Home',
      req   : req,
  });
};

exports.registerShow = function ( req, res, next ){
    res.render( 'register', {
        title : 'Register',
        req   : req
    });
};

exports.registerAction = function ( req, res, next ){
  new User({
      mail        : req.body.mail,
      password    : crypto.createHmac('sha1', "bibinoulelapinou").update(req.body.password).digest('hex'),
      isAdmin     : 0,
      updated_at  : Date.now()
  }).save( function ( err, user, count ){
    if( err ) {
      res.render( 'register', {
        title : 'Register',
        req   : req,
        error : err
      });
    }
    else {
      res.render( 'register', {
        title : 'Register',
        req   : req,
        success : "Votre compte ("+req.body.mail+") vient d'être crée avec succès !"
      });
    }
  });
};

exports.loginAction = function (req, res, next) {
  User.
    findOne({mail: req.body.mail, password: crypto.createHmac('sha1', "bibinoulelapinou").update(req.body.password).digest('hex')}).
    sort( '-updated_at' ).
    exec( function ( err, user ){
      if( err ) return next( err );

      if ( user ) { 
        req.session.userId = user._id;
        if(user.isAdmin) req.session.userAdmin = 1;
        res.redirect('/dashboard');
      }
      else {
        res.redirect('/');
      }
    });
};

exports.logoutAction = function (req, res, next) {
  req.session.destroy();
  res.redirect('/');
}

exports.dashboardShow = function ( req, res, next ){
  if(!req.session.userId) res.redirect('/');
  Statistic.
    find({ 'userId': req.session.userId}).
    sort( '-updated_at' ).
    count().
    exec( function ( err, total ){
      if( err ) return next( err );
      Statistic.
        find({ 'userId': req.session.userId, 'result': "win"}).
        sort( '-updated_at' ).
        count().
        exec( function ( err, win ){
          if( err ) return next( err );

        res.render( 'dashboard', {
            title : 'Dashboard',
            req   : req,
            total : total,
            win   : win,
            loose : total - win
        });
      });
    });
};

exports.playShow = function ( req, res, next ){
  if(!req.session.userId) res.redirect('/');
    res.render( 'play', {
        title : 'Play',
        req   : req
    });
};

exports.playAction = function ( req, res, next ){
  new Statistic({
      result      : req.body.result,
      lilies      : req.body.lilies,
      time        : req.body.time,
      best        : req.body.best,
      userId      : req.session.userId,
      updated_at  : Date.now()
  }).save( function ( err, statistic, count ){
    if( err ) {
      res.redirect("/");
    }
    else {
      res.render( 'play', {
        title : 'play',
        req   : req,
      });
    }
  });
};

exports.userShow = function ( req, res, next ){
  if(!req.session.userId) res.redirect('/');
    User.
    find().
    sort( '-updated_at' ).
    exec( function ( err, users ){
      if( err ) return next( err );

      res.render( 'user', {
          title : 'Users',
          req   : req,
          users : users
      });
    });
};

exports.statShow = function ( req, res, next ){
  if(!req.session.userId) res.redirect('/');
  Statistic.
    find({ 'userId': req.session.userId}).
    sort( '-updated_at' ).
    exec( function ( err, stats ){
      if( err ) return next( err );

      res.render( 'statistic', {
          title : 'Statistics',
          req   : req,
          stats : stats
      });
    });
};

exports.usersShow = function ( req, res, next ){
    User.
    find().
    sort( '-updated_at' ).
    exec( function ( err, users ){
      if( err ) return next( err );

      res.json(users);
    });
};