var exec = require('cordova/exec');

exports.download = function(url, description, success, error) {
    exec(success, error, "DownloadManager", "download", [url, description]);
};