package com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 20, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class InputStreamVolleyRequest extends Request<byte[]> {

    public InputStreamVolleyRequest(int post, String url, Response.Listener<byte[]> listener, Response.ErrorListener errListener, HashMap<String, String> params) {
        super(post, url, errListener);
    }
    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(byte[] response) {

    }
}
