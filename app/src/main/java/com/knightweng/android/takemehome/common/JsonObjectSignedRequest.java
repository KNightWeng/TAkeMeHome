package com.knightweng.android.takemehome.common;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.knightweng.android.takemehome.utils.Utils;

import org.json.JSONObject;

public class JsonObjectSignedRequest<T> extends JsonRequest<T> {
    private final Object        mObject;

    public JsonObjectSignedRequest(Object object, int method, String url, JSONObject jsonRequest, Listener<T> listener,
            ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
        this.mObject = object;
    }

    @Override
    public com.android.volley.Request.Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            JSONObject jsonObject = new JSONObject(jsonString);
            T object = Utils.fromJsonObject(jsonObject, mObject);
            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }
}