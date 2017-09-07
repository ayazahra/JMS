package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jasamarga.smartbook.R;

import su.whs.watl.ui.MultiColumnTextViewEx;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainContentDetailSlideFragment extends Fragment {

    private Context context;
    private View view;
    private Bundle bundle;
    private CharSequence mContent;
    private MultiColumnTextViewEx tv_content_detail_slide_main;

    public MainContentDetailSlideFragment() {

    }

    @SuppressLint("ValidFragment")
    public MainContentDetailSlideFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.list_item_detail_slide_main, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.bundle = getArguments();

        this.tv_content_detail_slide_main = (MultiColumnTextViewEx) view.findViewById(R.id.tv_content_detail_slide_main);

        this.mContent = this.bundle.getCharSequence("slide_content");
        this.tv_content_detail_slide_main.setText(this.mContent);
    }

}
