var webpack = require('webpack');
var pluginsConfig = require('./inherit/plugins.config.js');

pluginsConfig.push(new webpack.DefinePlugin({
  IS_PRODUCTION: false,
}));

pluginsConfig.push(new webpack.LoaderOptionsPlugin({
  options: {
    postcss: require('./vendor/postcss.config.js'),
    devServer: require('./vendor/devServer.config.js'),
  },
}));

module.exports = pluginsConfig;
