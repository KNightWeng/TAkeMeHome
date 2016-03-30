package com.knightweng.android.takemehome.presentation.slidepager;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.knightweng.android.takemehome.R;

import java.util.Arrays;

public class SlidePagerActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "slidepageractivity.extra.title";
    public static final String EXTRA_PICTURES = "slidepageractivity.extra.pictures";
    public static final String  EXTRA_ITEMNUM = "slidepageractivity.extra.itemnum";
    public static final String EXTRA_INDICATOR_TYPE = "slidepageractivity.extra.indicator.type";

    private PageIndicator mPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);

        setContentView(R.layout.activity_slide_pager);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        SlidePagerAdapter pagerAdapter =
                new SlidePagerAdapter(getSupportFragmentManager());

        if (getIntent() == null) return;

        // set pictures
        String[] pics = getIntent().getStringArrayExtra(EXTRA_PICTURES);
        pagerAdapter.addAll(Arrays.asList(pics));

        // set item number
        int mItemNum = getIntent().getIntExtra(EXTRA_ITEMNUM, 0);

        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPageIndicator = (PageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setViewPagerAndPosition(pager, mItemNum);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_indicator_circle) {
            mPageIndicator.setIndicatorType(PageIndicator.IndicatorType.CIRCLE);
        } else if (id == R.id.action_indicator_fraction) {
            mPageIndicator.setIndicatorType(PageIndicator.IndicatorType.FRACTION);
        }
        return super.onOptionsItemSelected(item);
    }*/
}