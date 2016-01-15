package com.knightweng.android.takemehome.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.knightweng.android.takemehome.R;
import com.knightweng.android.takemehome.presentation.fragment.PagerFragment;
import com.knightweng.android.takemehome.presentation.view.BaseView;
import com.knightweng.android.takemehome.utils.LogUtils;
import com.knightweng.android.takemehome.utils.NavigationUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity implements FacebookCallback<LoginResult>, BaseView.InteractionListener<Object> {

    private static final String LOG_TAG          = "HOME_ACTIVITY";

    CallbackManager             mCallbackManager;

    LoginButton                 mLoginButton;

    List<String>                mPermissionReadNeeds = Arrays.asList("user_photos");

    List<String>                mPermissionPublishNeeds = Arrays.asList("manage_pages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_home);
        mLoginButton = (LoginButton) findViewById(R.id.b_login);
        mLoginButton.setPublishPermissions(mPermissionPublishNeeds);
        //mLoginButton.setReadPermissions(mPermissionReadNeeds);
        mLoginButton.registerCallback(mCallbackManager, this);
        onAccessTokenChanged(AccessToken.getCurrentAccessToken(), AccessToken.getCurrentAccessToken());
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                onAccessTokenChanged(oldAccessToken, newAccessToken);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LogUtils.debugLog(LOG_TAG, "Login Successful " + loginResult.getAccessToken().toString());
    }

    @Override
    public void onCancel() {
        LogUtils.debugLog(LOG_TAG, "Login cancel ");
    }

    @Override
    public void onError(FacebookException e) {
        LogUtils.errorLog(LOG_TAG, "Login failed " + e.toString());
    }

    private void onAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
        if (newAccessToken != null) {
            navigateToFragment();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void navigateToFragment() {
        NavigationUtils.startFragment(this.getSupportFragmentManager(), R.id.fl_fragment_container,
                PagerFragment.newInstance(), false, NavigationUtils.NO_ANIMATION);
    }

    @Override
    public void onItemClick(Object item) {

    }
}
