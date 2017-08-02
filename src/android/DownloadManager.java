package downloadmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * This class echoes a string called from JavaScript.
 */
public class DownloadManager extends CordovaPlugin {
    
    protected static final String LOG_TAG = "Plugin_DownloadManager";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("download")) {
            String url = args.getString(0);
            //LOG.d(LOG_TAG, "url: " + url);
            String description = args.getString(1);
            //LOG.d(LOG_TAG, "description: " + description);
            this.startDownload(url, description, callbackContext);
            return true;
        }
        return false;
    }

    private void startDownload(String url, String description, CallbackContext callbackContext) {
        if (url != null && url.length() > 0) {
            String filename = url.substring(url.lastIndexOf("/")+1, url.length());
            try {
                filename = URLDecoder.decode(filename,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                callbackContext.error("Error in converting filename");
            }
            android.app.DownloadManager downloadManager = (android.app.DownloadManager) cordova.getActivity().getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);            
            Uri Download_Uri = Uri.parse(url);
            android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Download_Uri);
            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(android.app.DownloadManager.Request.NETWORK_WIFI | android.app.DownloadManager.Request.NETWORK_MOBILE);
            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);
            //Set the title of this download, to be displayed in notifications (if enabled).
            //LOG.d(LOG_TAG, "setTitle: " + filename);
            request.setTitle(filename);
            //Set a description of this download, to be displayed in notifications (if enabled)
            //LOG.d(LOG_TAG, "setDescription: " + description);
            request.setDescription(description);
            //Set the local destination for the downloaded file to a path within the application's external files directory            
            request.setDestinationInExternalFilesDir(cordova.getActivity().getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, filename);
            //Set visiblity after download is complete
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long downloadReference = downloadManager.enqueue(request);
            callbackContext.success(url);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}