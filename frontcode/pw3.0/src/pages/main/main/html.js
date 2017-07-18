const noJquery = require('withoutJqueryModule');
const layout = require('layout-without-nav');
const content = require('./content.ejs');

const pf = {
  constructInsideUrl: noJquery.constructInsideUrl,
};

const renderData = Object.assign({}, pf);

module.exports = layout.init({
  pageTitle: '',
}).run(content(renderData));

// module.exports = content;
