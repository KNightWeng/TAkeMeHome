package com.knightweng.android.takemehome.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.knightweng.android.takemehome.presentation.fragment.AlbumListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGES = 2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        //case 0:
        //    return AlbumListFragment.newInstance(AlbumListFragment.getItemBundle("photo"));
        case 0:
            return AlbumListFragment.newInstance(AlbumListFragment.getItemBundle("album"));
        case 1:
            return AlbumListFragment.newInstance(AlbumListFragment.getItemBundle("video"));
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
        //case 0:
        //    return "Photos";
        case 0:
            return "Albums";
        case 1:
            return "Videos";
        }
        return null;
    }

}
