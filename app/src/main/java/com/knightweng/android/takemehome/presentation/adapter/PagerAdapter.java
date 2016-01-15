package com.knightweng.android.takemehome.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.knightweng.android.takemehome.presentation.fragment.PhotoListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return PhotoListFragment.newInstance(PhotoListFragment.getItemBundle("photo"));
        case 1:
            return PhotoListFragment.newInstance(PhotoListFragment.getItemBundle("album"));
        case 2:
            return PhotoListFragment.newInstance(PhotoListFragment.getItemBundle("video"));
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return "Photos";
        case 1:
            return "Albums";
        case 2:
            return "Videos";
        }
        return null;
    }

}
