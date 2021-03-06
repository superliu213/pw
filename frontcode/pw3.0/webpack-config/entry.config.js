var path = require('path');
var dirVars = require('./base/dir-vars.config.js');
var pageArr = require('./base/page-entries.config.js');
var configEntry = {};

pageArr.forEach((page) => {
  configEntry[page] = path.resolve(dirVars.pagesDir, page + '/page');
  // console.log(page);
  // console.log(configEntry[page] );
});

// console.log(configEntry);

module.exports = configEntry;
