package com.knightweng.android.takemehome.domain.interactor;

public interface Interactor extends Runnable {
    /**
     * Everything inside this method will be executed asynchronously.
     */
    void run();
}