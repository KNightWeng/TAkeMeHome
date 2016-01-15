package com.knightweng.android.takemehome.data.datasource;

import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.presentation.activity.MyApplication;
import com.knightweng.android.takemehome.utils.RequestUtils;

import java.util.List;

public class CloudStore implements ItemDataSource<PhotoItem> {

    @Override
    public List<PhotoItem> getPhotos() {
        List<PhotoItem> results;
        results = RequestUtils.getPhotos(MyApplication.getMyApplicationContext());
        return results;
    }

    @Override
    public List<PhotoItem> getAlbums() {
        List<PhotoItem> results;
        results = RequestUtils.getAlbums(MyApplication.getMyApplicationContext());
        return results;
    }

    @Override
    public List<PhotoItem> getVideos() {
        List<PhotoItem> results;
        results = RequestUtils.getVideos(MyApplication.getMyApplicationContext());
        return results;
    }
}
