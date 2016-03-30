package com.knightweng.android.takemehome.domain.dto;

import android.util.Log;

import com.knightweng.android.takemehome.common.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhotoItem implements Serializable, ParsingObject {

    public String       mId;
    public fromField    mFrom;
    public List<Images> mImages;
    public String       mCoverPhoto;
    public String       mVideoPreviewPic;

    public class fromField implements Serializable {
        public String id;
        public String name;
    }

    public class Images implements Serializable {
        public int    height;
        public int    width;
        public String source;
    }

    @Override
    public PhotoItem fromJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) {
            return null;
        }

        mId = obj.optString(ApiConstants.Photo.ID);
        JSONObject from = obj.optJSONObject(ApiConstants.Photo.FROM);
        mFrom = new fromField();
        if (from != null) {
            mFrom.id = from.getString(ApiConstants.Photo.ID);
            mFrom.name = from.getString(ApiConstants.Photo.NAME);
        }

        JSONArray images = obj.optJSONArray(ApiConstants.Photo.IMAGES);
        mImages = new ArrayList<>();
        if (images != null) {
            for (int i = 0; i < images.length(); i++) {
                Images image = new Images();
                image.height = (images.getJSONObject(i).optInt(ApiConstants.Photo.HEIGHT));
                image.width = (images.getJSONObject(i).optInt(ApiConstants.Photo.WIDTH));
                image.source = (images.getJSONObject(i).optString(ApiConstants.Photo.SOURCE));
                mImages.add(image);
            }
        }

        mCoverPhoto = obj.optString(ApiConstants.Photo.COVER_PHOTO);
        mVideoPreviewPic = obj.optString(ApiConstants.Photo.VIDEO_PREVIEW);
        return this;
    }
}
