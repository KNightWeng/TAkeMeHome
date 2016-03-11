package com.knightweng.android.takemehome.presentation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knightweng.android.takemehome.presentation.activity.BaseActivity;
import com.knightweng.android.takemehome.presentation.activity.MyApplication;
import com.knightweng.android.takemehome.utils.LogUtils;

public abstract class BaseFragment extends Fragment {

    private static final String LOG_TAG = "BASE_FRAGMENT";

    protected BaseActivity      mActivity;

    protected MyApplication     mApplication;

    /*
     * Method to standardize tags of all fragments
     */
    public abstract String getFragmentTag();

    /*
     * Use this method instead of getmActivityActivity() if needed Otherwise, always use
     * mActivity
     */
    public BaseActivity getmActivity() {
        return mActivity;
    }

    public void onNewBundle(Bundle bundle) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onNewBundle(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        if (getArguments() != null && bundle != null)
            getArguments().putAll(bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onAttach(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onCreate(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) mActivity.getApplicationContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        LogUtils.debugLog(LOG_TAG, "onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onCreateView(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onViewCreated(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onActivityCreated(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onStart(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onResume(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onPause(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onStop(): " + this.getClass().getSimpleName() + ": " + getFragmentTag());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onDestroyView(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onDestroy(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtils.debugLog(LOG_TAG, "[LIFECYCLE] onDetach(): " + this.getClass().getSimpleName() + ": "
                + getFragmentTag());
        mActivity = null;
        super.onDetach();
    }

}
