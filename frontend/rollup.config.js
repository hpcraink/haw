'use strict';
import rollup      from 'rollup';
import nodeResolve from 'rollup-plugin-node-resolve';
import commonjs    from 'rollup-plugin-commonjs';
import uglify      from 'rollup-plugin-uglify';

export default {
  entry: 'assets/app/main.aot.js',
  dest: 'public/js/app/app.bundle.js', // output a single application bundle
  sourceMap: true,
  format: 'iife',
  onwarn: function(warning) {
    // Skip certain warnings

    // should intercept ... but doesn't in some rollup versions
    if ( warning.code === 'THIS_IS_UNDEFINED' ) { return; }

    // console.warn everything else
    console.warn( warning.message );
  },
  plugins: [
      commonjs({
        include: 'node_modules/rxjs/**'
      }),
      nodeResolve({
        jsnext: true,
        module: true
      }),
      uglify()
  ]
};
