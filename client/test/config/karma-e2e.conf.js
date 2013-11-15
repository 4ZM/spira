'use strict';

module.exports = function(config){
    config.set({
        basePath : '../../',

        files : [
            'src/lib/angular/*.js',
            'src/app/app.js',
            'src/app/**/*.js',
            'test/e2e/**/*.js'
        ],

        autoWatch : false,

        browsers : ['PhantomJS', 'Firefox', 'Chrome'],

        frameworks: ['ng-scenario'],

        singleRun : true,

        urlRoot: '/karma/',

        port: 9777,

        proxies : {
            '/': 'http://localhost:80/'
        },

        plugins : [
            'karma-junit-reporter',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-phantomjs-launcher',
            'karma-jasmine',
            'karma-ng-scenario'
        ],

        junitReporter : {
            outputFile: 'test_out/e2e.xml',
            suite: 'e2e'
        }

    });};
