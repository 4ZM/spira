'use strict';

module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        distdir: "release",
        testdir: "test",
        appdir: "src/app",

        pkg: grunt.file.readJSON('package.json'),

        jshint: {
            files: ['Gruntfile.js',
                    '<%= appdir %>/**/*.js',
                    '<%= testdir %>/unit/**/*.js',
                    '<%= testdir %>/e2e/**/*.js'],
            options:{
                globalstrict: true,
                globals:{
                    module: true, // Grunt
                    angular: true, // AngularJS

                    // Jasmine
                    describe: true,
                    xdescribe: true,
                    it: true,
                    xit: true,
                    expect: true,
                    beforeEach: true,
                    afterEach: true,
                    beforeAll: true,
                    afterAll: true,
                    spyOn: true,
                    runs: true,
                    waits: true,
                    waitsFor: true,

                    // Angular testing
                    // module: true,
                    inject: true,
                    browser: true,
                    element: true
                },
            },
        },

        karma: {
            unit: {
                configFile: '<%= testdir %>/config/karma.conf.js',
                singleRun: true,
            },
            unitheadless: {
                configFile: '<%= testdir %>/config/karma.conf.js',
                singleRun: true,
                browsers: ['PhantomJS']
            },
            e2eheadless: {
                configFile: '<%= testdir %>/config/karma-e2e.conf.js',
                singleRun: true,
                browsers: ['PhantomJS']
            },
            e2e: {
                configFile: '<%= testdir %>/config/karma-e2e.conf.js',
                singleRun: true,
            },
        },

        bower: {
            install: {
                options: {
                    targetDir: '<%= appdir %>/lib',
                    layout: 'byComponent',
                    install: true,
                    verbose: false,
                    cleanTargetDir: false,
                    cleanBowerDir: true
                }
            }
        },

        clean: {
            release: ['<%= distdir %>'],
            tmp: ['**/#*#', '**/*~'],
        },
        copy: {
            release: {
                files: [
                    { expand: true,
                      cwd: '<%= appdir %>/',
                      src: ['**/*.html'],
                      dest: '<%= distdir %>'
                    },
                ]
            }
        },
        useminPrepare: {
            html: ['<%= appdir %>/index.html'],
            options: {
                dest: '<%= distdir %>'
            }
        },
        usemin: {
            html: ['<%= distdir %>/index.html'],
        }
    });

    // Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-usemin');

    // Default is prep
    grunt.registerTask('default', ['clean', 'test']);

    // Check code
    grunt.registerTask('lint', ['jshint']);

    // Run tests
    grunt.registerTask('test', ['lint', 'karma:unitheadless', 'karma:e2eheadless']);

    // Run all tests
    grunt.registerTask('testall', ['lint', 'karma:unit', 'karma:e2e']);

    // Run before checkin - lint and test
    grunt.registerTask('prep', ['clean', 'testall']);

    // State deployment area
    grunt.registerTask('deploy', ['clean', 'copy', 'useminPrepare', 'concat', 'uglify', 'usemin']);
};
