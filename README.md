# cordova-plugin-downloadmanager
A Cordova plugin to download file in system's default download manager

## Supported Platforms

- Android (SDK >= 11)

## Installation

```
cordova plugin add https://github.com/Mhitra/cordova-plugin-downloadmanager
```

## How to Use 

```
//once device is ready
window.cordova.plugins.DownloadManager.download(url, description, function (success) {
    console.log('DownloadManager::download::success', success);
}, function (error) {    
    console.error('DownloadManager::download::error', error);
});
```

## Author

Original cordova plugin made by "https://github.com/vasani-arpit".

My cordova plugin is just a fork from the original cordova plugin:

"https://github.com/vasani-arpit/cordova-plugin-downloadmanager"

Thanks "vasani-arpit" for the idea!