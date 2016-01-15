package com.knightweng.android.takemehome.utils;

import android.content.Context;
import android.os.Build;

import com.knightweng.android.takemehome.domain.dto.ParsingObject;

import org.json.JSONObject;

public class Utils {

    private static String LOG_TAG = "UTILS";

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static int dpToPixels(Context context, float dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static <T> T fromJsonObject(JSONObject jsonObject, Object object) throws Exception {
        T obj = null;
        if (object instanceof JSONObject) {
            obj = (T) jsonObject;
        } else if (object instanceof ParsingObject) {
            try {
                obj = ((ParsingObject) object).fromJsonObject(jsonObject);
            } catch (Exception e) {
                LogUtils.errorLog(LOG_TAG, "Exception Parsing: ", e);
            }
        } else {
            throw new Exception("The object should implement ParsingObject");
        }
        return obj;
    }

}
