package com.knightweng.android.takemehome.presentation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.etsy.android.grid.StaggeredGridView;
import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.knightweng.android.takemehome.R;
import com.knightweng.android.takemehome.common.ApiConstants;
import com.knightweng.android.takemehome.common.QueryParams;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.domain.usecase.UseCaseFactory;
import com.knightweng.android.takemehome.presentation.adapter.AlbumListAdapter;
import com.knightweng.android.takemehome.presentation.adapter.PhotoListAdapter;
import com.knightweng.android.takemehome.presentation.fragment.PresenterFragment;
import com.knightweng.android.takemehome.presentation.presenter.ItemPresenter;
import com.knightweng.android.takemehome.presentation.slidepager.SlidePagerActivity;
import com.knightweng.android.takemehome.presentation.view.BaseView;
import com.knightweng.android.takemehome.presentation.view.CollectionView;
import com.knightweng.android.takemehome.utils.LogUtils;
import com.knightweng.android.takemehome.utils.VolleyLib;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
import com.poliveira.apps.parallaxlistview.ParallaxGridView;
import com.poliveira.apps.parallaxlistview.ParallaxScrollEvent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by knight on 2016/2/19.
 */
public class PhotoParallaxActivity extends PresenterActivity<ItemPresenter<PhotoItem>> implements
        CollectionView<PhotoItem>, PhotoListAdapter.OnItemClickListener{

    private static final String            LOG_TAG       = PhotoParallaxActivity.class.getName();

    private static final String            SCROLL_POSITION    = "scroll_position";

    private int                            mScrollPosition    = 0;

    private PhotoListAdapter               mPhotoListAdapter;

    private StaggeredGridView              mGridView;

    private ParallaxGridView               mParallaxGridView;

    private ScrollView                  mScrollView;

    private LinearLayout                   mLinearLayout;

    private RelativeLayout                 mContainer;

    private NetworkImageView               mNetworkImageView;

    private RelativeLayout                 mProgressView;

    private InteractionListener<PhotoItem> mListener;

    private PhotoItem                      mPhotoItem;

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LogUtils.debugLog(LOG_TAG, " onCreate()");

        Intent intent = getIntent();
        mPhotoItem = (PhotoItem) intent.getSerializableExtra("PhotoItem");

        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(mPhotoItem.mId);
        //try {
        //    mListener = (InteractionListener) this;
        //} catch (ClassCastException e) {
        //    throw new ClassCastException(LOG_TAG + " must implement OnFragmentInteractionListener");
        //}
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_album_layout);

        mContainer = (RelativeLayout) findViewById(R.id.album_relativeLayout);

        mContainer.removeAllViews();

        /*MyFadingActionBarHelper helper = new MyFadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_color)
                .headerLayout(R.layout.album_view_header)
                .contentLayout(R.layout.activity_album_layout)
                .lightActionBar(true);
        setContentView(helper.createView(this));
        helper.initActionBar(this);*/

        final View v2 = getLayoutInflater().inflate(R.layout.album_view_header, mParallaxGridView, true);
        final ImageView mImageView = (ImageView) v2.findViewById(R.id.album_view_header_imageview);
        Picasso.with(getContext())
                .load(ApiConstants.getAlbumCoverPhotoUrl(mPhotoItem.mCoverPhoto))
                .transform(new CircleTransform())
                .into(mImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Parallax", "onSuccess");
                        if(mPhotoListAdapter != null)
                            mPhotoListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), "Load Header image error !!", Toast.LENGTH_LONG);
                    }
                });

        final View v = getLayoutInflater().inflate(R.layout.activity_album_layout, mContainer, true);
        ParallaxGridView mParallaxGridView = (ParallaxGridView) v.findViewById(R.id.view);
        mPhotoListAdapter = new PhotoListAdapter(PhotoParallaxActivity.this, new ArrayList<PhotoItem>());
        mParallaxGridView.setParallaxView(v2);
        mParallaxGridView.setAdapter(mPhotoListAdapter);
        mParallaxGridView.setNumColumns(2);
        /*StaggeredGridView mStaggeredGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mPhotoListAdapter = new PhotoListAdapter(PhotoParallaxActivity.this, new ArrayList<PhotoItem>());
        mStaggeredGridView.setAdapter(mPhotoListAdapter);*/


        //final View v = getLayoutInflater().inflate(R.layout.activity_album_layout, mContainer, true);

        //NetworkImageView mNetworkImageView = (NetworkImageView) findViewById(R.id.album_view_header_networkimageview);
        //Picasso.with(getContext()).load(ApiConstants.getCoverPhotoUrl(mCoverPhoto)).into(mNetworkImageView);

        /*mPEWImageView = (PEWImageView) findViewById(R.id.album_pewimageview);
        Picasso.with(getContext()).load(ApiConstants.getCoverPhotoUrl(mCoverPhoto)).into(mPEWImageView);
        //mPEWImageView.setImageUrl(ApiConstants.getCoverPhotoUrl(mCoverPhoto), VolleyLib.getImageLoader());

        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mLinearLayout = (LinearLayout) findViewById(R.id.album_linearlayout);
        mScrollView = (ScrollView) findViewById(R.id.album_scrollview);

        mPhotoListAdapter = new PhotoListAdapter(this, new ArrayList<PhotoItem>());
        mGridView.setAdapter(mPhotoListAdapter);

        mLinearLayout.removeAllViews();
        mLinearLayout.addView(mPEWImageView);
        mLinearLayout.addView(mGridView);*/
        /*mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.album_view_header_networkimageview);
        Picasso.with(getContext()).load(ApiConstants.getCoverPhotoUrl(mCoverPhoto)).into(mNetworkImageView);

        mPhotoListAdapter = new PhotoListAdapter(this, new ArrayList<PhotoItem>());
        mGridView.setAdapter(mPhotoListAdapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    protected ItemPresenter<PhotoItem> createPresenter() {
        LogUtils.debugLog(LOG_TAG, "createPresenter()");
        return new ItemPresenter<PhotoItem>(UseCaseFactory.newGetPhotoItemUseCaseInstance());
    }

    private void loadMediaList(String id) {
        QueryParams queryParams = QueryParams.getNewInstance();
        queryParams.setText("albumphotos");
        queryParams.setData(id);
        presenter.init(queryParams);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
        supportInvalidateOptionsMenu();

        loadMediaList(mPhotoItem.mId);
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
        mListener = null;
    }

    @Override
    public void renderCollection(Collection<PhotoItem> photoItems) {
        if (photoItems != null && mPhotoListAdapter != null) {
            mPhotoListAdapter.setCollection(photoItems);
            mPhotoListAdapter.notifyDataSetChanged();
            mPhotoListAdapter.setOnItemClickListener(this);

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
        mPhotoListAdapter.setCollection(new ArrayList<PhotoItem>());
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
        return getApplicationContext();
    }

    @Override
    public void onItemClicked(View v, PhotoItem photoItem) {
        /*if (presenter != null && photoItem != null) {
            presenter.onItemClicked(photoItem);
        }*/
        Log.d(LOG_TAG, photoItem.mId + " " + photoItem.mFrom + " " + photoItem.mImages + " " + photoItem.mCoverPhoto + " " + photoItem.mVideoPreviewPic);

        Intent intent = new Intent(this, SlidePagerActivity.class);
        intent.putExtra(SlidePagerActivity.EXTRA_PICTURES, mPhotoListAdapter.getPhotosList());
        intent.putExtra(SlidePagerActivity.EXTRA_ITEMNUM, mPhotoListAdapter.getItemNumber(photoItem));
        startActivity(intent);
    }

}
