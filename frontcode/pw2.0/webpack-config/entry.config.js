var path = require('path');
var dirVars = require('./base/dir-vars.config.js');
var configEntry = {
  app: path.resolve(dirVars.srcRootDir, './entry'),
  vendors: ['jquery','bootstrap','jquery-mockjax']
};
module.exports = configEntry;
