module.exports ={
    devServer:{
        https:false
    }
}
//如需gz压缩打包 请将以下注释放开
/*const CompressionWebpackPlugin = require('compression-webpack-plugin');
const isProduction = process.env.NODE_ENV === 'production';
module.exports = {
    configureWebpack: config => {
        if (isProduction) {
            config.plugins.push(new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: /\.(js|css|less)$/,
                threshold: 10240,
                minRatio: 0.8,
                deleteOriginalAssets: true
            }));
            config["performance"] = {//打包文件大小配置
                "maxEntrypointSize": 10000000,
                "maxAssetSize": 30000000
            }
        }
    }
}*/
