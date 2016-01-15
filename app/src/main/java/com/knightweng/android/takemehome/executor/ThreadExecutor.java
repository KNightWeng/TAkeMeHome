package com.knightweng.android.takemehome.executor;

public interface ThreadExecutor {
    java.util.concurrent.Future<?> execute(final Runnable runnable);
}
