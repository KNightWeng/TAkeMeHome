package com.knightweng.android.takemehome.presentation.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.knightweng.android.takemehome.R;
import com.knightweng.android.takemehome.common.ApiConstants;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;
import com.knightweng.android.takemehome.presentation.view.CircularNetworkImageView;
import com.knightweng.android.takemehome.utils.VolleyLib;

public class AlbumListAdapter extends BaseAdapter {

    private final Context mContext;

    private final LayoutInflater mLayoutInflater;

    private List<PhotoItem>      mAlbums;

    private OnItemClickListener  mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(View anchor, PhotoItem photoItem);
    }

    public AlbumListAdapter(Context context, Collection<PhotoItem> collection) {
        mContext = context;
        validateCollection(collection);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAlbums = new ArrayList<>(collection);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void validateCollection(Collection<PhotoItem> photoItems) {
        if (photoItems == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setCollection(Collection<PhotoItem> photoItems) {
        validateUsersCollection(photoItems);
        mAlbums = new ArrayList<>(photoItems);
        notifyDataSetChanged();
    }

    private void validateUsersCollection(Collection<PhotoItem> photoItems) {
        if (photoItems == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public int getCount() {
        if (mAlbums == null)
            return 0;
        return mAlbums.size();
    }

    @Override
    public Object getItem(int position) {
        if (mAlbums == null)
            return null;
        return mAlbums.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mAlbums == null)
            return 0;
        return Long.parseLong(mAlbums.get(position).mId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("AlbumListAdapter", "AlbumListAdapter:getView");
        final PhotoItem result = (PhotoItem) getItem(position);

        ViewHolder resultViewHolder;

        if (convertView == null) {
            // init convertView by layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.photo_row_item, null);
        }

        if (convertView.getTag() == null) {
            resultViewHolder = new ViewHolder();
            resultViewHolder.instantiate(convertView);
            convertView.setTag(resultViewHolder);
        } else {
            resultViewHolder = (ViewHolder) convertView.getTag();
        }

        //final PhotoItem photoItem = mPhotos.get(position);
        convertView.findViewById(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(v, result);
            }
        });

        resultViewHolder.bindViews(result);
        return convertView;
    }

    public static class ViewHolder {

        NetworkImageView mPhoto;

        public void instantiate(View view) {
            mPhoto = (NetworkImageView) view.findViewById(R.id.iv_image);
        }

        public void bindViews(PhotoItem item) {
            if (item.mCoverPhoto != null && !item.mCoverPhoto.equals("")) {
                mPhoto.setImageUrl(ApiConstants.getAlbumCoverPhotoUrl(item.mId), VolleyLib.getImageLoader());
            } else if (item.mVideoPreviewPic != null && !item.mVideoPreviewPic.equals("")) {
                mPhoto.setImageUrl(item.mVideoPreviewPic, VolleyLib.getImageLoader());
            } else {
                mPhoto.setImageUrl(item.mImages.get(0).source, VolleyLib.getImageLoader());
            }
        }
    }

}
