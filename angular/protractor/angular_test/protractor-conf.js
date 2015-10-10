exports.config = {
  allScriptsTimeout: 11000,

  specs: [
    'e2e/expression/angular_context_test.js',
    'e2e/expression/one_time_binding_test.js',
    'e2e/directive/normalization_test.js',
    'e2e/expression/simple_test.js',
    'e2e/expression/expr_eval_test.js',
  ],

  capabilities: {
    'browserName': 'chrome'
  },

  chromeOnly: true,

  baseUrl: 'http://localhost:8080/',

  framework: 'jasmine',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  }
};
