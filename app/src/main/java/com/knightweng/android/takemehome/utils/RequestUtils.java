package com.knightweng.android.takemehome.utils;

import android.content.Context;

import com.knightweng.android.takemehome.common.ApiConstants;
import com.knightweng.android.takemehome.common.ApiUtils;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.entity.PhotosResponse;

import java.util.ArrayList;
import java.util.List;

public class RequestUtils extends ApiUtils {

    private static final String LOG_TAG = "REQUEST_UTILS";

    private static final int JSON_REQUEST_TIMEOUT = 10000;

    public static List<PhotoItem> getAlbumPhotos(Context context, String id) {
        String url = ApiConstants.getAlbumPhotoUrl(id);
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, JSON_REQUEST_TIMEOUT, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Results received");
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }

    private static PhotosResponse getAlbumByPage(Context context, String url) {
        final ResultWrapper resultWrapper = new ResultWrapper();

        makeSyncGetJsonRequest(new PhotosResponse(), context, url, JSON_REQUEST_TIMEOUT, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Results received");

                resultWrapper.setResult(response);
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (PhotosResponse) resultWrapper.getResult();
    }

    public static List<PhotoItem> getAlbums(Context context) {
        String url = ApiConstants.getAlbumUrl();
        Boolean nextPage = true;
        List<PhotoItem> photoItemList = new ArrayList<>();

        while(nextPage) {
            PhotosResponse response = getAlbumByPage(context, url);
            photoItemList.addAll(response.getResult());

            url = ApiConstants.getNextPageAlbumUrl(response.getNextPagingHashCode());
            nextPage = response.getNextPaging();

            LogUtils.debugLog(LOG_TAG, "url = " + url + " nextPage = " + nextPage );

        }

        return photoItemList;
    }

    /*public static List<PhotoItem> getAlbums(Context context) {
        String url = ApiConstants.getAlbumUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, JSON_REQUEST_TIMEOUT, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Results received");
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }*/

    public static List<PhotoItem> getVideos(Context context) {
        String url = ApiConstants.getVideoUrl();
        final ResultWrapper resultWrapper = new ResultWrapper();
        makeSyncGetJsonRequest(new PhotosResponse(), context, url, JSON_REQUEST_TIMEOUT, new ApiResponseListener<PhotosResponse>() {
            @Override
            public void onResponse(PhotosResponse response) {
                LogUtils.debugLog(LOG_TAG, "Results received");
                resultWrapper.setResult(response.getResult());
            }

            @Override
            public void onError(Exception exception) {
                LogUtils.errorLog(LOG_TAG, "Failed to fetch page. API returned: " + exception);
            }

        });
        return (List<PhotoItem>) resultWrapper.getResult();
    }


}
