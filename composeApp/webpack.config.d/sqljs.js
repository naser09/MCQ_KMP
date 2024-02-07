// {project}/webpack.config.d/sqljs.js
config.resolve = {
    fallback: {
        fs: false,
        path: false,
        crypto: false,
    }
};

const CopyWebpackPlugin = require('copy-webpack-plugin');
config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            '../../../js/node_modules/sql.js/dist/sql-wasm.wasm',
            '../../../js/node_modules/@eonic.ai/absurd-sql/dist'
        ]
    })
);