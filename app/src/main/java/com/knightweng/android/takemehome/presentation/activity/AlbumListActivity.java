package com.knightweng.android.takemehome.presentation.activity;

import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.etsy.android.grid.StaggeredGridView;
import com.knightweng.android.takemehome.R;
import com.knightweng.android.takemehome.common.QueryParams;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.domain.usecase.UseCaseFactory;
import com.knightweng.android.takemehome.presentation.activity.HomeActivity;
import com.knightweng.android.takemehome.presentation.activity.PhotoParallaxActivity;
import com.knightweng.android.takemehome.presentation.activity.PresenterActivity;
import com.knightweng.android.takemehome.presentation.adapter.AlbumListAdapter;
import com.knightweng.android.takemehome.presentation.presenter.ItemPresenter;
import com.knightweng.android.takemehome.presentation.view.CollectionView;
import com.knightweng.android.takemehome.utils.LogUtils;

public class AlbumListActivity extends PresenterActivity<ItemPresenter<PhotoItem>> implements
        CollectionView<PhotoItem>, AlbumListAdapter.OnItemClickListener {

    private static final String            LOG_TAG            = AlbumListActivity.class.getName();

    private static final String            SCROLL_POSITION    = "scroll_position";

    private int                            mScrollPosition    = 0;

    private AlbumListAdapter               mAlbumListAdapter;

    private StaggeredGridView              mStaggeredGridView;

    private RelativeLayout                 mProgressView;

    private InteractionListener<PhotoItem> mListener;

    private String                         mQuery;

    /**
     * Should not be called from outside this activity.
     */
    public AlbumListActivity() {
    }

    public static Bundle getItemBundle(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mListener = (InteractionListener) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(LOG_TAG + " must implement OnFragmentInteractionListener");
        }

        /* Added Layout */
        setContentView(R.layout.fragment_media_list);

        /* findViews */
        mStaggeredGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mProgressView = (RelativeLayout) findViewById(R.id.rl_progress);

        /* bindViews */
        mAlbumListAdapter = new AlbumListAdapter(this, new ArrayList<PhotoItem>());
        mStaggeredGridView.setAdapter(mAlbumListAdapter);
    }

    @Override
    protected ItemPresenter<PhotoItem> createPresenter() {
        LogUtils.debugLog(LOG_TAG, "createPresenter()");
        return new ItemPresenter<PhotoItem>(UseCaseFactory.newGetPhotoItemUseCaseInstance());
    }

    private void loadMediaList() {
        QueryParams queryParams = QueryParams.getNewInstance();
        queryParams.setText(mQuery);
        presenter.init(queryParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mStaggeredGridView != null && mStaggeredGridView.getAdapter() != null) {
            outState.putInt(SCROLL_POSITION, mScrollPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.debugLog(LOG_TAG, item.getItemId() + " ");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void renderCollection(Collection<PhotoItem> photoItems) {
        if (photoItems != null && mAlbumListAdapter != null) {
            mAlbumListAdapter.setCollection(photoItems);
            mAlbumListAdapter.notifyDataSetChanged();
            mAlbumListAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void viewItem(PhotoItem photoItem) {
        mListener.onItemClick(photoItem);
    }

    @Override
    public void showLoading() {
        this.mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        mAlbumListAdapter.setCollection(new ArrayList<PhotoItem>());
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        LogUtils.errorLog(LOG_TAG, message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClicked(View v, PhotoItem photoItem) {
        /*if (presenter != null && photoItem != null) {
            presenter.onItemClicked(photoItem);
        }*/
        Log.d("PhotoItem", photoItem.mId + " " + photoItem.mFrom + " " + photoItem.mImages + " " + photoItem.mCoverPhoto + " " + photoItem.mVideoPreviewPic);

        Intent intent = new Intent();
        intent.setClass(this, PhotoParallaxActivity.class);
        startActivity(intent);
    }
}
