var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var path = require('path');
var dirVars = require('../base/dir-vars.config.js');

var configPlugins = [
  /* 全局shimming */
  new webpack.ProvidePlugin({
    $: 'jquery',
    jQuery: 'jquery',
    'window.jQuery': 'jquery',
    'window.$': 'jquery',
  }),
  new webpack.optimize.CommonsChunkPlugin({ name: 'vendors', filename: 'vendors/vendor.js' }),
];

module.exports = configPlugins;
