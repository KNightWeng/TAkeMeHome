package com.knightweng.android.takemehome.common;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.RequestFuture;
import com.knightweng.android.takemehome.domain.dto.ParsingObject;
import com.knightweng.android.takemehome.utils.*;

public class ApiUtils extends RequestUtils {

    private static final String LOG_TAG = "API_UTILS";

    /**
     * Sync Http call. Waits on current thread. DO NOT USE SYNC CALLS FROM UI THREAD!
     *
     * @param t
     * @param context
     * @param url
     * @param timeout
     * @param listener
     * @param <T>
     */
    public static <T extends ParsingObject> void makeSyncGetJsonRequest(T t, final Context context, String url,
            int timeout, ApiResponseListener<T> listener) {
        RequestFuture<T> future = RequestFuture.newFuture();
        LogUtils.warnLog(LOG_TAG, "makeSyncGetJsonRequest()::jsonRequest = " + url);
        JsonRequest<?> request = getRequest(t, context, Request.Method.GET, url, null, future, future);
        request.setRetryPolicy(new DefaultRetryPolicy(getTimeout(context), 0, 0));
        VolleyLib.getRequestQueue().add(request);
        try {
            //noinspection unchecked
            t = (T) getFuture(future, context, timeout);
            listener.onResponse(t);
        } catch (Exception ex) {
            LogUtils.errorLog(LOG_TAG, "Failed to make sync get json request", ex);
            listener.onError(ex);
        }
    }
}