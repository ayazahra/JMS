package com.jasamarga.smartbook.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by apridosandyasa on 6/27/16.
 */
public class CircularSlide implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private int mPosition;
    private int previousState, currentState;

    public CircularSlide(final ViewPager viewPager, int position) {
        mViewPager = viewPager;
        mPosition = position;
    }

    @Override
    public void onPageSelected(final int position) {
        mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
        int currentPage = mViewPager.getCurrentItem();
        if (currentPage == mViewPager.getAdapter().getCount() - 1 || currentPage == 0){
            previousState = currentState;
            currentState = state;
            if (previousState == 1 && currentState == 0){
                mPosition = (currentPage == 0) ? mViewPager.getAdapter().getCount() - 1 : 0;
                mViewPager.setCurrentItem(mPosition);
            }
        }
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }
}

