package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.util.HashMap;

/**
 * Created by apridosandyasa on 8/26/16.
 */
public class BocFragment extends DialogFragment {

    private final static String TAG =  BocFragment.class.getSimpleName();
    private Context context;
    private View view;
    private String title = "";
    private String kontent = "";
    private String mode;
    private CustomTextView tv_header_boc;
    private WebView wv_content_boc;
    private ImageView iv_close_boc;
    private ProgressDialog progressDialog;

    public BocFragment() {

    }

    @SuppressLint("ValidFragment")
    public BocFragment(Context context, String mKontent, String mTitle, String m) {
        this.context = context;
        this.kontent = mKontent;
        this.title = mTitle;
        this.mode = m;
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

        this.view = inflater.inflate(R.layout.content_boc, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.tv_header_boc = (CustomTextView) view.findViewById(R.id.tv_header_boc);
        this.wv_content_boc = (WebView) view.findViewById(R.id.wv_content_boc);
        this.iv_close_boc = (ImageView) view.findViewById(R.id.iv_close_boc);

        setupBocProgressDialog();

        this.tv_header_boc.setText(this.title);
        clearWebviewCache();
        this.wv_content_boc.getSettings().setJavaScriptEnabled(true);
        this.wv_content_boc.getSettings().setPluginState(WebSettings.PluginState.ON);
        this.wv_content_boc.setWebViewClient(new BocWebviewClient());
        Log.d(TAG, "htmlString " + this.kontent.replace("<img src=","<img src=http://202.158.49.221:8000/"));
        if (this.mode.equals("web"))
            //this.wv_content_boc.loadDataWithBaseURL(null, this.kontent.replace("<img src=\"","<img src=\"http://202.158.49.221:8000/"), "text/html", "utf-8", null);
            this.wv_content_boc.loadDataWithBaseURL(null, this.kontent, "text/html", "utf-8", null);
        else
            //this.wv_content_boc.loadUrl(this.kontent.replace("<img src=\"","<img src=\"http://202.158.49.221:8000/"));
            this.wv_content_boc.loadUrl(this.kontent);

        this.iv_close_boc.setOnClickListener(new CloseBoc());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.progressDialog.show();
    }

    private void clearWebviewCache() {
        this.wv_content_boc.clearCache(true);
        this.wv_content_boc.clearHistory();
        this.wv_content_boc.clearFormData();
    }

    private void setupBocProgressDialog() {
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage("Menunggu untuk mengunduh " + this.title + "..");
        this.progressDialog.setCancelable(true);
        this.progressDialog.setCanceledOnTouchOutside(true);
        this.progressDialog.setOnCancelListener(new ProgressCancelDialogListener());
    }

    private class BocWebviewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }
    }

    private class CloseBoc implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    private class ProgressCancelDialogListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialog) {
            progressDialog.dismiss();
            dismiss();
        }
    }

}
