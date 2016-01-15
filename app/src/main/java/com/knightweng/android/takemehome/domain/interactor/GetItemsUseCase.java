package com.knightweng.android.takemehome.domain.interactor;


import com.knightweng.android.takemehome.common.QueryParams;
import com.knightweng.android.takemehome.domain.usecase.Callback;
import com.knightweng.android.takemehome.executor.PostExecutionThread;

public interface GetItemsUseCase<T> extends Interactor {

    void getItem(QueryParams queryParams, PostExecutionThread postExecutionThread, Callback<T> callback, boolean async,
            boolean applyUserState);

}
