package com.knightweng.android.takemehome.data.repository;

import com.knightweng.android.takemehome.data.datasource.CloudStore;
import com.knightweng.android.takemehome.data.datasource.ItemDataSource;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;

import java.util.List;

public class ItemRepository implements ContentRepository<PhotoItem> {

    private static final String LOG_TAG = "ITEM_REPOSITORY";

    ItemDataSource              mCloud  = new CloudStore();

    @Override
    public List<PhotoItem> getPhotos() {
        return mCloud.getPhotos();
    }

    @Override
    public List<PhotoItem> getAlbums() {
        return mCloud.getAlbums();
    }

    @Override
    public List<PhotoItem> getVideos() {
        return mCloud.getVideos();
    }
}
