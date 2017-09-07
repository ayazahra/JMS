package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;

import com.jasamarga.smartbook.R;

import su.whs.watl.text.BaseTextPagerAdapter;
import su.whs.watl.text.ITextPagesNumber;
import su.whs.watl.ui.MultiColumnTextViewEx;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainDetailSlidePagerAdapter extends BaseTextPagerAdapter {

    private Context context;
    private View view;
    private MultiColumnTextViewEx tv_content_detail_slide_main;

    public MainDetailSlidePagerAdapter(Context context, ITextPagesNumber pageIndicator) {
        super(R.id.tv_content_detail_slide_main, pageIndicator);
        this.context = context;
    }


    @Override
    public View getViewForPage(int position) {
        this.view = LayoutInflater.from(this.context).inflate(R.layout.list_item_detail_slide_main, null, false);

        this.tv_content_detail_slide_main = (MultiColumnTextViewEx) this.view.findViewById(R.id.tv_content_detail_slide_main);
        if (this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            tv_content_detail_slide_main.setColumnsCount(2);

        return view;
    }
}
