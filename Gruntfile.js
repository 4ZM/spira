'use strict';

module.exports = function(grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        jshint: {
            files: ['Gruntfile.js', '<%= pkg.directories.webapp %>/js/*.js'],
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
                configFile: 'presentation/config/karma.conf.js',
            },

            continuous: {
                configFile: 'presentation/config/karma.conf.js',
                singleRun: true,
                browsers: ['PhantomJS']
            },
        },

        // shell: {                                // Task
        //     listFolders: {                      // Target
        //         options: {                      // Options
        //             stdout: true
        //         },
        //         command: 'ls'
        //     }
        // },

        bower: {
            install: {
                options: {
                    targetDir: 'presentation/app/lib',
                    layout: 'byComponent',
                    install: true,
                    verbose: false,
                    cleanTargetDir: false,
                    cleanBowerDir: true
                }
            }
        },

        clean: {
            release: ['<%= pkg.directories.release %>'],
            tmp: ['**/#*#', '**/*~']
        },
        copy: {
            release: {
                files: [
                    { expand: true,
                      cwd: '<%= pkg.directories.webapp %>/',
                      src: ['**/*.html'],
                      dest: '<%= pkg.directories.release %>'
                    },
                ]
            }
        },
        useminPrepare: {
            html: ['<%= pkg.directories.webapp %>/index.html'],
            options: {
                dest: '<%= pkg.directories.release %>'
            }
        },
        usemin: {
            html: ['<%= pkg.directories.release %>/index.html'],
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
    // grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-usemin');
    
    // Default task(s).
    grunt.registerTask('default', ['jshint']);

    grunt.registerTask('deploy', ['clean', 'copy', 'useminPrepare', 'concat', 'uglify', 'usemin']);
    //grunt.registerTask('foo', ['concat', 'jshint']);
};
