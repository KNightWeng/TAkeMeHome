package com.knightweng.android.takemehome.domain.usecase;

import com.knightweng.android.takemehome.executor.PostExecutionThread;
import com.knightweng.android.takemehome.executor.ThreadExecutor;

import java.util.concurrent.Future;

@SuppressWarnings("unchecked")
public class BaseUseCase {
    protected boolean mAsync = true;
    protected boolean mApplyUserState;
    protected PostExecutionThread mPostExecutionThread;
    protected ThreadExecutor mThreadExecutor;
    protected Callback mCallback;
    protected Future mFuture;

    protected void notifyOnSuccess(final Object obj) {
        if (mPostExecutionThread != null) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    if (mCallback != null) {
                        mCallback.onSuccess(obj);
                    }
                }
            });
        } else {
            if (mCallback != null) {
                mCallback.onSuccess(obj);
            }
        }
    }

    protected void notifyOnError(final Exception exception) {
        mPostExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onError(exception);
                }
            }
        });
    }

    protected boolean isTaskRunning() {
        return mFuture != null && !(mFuture.isDone() || mFuture.isCancelled());

    }
}
