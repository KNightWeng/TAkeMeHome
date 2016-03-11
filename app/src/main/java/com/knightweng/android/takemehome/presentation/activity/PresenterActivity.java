package com.knightweng.android.takemehome.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knightweng.android.takemehome.presentation.activity.BaseActivity;
import com.knightweng.android.takemehome.presentation.presenter.BasePresenter;
import com.knightweng.android.takemehome.presentation.view.BaseView;
import com.knightweng.android.takemehome.utils.LogUtils;


public abstract class PresenterActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    /**
     * The presenter for this view. Will be instantiated with
     * {@link #createPresenter()}
     */
    protected P presenter;

    /**
     * Creates a new presenter instance, if needed. Will reuse the previous
     * presenter instance if {@link #setRetainInstance(boolean)} is set to true.
     * This method will be called after from
     * {@link #onViewCreated(android.view.View, android.os.Bundle)}
     */
    protected abstract P createPresenter();

    @Override
    public void onStart() {
        super.onStart();

        // Create the presenter if needed
        if (presenter == null) {
            presenter = createPresenter();

            if (presenter == null) {
                throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
            }
        }
        presenter.attachView(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        LogUtils.debugLog("PresentActivity", "setContentView()");
        super.setContentView(layoutResID);
    }

    /**
     * Return the layout resource like R.layout.my_layout
     *
     * @return the layout resource or null, if you don't want to have an UI
     */
    protected int getLayoutRes() {
        return 0;
    }

}
