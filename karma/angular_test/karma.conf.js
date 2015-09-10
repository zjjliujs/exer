module.exports = function(config){
  config.set({

    basePath : './',

    files : [
      'angular-1.3.15/angular.js',
      'angular-1.3.15/angular-mocks.js',
      'test/*.js',
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-jasmine'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }

  });
};
