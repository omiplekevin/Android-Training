package com.android.omiplekevin.workoutheaders.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   May 16, 2017<br/>
 * IN CLASS:        com.f45tv.f45powerandroid.helper<br/>
 * <br/>
 * Provides information about the connection status of the application during runtime
 * <br/>
 **/
public class NetworkUtilities {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static final String
            NETWORK_STATUS_NOT_CONNECTED = "NOT CONNECTED",
            NETWORK_STAUS_WIFI = "WIFI",
            NETWORK_STATUS_MOBILE = "MOBILE";

    /**
     * DEVELOPER:       OMIPLEKEVIN<br/>
     * LAST MODIFIED:   May 16, 0016<br/>
     * IN CLASS:        NetworkUtilities<br/>
     * <br/>
     * get current connection status / type of the app
     * <br/>
     *
     * @param context
     * @return <code>1</code> - return as Wifi Connectivity<br/>
     * <code>0</code> - return as Mobile Connectivity<br/>
     * Otherwise not connected<br/>
     * See {@link NetworkUtilities}
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * DEVELOPER:       OMIPLEKEVIN<br/>
     * LAST MODIFIED:   May 16, 0016<br/>
     * IN CLASS:        NetworkUtilities<br/>
     * <br/>
     * return String representation of the current connection
     * <br/>
     *
     * @param context
     * @return <code>String</code>
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtilities.getConnectivityStatus(context);
        String status = "";
        if (conn == NetworkUtilities.TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == NetworkUtilities.TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == NetworkUtilities.TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

}
