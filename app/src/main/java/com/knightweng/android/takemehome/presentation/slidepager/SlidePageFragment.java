package com.knightweng.android.takemehome.presentation.slidepager;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.knightweng.android.takemehome.R;
import com.knightweng.android.takemehome.presentation.view.PinchToZoomDraweeView;

public class SlidePageFragment extends Fragment {
    private static final String PIC_URL = "slidepagefragment.picurl";

    public static SlidePageFragment newInstance(@NonNull final String picUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(PIC_URL, picUrl);

        SlidePageFragment fragment = new SlidePageFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slide_page, container, false);

        PinchToZoomDraweeView view = (PinchToZoomDraweeView) rootView.findViewById(R.id.pic);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String url = arguments.getString(PIC_URL);
            view.setImageURI(Uri.parse(url));
        }

        return rootView;
    }
}
