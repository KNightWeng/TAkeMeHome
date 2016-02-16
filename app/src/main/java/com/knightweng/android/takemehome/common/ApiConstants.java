package com.knightweng.android.takemehome.common;

import com.facebook.AccessToken;

public class ApiConstants {

    public interface Photo {
        String ID            = "id";
        String NAME          = "name";
        String SOURCE        = "source";
        String FROM          = "from";
        String HEIGHT        = "height";
        String WIDTH         = "width";
        String IMAGES        = "images";
        String COVER_PHOTO   = "cover_photo";
        String VIDEO_PREVIEW = "picture";
    }

    public interface Page {
        String CURSORS       = "cursors";
        String AFTER         = "after";
    }

    public static String getPhotoUrl() {
        return getPhotoUrl(5);
    }

    private static String getPhotoUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.3/1521786444750433/photos?fields=id,from,images&limit=" + pageSize + "&access_token="
                + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getAlbumUrl() { return getAlbumUrl(50); }

    private static String getAlbumUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.3/1521786444750433/albums?fields=id,cover_photo&limit=" + pageSize + "&access_token=" + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getAlbumNextPageUrl(String hashCode) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.3/1521786444750433/albums?fields=id,cover_photo&limit=50&after=" + hashCode + "&access_token=" + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getCoverPhotoUrl(String id) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.3/" + id + "/picture?access_token="
                + AccessToken.getCurrentAccessToken().getToken();
    }

    public static String getVideoUrl() {
        return getVideoUrl(5);
    }

    private static String getVideoUrl(int pageSize) {
        AccessToken.getCurrentAccessToken().getToken();
        return "https://graph.facebook.com/v2.3/1521786444750433/videos?fields=id,from,picture&limit=" + pageSize + "&access_token="
                + AccessToken.getCurrentAccessToken().getToken();
    }

}
