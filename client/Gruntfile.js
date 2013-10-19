'use strict';

module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        distdir: "release",
        testdir: "test",
        appdir: "app",

        pkg: grunt.file.readJSON('package.json'),

        jshint: {
            files: ['Gruntfile.js', '<%= appdir %>/js/*.js'],
            options:{
                globalstrict: true,
                globals:{
                    module: true, // grunt
                    angular: true
                },
            },
        },

        karma: {
            unit: {
                configFile: '<%= testdir %>/config/karma.conf.js',
                singleRun: true,
                browsers: ['PhantomJS']
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
            tmp: ['**/#*#', '**/*~']
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
    grunt.registerTask('default', ['clean', 'test', 'lint']);

    // Check code
    grunt.registerTask('lint', ['jshint']);

    // Run unit tests
    grunt.registerTask('test', ['karma:unit']);

    // Run before checkin - lint and test
    grunt.registerTask('prep', ['clean', 'test', 'lint']);

    // State deployment area
    grunt.registerTask('deploy', ['clean', 'copy', 'useminPrepare', 'concat', 'uglify', 'usemin']);
};
