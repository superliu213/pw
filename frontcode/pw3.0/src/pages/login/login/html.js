const noJquery = require('withoutJqueryModule');
const layout = require('layout-without-nav');

const pf = {
  constructInsideUrl: noJquery.constructInsideUrl,
};

const content = require('./content.ejs');

const renderData = Object.assign({}, pf);

module.exports = layout.init({
  pageTitle: '',
}).run(content(renderData));
