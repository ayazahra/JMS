package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.MainDetailSlidePagerAdapter;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.DepthPageTransformer;
import com.jasamarga.smartbook.widget.ReaderViewPagerTransformer;
import com.jasamarga.smartbook.widget.TextOptionsHandler;
import com.ugurtekbas.fadingindicatorlibrary.FadingIndicator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import su.whs.watl.text.HtmlTagHandler;
import su.whs.watl.text.HyphenLineBreaker;
import su.whs.watl.text.ITextPagesNumber;
import su.whs.watl.text.ImagePlacementHandler;
import su.whs.watl.text.hyphen.HyphenPattern;
import su.whs.watl.text.hyphen.PatternsLoader;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainDetailSlideFragment extends DialogFragment {

    private final static String TAG = MainDetailSlideFragment.class.getSimpleName();
    private Context context;
    private View view;
    private Bundle bundle;
    private TextView tv_header_detail_slide_main, tv_title_detail_slide_main;
//    private ViewPager vp_detail_slide_main;
//    private MainDetailSlidePagerAdapter vp_detail_slide_main_adapter;
//    private FadingIndicator fi_detail_slide_main;
//    private TextOptionsHandler mOptionsHandler;
    private WebView wv_detail_slide_main;
    private ImageView iv_close_detail_slide_main;
    private CharSequence mHeader, mTitle, mContent;

    public MainDetailSlideFragment() {

    }

    @SuppressLint("ValidFragment")
    public MainDetailSlideFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.width = (int) (Utility.DisplayUtility.getScreenWidth(this.context) * 0.97);
        windowParams.height = (int) (Utility.DisplayUtility.getScreenHeight(this.context) * 0.97);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_detail_slide_main, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.bundle = getArguments();

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.tv_header_detail_slide_main = (TextView) view.findViewById(R.id.tv_header_detail_slide_main);
        this.tv_title_detail_slide_main = (TextView) view.findViewById(R.id.tv_title_detail_slide_main);
//        this.vp_detail_slide_main = (ViewPager) view.findViewById(R.id.vp_detail_slide_main);
//        this.fi_detail_slide_main = (FadingIndicator) view.findViewById(R.id.fi_detail_slide_main);
        this.wv_detail_slide_main = (WebView) view.findViewById(R.id.wv_detail_slide_main);
        this.iv_close_detail_slide_main = (ImageView) view.findViewById(R.id.iv_close_detail_slide_main);

        this.mHeader = this.bundle.getCharSequence("slide_header");
        this.mTitle = this.bundle.getCharSequence("slide_title");
        this.mContent = this.bundle.getCharSequence("slide_content");
        Log.d("TAG", "mContent " + this.mContent.toString());

        this.tv_header_detail_slide_main.setText("");
        this.tv_title_detail_slide_main.setText(this.mTitle);

        this.wv_detail_slide_main.loadDataWithBaseURL(null, this.mContent.toString().replace("<img src=\"","<img src=\"http://202.158.49.221:8000/"), "text/html", "utf-8", null);

//        this.vp_detail_slide_main.setPageMargin(2);
//        if (Build.VERSION.SDK_INT > 11)
//            this.vp_detail_slide_main.setPageTransformer(false, new DepthPageTransformer());
//            this.vp_detail_slide_main.setPageTransformer(false,
//                    new ReaderViewPagerTransformer(
//                            ReaderViewPagerTransformer.TransformType.SLIDE_OVER
//                    ));
//
//        vp_detail_slide_main_adapter = new MainDetailSlidePagerAdapter(this.context, mPagesIndicator);
//        vp_detail_slide_main_adapter.getOptions()
//                .setDrawableMinimumScaleFactor(0.6f)
//                .setTextPaddings(8,8,8,8);
//        mOptionsHandler = new TextOptionsHandler(context, vp_detail_slide_main_adapter);
//        mOptionsHandler.onOptionsItemSelected();
//        vp_detail_slide_main.setAdapter(vp_detail_slide_main_adapter);
//        loadArticles();

        this.iv_close_detail_slide_main.setImageResource(R.drawable.icon_close);

        this.iv_close_detail_slide_main.setOnClickListener(new CloseDialog());
    }

    private void loadArticles() {
        new AsyncTask<Void,Void,CharSequence>() {

            @Override
            protected void onPreExecute() {


            }

            @Override
            protected CharSequence doInBackground(Void... params) {
                return loadArticle(mContent);
            }

            @Override
            protected void onPostExecute(CharSequence result) {
                Log.d("TAG", "result " + result);
//                HyphenPattern pat = PatternsLoader.getInstance(context).getHyphenPatternAssets("en_us.hyphen.dat");
//                vp_detail_slide_main_adapter.getOptions()
//                        .setImagePlacementHandler(new ImagePlacementHandler.DefaultImagePlacementHandler())
//                        .setLineBreaker(HyphenLineBreaker.getInstance(pat));
//                vp_detail_slide_main_adapter.getTextPaint().setColor(getResources().getColor(R.color.colorBlueGrey700));
//                vp_detail_slide_main_adapter.setText(result);
//                fi_detail_slide_main.setViewPager(vp_detail_slide_main);
//                vp_detail_slide_main.setOnPageChangeListener(new MainDetailSlideOnPageChangeListener());
            }

        }.execute();
    }

    private CharSequence loadArticle(CharSequence mContent) {
        String article = readArticle(mContent);
        return article;
    }

    private String readArticle(CharSequence name) {
        return name.toString();
    }

    private ITextPagesNumber mPagesIndicator = new ITextPagesNumber() {
        @Override
        public void updateInfo(View page, int number, int total) {
            IndicatorHolder ih = (IndicatorHolder) page.getTag();
            if (ih == null)
                ih = new IndicatorHolder(page);
            ih.set(number,total);
        }
    };

    private class MainDetailSlideOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
//            if (state==ViewPager.SCROLL_STATE_IDLE) {
//                for (int i=0; i < vp_detail_slide_main.getChildCount(); i++) {
//                    View child = vp_detail_slide_main.getChildAt(i);
//                    child.destroyDrawingCache();
//                }
//                vp_detail_slide_main.invalidate();
//            }
        }
    }

    private class IndicatorHolder {
        View p;
        int _n = -1;
        int _t = -1;

        public IndicatorHolder(View page) {
            p = page;
            p.setTag(this);
        }
        public void set(int n, int t) {
            if (_n==n && _t==t) return;
            p.destroyDrawingCache();
            _n = n;
            _t = t;
        }
    }

    private class CloseDialog implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

}
