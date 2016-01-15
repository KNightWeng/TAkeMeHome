package com.knightweng.android.takemehome.data.repository;

import java.util.List;

public interface ContentRepository<T> {


    /**
     * Get search results for query
     *
     * @return List<T>
     */
    List<T> getPhotos();

    List<T> getAlbums();

    List<T> getVideos();
}
