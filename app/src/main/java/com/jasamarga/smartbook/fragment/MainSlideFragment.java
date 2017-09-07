package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.jasamarga.smartbook.widget.MySpannable;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainSlideFragment extends Fragment {

    private static final String TAG = MainSlideFragment.class.getSimpleName();
    private Context context;
    private View view;
    private ImageView iv_pict_slide_main;
    private CustomTextView tv_title_item_slide_main, tv_content_item_slide_main;
    private Bundle bundle;
    private CharSequence mTitle, mContent, mRingkas;
    private MainDetailSlideFragment mainDetailSlideFragment;

    public MainSlideFragment() {

    }

    @SuppressLint("ValidFragment")
    public MainSlideFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_slide_main, container, false);

        return this.view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.bundle = getArguments();
        this.mTitle = this.bundle.getCharSequence("slide_title");
        this.mContent = this.bundle.getCharSequence("slide_content");
        this.mRingkas = this.bundle.getCharSequence("slide_ringkas");

        this.iv_pict_slide_main = (ImageView) view.findViewById(R.id.iv_pict_slide_main);
        this.tv_title_item_slide_main = (CustomTextView) view.findViewById(R.id.tv_title_slide_main);
        this.tv_content_item_slide_main = (CustomTextView) view.findViewById(R.id.tv_content_slide_main);

        setupImageSizeBasedOnScreenSize();

        this.tv_title_item_slide_main.setText(mTitle);
        this.tv_content_item_slide_main.setText(mRingkas);
        Log.d(TAG, "mRingkas " + mRingkas);
        if (!this.tv_content_item_slide_main.getText().toString().equals(""))
            makeTextViewResizable(this.tv_content_item_slide_main, 3, "... Read More", true);

    }

    private void ShowMainSlideDetailViews() {
        this.mainDetailSlideFragment = new MainDetailSlideFragment(this.context);
        this.mainDetailSlideFragment.setArguments(this.bundle);
        this.mainDetailSlideFragment.show(getChildFragmentManager(), "mainDetailSlideFragment");
    }

    private void setupImageSizeBasedOnScreenSize() {
        DisplayMetrics metrics = Utility.DisplayUtility.getDisplayMetrics(this.context);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        if (smallestWidth > 600) {
            this.iv_pict_slide_main.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            this.iv_pict_slide_main.setScaleType(ImageView.ScaleType.CENTER);
        }
    }

    private void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    Log.d(TAG, "maxLine " + maxLine);
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    Log.d(TAG, "lineEndIndex " + maxLine);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length()) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });
    }

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){

                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        ShowMainSlideDetailViews();
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

}
