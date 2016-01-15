package com.knightweng.android.takemehome.entity;

import com.knightweng.android.takemehome.domain.dto.ParsingObject;
import com.knightweng.android.takemehome.domain.dto.PhotoItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotosResponse implements ParsingObject, Serializable {
    private List<PhotoItem> mPhotoItemsList;

    public List<PhotoItem> getResult() {
        return mPhotoItemsList;
    }
    @Override
    public PhotosResponse fromJsonObject(JSONObject obj) throws JSONException {
        if (null == obj) {
            throw new JSONException("JSON is null");
        }
        this.mPhotoItemsList = new ArrayList<>();
        JSONArray itemsArray = obj.optJSONArray("data");
        if (itemsArray == null) {
            return this;
        }

        JSONObject itemObj;
        for (int i = 0; i < itemsArray.length(); i++) {
            itemObj = itemsArray.getJSONObject(i);
            PhotoItem photoItem = new PhotoItem();
            photoItem.fromJsonObject(itemObj);
            this.mPhotoItemsList.add(photoItem);
        }
        return this;
    }
}