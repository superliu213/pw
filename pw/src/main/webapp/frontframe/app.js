var express = require('express');
var app = express();

app.use(express.static('.'));
app.get('/examples/:project/:func', require('./examples'));

var server = app.listen(3000, function() {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Examples app listening at http://%s:%s', host, port);
});

var child_process = require("child_process");

var url = "http://127.0.0.1",
  port = 3000,
  cmd = '';

switch (process.platform) {
  case 'wind32':
    cmd = 'start';
    break;

  case 'linux':
    cmd = 'xdg-open';
    break;

  case 'darwin':
    cmd = 'open';
    break;
}

child_process.exec(cmd + ' ' + url + ':' + port + "/login.html");
