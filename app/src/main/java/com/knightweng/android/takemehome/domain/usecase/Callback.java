package com.knightweng.android.takemehome.domain.usecase;

public interface Callback<T> {

    void onSuccess(T obj);

    void onError(Exception ex);
}
