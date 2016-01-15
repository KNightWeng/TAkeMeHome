package com.knightweng.android.takemehome.data.repository;

public class RepositoryFactory {

    private static ContentRepository sPhotoRepository;

    public static synchronized ContentRepository getPhotosRepositoryInstance() {
        if (sPhotoRepository == null) {
            sPhotoRepository = new ItemRepository();
        }
        return sPhotoRepository;
    }
}
