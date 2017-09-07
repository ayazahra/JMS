package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.jasamarga.smartbook.callback.AnggaranFragmentCallback;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.remote.DownloadFileUrlConnectionImpl;
import es.voghdev.pdfviewpager.library.util.FileUtil;

/**
 * Created by apridosandyasa on 8/17/16.
 */
public class AnggaranFragment extends DialogFragment {

    private final static String TAG =  AnggaranFragment.class.getSimpleName();
    private Context context;
    private View view;
    private String title = "";
    private String kontent = "";
    private String mode;
    private CustomTextView tv_header_anggaran;
    private RemotePDFViewPager rpvp_content_anggaran;
    private PDFPagerAdapter rpvp_content_anggaran_adapter;
    private DownloadFile.Listener rpvp_content_anggaran_listener;
    private DownloadFile downloadFile;
    private WebView wv_content_anggaran;
    private ImageView iv_close_anggaran;
    private ProgressDialog progressDialog;
    private AnggaranFragmentCallback callback;

    public AnggaranFragment() {

    }

    @SuppressLint("ValidFragment")
    public AnggaranFragment(Context context, AnggaranFragmentCallback listener, String mKontent, String mTitle, String m) {
        this.context = context;
        this.callback = listener;
        this.title = mTitle;
        this.kontent = mKontent;
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

        this.view = inflater.inflate(R.layout.content_anggaran, container, false);

        return this.view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.tv_header_anggaran = (CustomTextView) view.findViewById(R.id.tv_header_anggaran);
        this.wv_content_anggaran = (WebView) view.findViewById(R.id.wv_content_anggaran);
        this.rpvp_content_anggaran = (RemotePDFViewPager) view.findViewById(R.id.rpvp_content_anggaran);
        this.iv_close_anggaran = (ImageView) view.findViewById(R.id.iv_close_anggaran);

        setupAnggaranProgressDialog();

        if (this.mode.equals("web")) {
            this.tv_header_anggaran.setText(this.title);
            clearWebviewCache();
            this.wv_content_anggaran.getSettings().setJavaScriptEnabled(true);
            this.wv_content_anggaran.getSettings().setPluginState(WebSettings.PluginState.ON);
            this.wv_content_anggaran.getSettings().setUseWideViewPort(true);
            this.wv_content_anggaran.getSettings().setLoadWithOverviewMode(true);
            this.wv_content_anggaran.getSettings().setBuiltInZoomControls(true);
            this.wv_content_anggaran.getSettings().setTextSize(WebSettings.TextSize.LARGER);
            this.wv_content_anggaran.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            this.wv_content_anggaran.setWebViewClient(new AnggaranWebviewClient());
            Log.d(TAG, "htmlString " + this.kontent.replace("<img src=\"","<img src=\"http://202.158.49.221:8000/"));
            this.wv_content_anggaran.loadDataWithBaseURL(null, this.kontent.replace("<img src=\"","<img src=\"http://202.158.49.221:8000/"), "text/html", "utf-8", null);
            this.wv_content_anggaran.setVisibility(View.VISIBLE);
        }else if (this.mode.equals("pdf")) {
            this.tv_header_anggaran.setText(this.title);
            this.rpvp_content_anggaran_listener = new AnggaranDownloadPDFListener();
            this.downloadFile = new DownloadFileUrlConnectionImpl(this.context, new Handler(), this.rpvp_content_anggaran_listener);
            this.downloadFile.download("http://202.158.49.221:8000/download/Anggaran_dasar_ina.pdf",
                    new File(this.context.getExternalFilesDir("pdf"), "Anggaran_dasar_ina.pdf").getAbsolutePath());

            this.downloadFile = new DownloadFileUrlConnectionImpl(this.context, new Handler(), this.rpvp_content_anggaran_listener);
            this.downloadFile.download("http://202.158.49.221:8000/download/Anggaran_dasar_ina.pdf",
                    new File(this.context.getExternalFilesDir("pdf"), "Anggaran_dasar_ina.pdf").getAbsolutePath());
        }else{
            this.tv_header_anggaran.setText(this.title);
            clearWebviewCache();
            Log.d(TAG, "konten " + this.kontent);
            if (this.title.toLowerCase(Locale.getDefault()).equals("peta")) {
                this.kontent = this.kontent.replace("http://www.jasamarga.com/", "http://202.158.49.221:8000/");
            }else{
                this.kontent = this.kontent.replace("<img src=\"","<img src=\"http://http://202.158.49.221:8000/");
            }
            Log.d(TAG, "konten " + this.kontent);
            this.kontent = (this.kontent.contains("struktur")) ? ConstantAPI.BASE_URL + "/public/" + this.kontent : this.kontent;
            Log.d(TAG, "konten " + this.kontent);
            this.wv_content_anggaran.getSettings().setJavaScriptEnabled(true);
            this.wv_content_anggaran.getSettings().setPluginState(WebSettings.PluginState.ON);
            this.wv_content_anggaran.getSettings().setUseWideViewPort(true);
            this.wv_content_anggaran.getSettings().setLoadWithOverviewMode(true);
            this.wv_content_anggaran.getSettings().setBuiltInZoomControls(true);
            this.wv_content_anggaran.getSettings().setTextSize(WebSettings.TextSize.LARGER);
            this.wv_content_anggaran.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            this.wv_content_anggaran.setWebViewClient(new AnggaranWebviewClient());
            Log.d(TAG, "htmlString " + this.kontent);
            this.wv_content_anggaran.loadUrl(this.kontent);
            this.wv_content_anggaran.setVisibility(View.VISIBLE);
        }



//            if (Build.VERSION.SDK_INT > 20) {
//                this.rpvp_content_anggaran = new RemotePDFViewPager(this.context, "http://partners.adobe.com/public/developer/en/xml/AdobeXMLFormsSamples.pdf", this.rpvp_content_anggaran_listener);
//                this.rpvp_content_anggaran.setId(R.id.rpvp_content_anggaran);
//            }else{
//                this.downloadFile = new DownloadFileUrlConnectionImpl(this.context, new Handler(), this.rpvp_content_anggaran_listener);
//                this.downloadFile.download("http://jasamarga.com/download/Anggaran_dasar_ina.pdf",
//                        new File(this.context.getExternalFilesDir("pdf"), "Anggaran_dasar_ina.pdf").getAbsolutePath());
//            }

        this.iv_close_anggaran.setOnClickListener(new CloseAnggaran());

    }

