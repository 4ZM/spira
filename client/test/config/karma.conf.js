'use strict';

module.exports = function(config){
    config.set({
        basePath : '../../',

        files : [
            'src/lib/angular/*.js',
            'test/lib/angular/angular-mocks.js',
            'src/app/app.js',
            'src/app/**/*.js',
            'test/unit/**/*.js'
        ],

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['PhantomJS', 'Firefox', 'Chrome'],

        plugins : [
            'karma-junit-reporter',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-phantomjs-launcher',
            'karma-jasmine'
        ],

        junitReporter : {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }
    });};
