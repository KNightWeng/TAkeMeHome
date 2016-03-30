package com.knightweng.android.takemehome.presentation.fragment;

import java.io.Serializable;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.knightweng.android.takemehome.common.ApiConstants;
import com.knightweng.android.takemehome.common.QueryParams;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.domain.usecase.UseCaseFactory;
import com.knightweng.android.takemehome.presentation.activity.HomeActivity;
import com.knightweng.android.takemehome.presentation.activity.PhotoParallaxActivity;
import com.knightweng.android.takemehome.presentation.adapter.AlbumListAdapter;
import com.knightweng.android.takemehome.presentation.presenter.ItemPresenter;
import com.knightweng.android.takemehome.presentation.view.CollectionView;
import com.knightweng.android.takemehome.utils.LogUtils;

public class AlbumListFragment extends PresenterFragment<ItemPresenter<PhotoItem>> implements
        CollectionView<PhotoItem>, AlbumListAdapter.OnItemClickListener {

    private static final String            FRAGMENT_TAG       = AlbumListFragment.class.getName();

    private static final String            LOG_TAG            = "PHOTO_LIST_FRAGMENT";

    private static final String            SCROLL_POSITION    = "scroll_position";

    private int                            mScrollPosition    = 0;

    private AlbumListAdapter               mAlbumListAdapter;

    private StaggeredGridView                       mGridView;

    private RelativeLayout                 mProgressView;

    private InteractionListener<PhotoItem> mListener;

    private String                         mQuery;

    private String                         mFragmentTagSuffix = "";

    /**
     * Should not be called from outside this fragment.
     */
    public AlbumListFragment() {
    }

    public static Bundle getItemBundle(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        return bundle;
    }

    public static AlbumListFragment newInstance(Bundle bundle) {
        AlbumListFragment fragment = new AlbumListFragment();
        if (bundle != null) {
            fragment.mQuery = bundle.getString("query", "photo");
            fragment.setFragmentTagSuffix(fragment.mQuery);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public void setFragmentTagSuffix(String fragmentTagSuffix) {
        mFragmentTagSuffix = fragmentTagSuffix;
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG + mFragmentTagSuffix;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewBundle(getArguments());
    }

    @Override
    public void onNewBundle(Bundle bundle) {
        super.onNewBundle(bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    protected ItemPresenter<PhotoItem> createPresenter() {
        LogUtils.debugLog(LOG_TAG, "createPresenter()");
        return new ItemPresenter<PhotoItem>(UseCaseFactory.newGetPhotoItemUseCaseInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_media_list, container, false);
        findViews(v);
        bindViews();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMediaList();
        if (savedInstanceState != null) {
            int posArray = savedInstanceState.getInt(SCROLL_POSITION);
            if (posArray > 0) {
                mScrollPosition = posArray;
            }
        }
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
        mActivity.supportInvalidateOptionsMenu();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mGridView != null && mGridView.getAdapter() != null) {
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

    private void findViews(View view) {
        mGridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        mProgressView = (RelativeLayout) view.findViewById(R.id.rl_progress);
    }

    private void bindViews() {
        Log.d("AlbumListFragment", "bindViews");
        mAlbumListAdapter = new AlbumListAdapter(mActivity, new ArrayList<PhotoItem>());
        mGridView.setAdapter(mAlbumListAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        if (mActivity != null) {
            mActivity.setProgressBarIndeterminateVisibility(true);
        }
    }

    @Override
    public void hideLoading() {
        this.mProgressView.setVisibility(View.GONE);
        if (mActivity != null) {
            mActivity.setProgressBarIndeterminateVisibility(false);
        }
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
        return mApplication;
    }

    @Override
    public void onItemClicked(View v, PhotoItem photoItem) {
        /*if (presenter != null && photoItem != null) {
            presenter.onItemClicked(photoItem);
        }*/
        Log.d("PhotoItem", photoItem.mId);

        Intent intent = new Intent();
        intent.putExtra("PhotoItem", (Serializable) photoItem);
        intent.setClass(getActivity(), PhotoParallaxActivity.class);
        startActivity(intent);
    }
}