    @Override
    public void onResume() {
        super.onResume();
        this.progressDialog.show();
    }

    private void clearWebviewCache() {
        this.wv_content_anggaran.clearCache(true);
        this.wv_content_anggaran.clearHistory();
        this.wv_content_anggaran.clearFormData();
    }

    private void setupAnggaranProgressDialog() {
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage("Menunggu untuk mengunduh " + this.title + "..");
        this.progressDialog.setCancelable(true);
        this.progressDialog.setCanceledOnTouchOutside(true);
        this.progressDialog.setOnCancelListener(new ProgressDialogCancelListener());
    }

    private class AnggaranDownloadPDFListener implements DownloadFile.Listener {

        @Override
        public void onSuccess(String url, String destinationPath) {
            File file = new File(destinationPath);
//            if (Build.VERSION.SDK_INT > 20) {
//                rpvp_content_anggaran_adapter = new PDFPagerAdapter(context, "AdobeXMLFormsSamples.pdf");
//                rpvp_content_anggaran.setAdapter(rpvp_content_anggaran_adapter);
//            }else{
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//                //dismiss();
//            }
            callback.onAnggaranFragmentCallback(file);
            progressDialog.dismiss();
            dismiss();
        }

        @Override
        public void onFailure(Exception e) {
            Log.d(TAG, "PDF Exception " + e.getMessage());
        }

        @Override
        public void onProgressUpdate(int progress, int total) {

        }
    }

    private class AnggaranWebviewClient extends WebViewClient {

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

    private class CloseAnggaran implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    private class ProgressDialogCancelListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialog) {
            progressDialog.dismiss();
            dismiss();
        }
    }

}

