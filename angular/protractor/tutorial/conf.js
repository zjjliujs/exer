// conf.js
exports.config = {
  specs: ['spec1.js'],

  capabilities: {
    'browserName': 'chrome'
  },

  chromeOnly: true,

  framework: 'jasmine',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  }
}
