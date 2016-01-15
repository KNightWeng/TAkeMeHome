package com.knightweng.android.takemehome.domain.usecase;

import com.knightweng.android.takemehome.data.repository.RepositoryFactory;
import com.knightweng.android.takemehome.domain.interactor.GetItemsUseCase;
import com.knightweng.android.takemehome.executor.ExecutorFactory;

public class UseCaseFactory {

    public static GetItemsUseCase newGetPhotoItemUseCaseInstance() {
        return new GetPhotoItemsUseCase(RepositoryFactory.getPhotosRepositoryInstance(),
                ExecutorFactory.getThreadExecutorInstance());
    }
}
