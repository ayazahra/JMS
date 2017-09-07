package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.MainDetailSlidePagerAdapter;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.DepthPageTransformer;
import com.jasamarga.smartbook.widget.ReaderViewPagerTransformer;
import com.jasamarga.smartbook.widget.TextOptionsHandler;
import com.ugurtekbas.fadingindicatorlibrary.FadingIndicator;

import su.whs.watl.text.HyphenLineBreaker;
import su.whs.watl.text.ITextPagesNumber;
import su.whs.watl.text.hyphen.HyphenPattern;
import su.whs.watl.text.hyphen.PatternsLoader;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class SekilasContentFragment extends DialogFragment {

    private Context context;
    private View view;
    private RelativeLayout rl_content_sekilas;
    private TextView tv_header_sekilas, tv_title_sekilas;
    private ImageView iv_close_sekilas;
    private ViewPager vp_sekilas;
    private MainDetailSlidePagerAdapter vp_sekilas_adapter;
    private FadingIndicator fi_sekilas;
    private String kontent;
    private String title;
    private TextOptionsHandler mOptionsHandler;

    public SekilasContentFragment() {

    }

    @SuppressLint("ValidFragment")
    public SekilasContentFragment(Context context, String mKontent, String mTitle) {
        this.context = context;
        this.kontent = mKontent;
        this.title = mTitle;
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

        this.view = inflater.inflate(R.layout.content_sekilas, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.tv_header_sekilas = (TextView) view.findViewById(R.id.tv_header_sekilas);
        this.tv_title_sekilas = (TextView) view.findViewById(R.id.tv_title_sekilas);
        this.rl_content_sekilas = (RelativeLayout) view.findViewById(R.id.rl_content_sekilas);
        this.vp_sekilas = (ViewPager) view.findViewById(R.id.vp_sekilas);
        this.fi_sekilas = (FadingIndicator) view.findViewById(R.id.fi_sekilas);
        this.iv_close_sekilas = (ImageView) view.findViewById(R.id.iv_close_sekilas);


        this.tv_header_sekilas.setText(this.title);

        this.vp_sekilas.setPageMargin(2);
        if (Build.VERSION.SDK_INT > 11)
            this.vp_sekilas.setPageTransformer(false, new DepthPageTransformer());
        else
            this.vp_sekilas.setPageTransformer(false,
                new ReaderViewPagerTransformer(
                        ReaderViewPagerTransformer.TransformType.SLIDE_OVER
                ));

        this.vp_sekilas_adapter = new MainDetailSlidePagerAdapter(this.context, mPagesIndicator);
        this.vp_sekilas_adapter.getOptions()
                .setDrawableMinimumScaleFactor(0.6f)
                .setTextPaddings(8,8,8,8);
        this.mOptionsHandler = new TextOptionsHandler(context, this.vp_sekilas_adapter);
        this.mOptionsHandler.onOptionsItemSelected();
        this.vp_sekilas.setAdapter(this.vp_sekilas_adapter);
        loadArticles();

//        if (this.bundle.getCharSequence("type").equals("content")) {
//        }else {
//            this.rl_content_sekilas.setPadding(0, 0, 0, 0);
//            this.vp_sekilas_adapter1 = new BocPagerAdapter(getChildFragmentManager(), this.context, this.bundle);
//            this.vp_sekilas.setAdapter(this.vp_sekilas_adapter1);
//            this.fi_sekilas.setViewPager(this.vp_sekilas);
//        }

        this.iv_close_sekilas.setOnClickListener(new CloseSekilasPopUp());

    }

    private void loadArticles() {
        new AsyncTask<Void,Void,CharSequence>() {

            @Override
            protected void onPreExecute() {


            }

            @Override
            protected CharSequence doInBackground(Void... params) {
                return loadArticle(Utility.CharSequenceUtility.noTrailingwhiteLines(Html.fromHtml(kontent)));
            }

            @Override
            protected void onPostExecute(CharSequence result) {
                HyphenPattern pat = PatternsLoader.getInstance(context).getHyphenPatternAssets("en_us.hyphen.dat");
                vp_sekilas_adapter.getOptions()
                        // .setImagePlacementHandler(new ImagePlacementHandler.DefaultImagePlacementHandler())
                        .setLineBreaker(HyphenLineBreaker.getInstance(pat));
                vp_sekilas_adapter.getTextPaint().setColor(getResources().getColor(R.color.colorBlueGrey700));
                vp_sekilas_adapter.setText(result);
                vp_sekilas.setOnPageChangeListener(new SekilasOnPageChangeListener());
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

    private class SekilasOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state==ViewPager.SCROLL_STATE_IDLE) {
                for (int i=0; i < vp_sekilas.getChildCount(); i++) {
                    View child = vp_sekilas.getChildAt(i);
                    child.destroyDrawingCache();
                }
                vp_sekilas.invalidate();
            }

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

    private class CloseSekilasPopUp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

}
