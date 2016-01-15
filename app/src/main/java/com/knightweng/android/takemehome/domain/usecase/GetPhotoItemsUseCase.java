package com.knightweng.android.takemehome.domain.usecase;

import com.knightweng.android.takemehome.common.QueryParams;
import com.knightweng.android.takemehome.data.repository.ContentRepository;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.domain.interactor.GetItemsUseCase;
import com.knightweng.android.takemehome.executor.PostExecutionThread;
import com.knightweng.android.takemehome.executor.ThreadExecutor;
import com.knightweng.android.takemehome.utils.LogUtils;

import java.util.List;

public class GetPhotoItemsUseCase extends BaseUseCase implements GetItemsUseCase<List<PhotoItem>> {

    private static final String     LOG_TAG = "GET_PHOTO_ITEMS_USE_CASE";

    private final ContentRepository mContentRepository;

    private String                  mQuery;

    public GetPhotoItemsUseCase(ContentRepository contentRepository, ThreadExecutor executor) {
        mContentRepository = contentRepository;
        mThreadExecutor = executor;
    }

    @Override
    public void getItem(QueryParams queryParams, PostExecutionThread postExecutionThread, Callback callback,
            boolean async, boolean applyUserState) {
        mQuery = queryParams.getTEXT();
        mCallback = callback;
        mPostExecutionThread = postExecutionThread;
        mAsync = async;
        mApplyUserState = applyUserState;
        getData();
    }

    @Override
    public void run() {
        try {
            if (mQuery.equals("photo")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getPhotos();
                notifyOnSuccess(result);
            } else if (mQuery.equals("album")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getAlbums();
                notifyOnSuccess(result);
            } else if (mQuery.equals("video")) {
                @SuppressWarnings("unchecked")
                List<PhotoItem> result = mContentRepository.getVideos();
                notifyOnSuccess(result);
            }

        } catch (Exception e) {
            LogUtils.errorLog(LOG_TAG, "Exception on background thread... ", e);
            notifyOnError(e);
        }
    }

    private void getData() {
        if (mAsync) {
            if (!isTaskRunning()) {
                mFuture = mThreadExecutor.execute(this);
            }
        } else {
            run();
        }
    }
}
