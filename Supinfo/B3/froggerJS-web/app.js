
/**
 * Module dependencies.
 */

var express = require('express')
  , userDB = require('./models/userDB')
  , statisticDB = require('./models/statisticDB')
  , routes = require('./routes')
  , http = require('http')
  , swig = require('swig')
  , path = require('path');

var app = express();

// swig configuration
swig._cache = {};
swig.express3 = function (path, options, fn) {
  swig._read(path, options, function (err, str) {
    if (err) {
      return fn(err);
    }
    try {
      options.filename = path;
      var tmpl = swig.compile(str, options);
      fn(null, tmpl(options));
    } catch (error) {
      fn(error);
    }
  });
};

swig._read = function (path, options, fn) {
  var str = swig._cache[path];

  // cached (only if cached is a string and not a compiled template function)
  if (options.cache && str && typeof str === 'string') {
    return fn(null, str);
  }

  // read
  require('fs').readFile(path, 'utf8', function (err, str) {
    if (err) {
      return fn(err);
    }
    if (options.cache) {
      swig._cache[path] = str;
    }
    fn(null, str);
  });
};

swig.init({
  root: __dirname + '/views',
  allowErrors: true
});



// all environments
app.engine('html', swig.express3);
app.set('view engine', 'html');
app.set('views', __dirname + '/views');
app.set('view options', { layout: false });
app.set('view cache', false);

app.set('port', process.env.PORT || 3000);
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(express.cookieParser('your secret here'));
app.use(express.session());
app.use(app.router);
app.use(require('stylus').middleware(__dirname + '/public'));
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

app.get('/', routes.indexShow);
app.get('/register', routes.registerShow);
app.post('/register', routes.registerAction);
app.post('/login', routes.loginAction);
app.get('/logout', routes.logoutAction);

app.get('/dashboard', routes.dashboardShow);
app.get('/play', routes.playShow);
app.post('/play', routes.playAction);
app.get('/user', routes.userShow);
app.get('/statistic', routes.statShow);

app.get('/users', routes.usersShow);


http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
