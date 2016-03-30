package com.knightweng.android.takemehome.data.datasource;

import java.util.List;


public interface ItemDataSource<T> {

    //List<T> getPhotos();

    List<T> getAlbumPhotos(String id);

    List<T> getAlbums();

    List<T> getVideos();
}
