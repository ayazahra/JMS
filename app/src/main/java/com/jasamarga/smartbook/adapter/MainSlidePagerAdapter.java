package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Html;
import android.text.TextUtils;

import com.jasamarga.smartbook.fragment.MainSlideFragment;
import com.jasamarga.smartbook.object.Highlight;
import com.jasamarga.smartbook.object.MainSlideData;
import com.jasamarga.smartbook.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainSlidePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;
    private List<Highlight> highlightList;

    public MainSlidePagerAdapter(FragmentManager fm, Context context, List<Highlight> objects) {
        super(fm);
        this.context = context;
        this.highlightList = objects;
        initializeFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("slide_pos", position);
        bundle.putCharSequence("slide_header", Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.highlightList.get(position).getNama()))));
        bundle.putCharSequence("slide_title", Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.highlightList.get(position).getNama()))));
        bundle.putCharSequence("slide_content", Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.highlightList.get(position).getFullText()))));
        bundle.putCharSequence("slide_ringkas", Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.highlightList.get(position).getIntroText()))));
        this.fragmentList.get(position).setArguments(bundle);
        return this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    private void initializeFragment() {
        this.fragmentList = new ArrayList<>();
        for (int i = 0; i < this.highlightList.size(); i++) {
            this.fragmentList.add(new MainSlideFragment(this.context));
        }
    }
}
