package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.BawahanListAdapter;
import com.jasamarga.smartbook.callback.BawahanListAdapterCallback;
import com.jasamarga.smartbook.callback.MainCallback;
import com.jasamarga.smartbook.callback.StrukturCabangCallback;
import com.jasamarga.smartbook.logic.StrukturCabangLogic;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.SharedPreferencesProvider;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 10/11/16.
 */

public class StrukturCabangFragment extends DialogFragment implements StrukturCabangCallback, BawahanListAdapterCallback  {

    private static final String TAG = StrukturCabangFragment.class.getSimpleName();
    private Context context;
    private View view;
    private CustomTextView tv_header_strukturcabang, tv_name_strukturcabang;
    private CircleImageView civ_pict_strukturcabang;
    private ImageView iv_close_strukturcabang;
    private RecyclerView rv_bawahan_strukturcabang;
    private LinearLayoutManager rv_bawahan_strukturcabang_llm;
    private BawahanListAdapter rv_bawahan_strukturcabang_adapter;
    private String konten, title;
    private PersonalInfo personalInfo;
    private List<PersonalInfo> bawahanList = new ArrayList<>();
    private StrukturCabangLogic strukturCabangLogic;
    private String params = "";
    private Target loadedBitmap;
    private MainCallback callback;

    public StrukturCabangFragment() {

    }

    @SuppressLint("ValidFragment")
    public StrukturCabangFragment(Context context, String mKonten, String mTitle, MainCallback listener) {
        this.context = context;
        this.konten = mKonten;
        this.title = mTitle;
        this.callback = listener;
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

        this.view = inflater.inflate(R.layout.content_strukturcabang, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.params = this.konten;
        Log.d(TAG, params);

        this.tv_header_strukturcabang = (CustomTextView) view.findViewById(R.id.tv_header_strukturcabang);
        this.civ_pict_strukturcabang = (CircleImageView) view.findViewById(R.id.civ_pict_strukturcabang);
        this.tv_name_strukturcabang = (CustomTextView) view.findViewById(R.id.tv_name_strukturcabang);
        this.rv_bawahan_strukturcabang = (RecyclerView) view.findViewById(R.id.rv_bawahan_strukturcabang);
        this.iv_close_strukturcabang = (ImageView) view.findViewById(R.id.iv_close_strukturcabang);

        this.tv_header_strukturcabang.setText(this.title);

        this.rv_bawahan_strukturcabang_llm = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        this.rv_bawahan_strukturcabang.setHasFixedSize(true);
        this.rv_bawahan_strukturcabang.setLayoutManager(this.rv_bawahan_strukturcabang_llm);

        this.iv_close_strukturcabang.setOnClickListener(new CloseStrukturCabang());

        this.strukturCabangLogic = new StrukturCabangLogic(this.context, this);
        this.strukturCabangLogic.setupStrukturCabangViews(this.params);
    }

    @Override
    public void finishedSetupStruktrukCabangViews(PersonalInfo personalInfo, List<PersonalInfo> bawahanList) {
        this.personalInfo = personalInfo;
        this.bawahanList = bawahanList;

        if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                civ_pict_strukturcabang.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        this.civ_pict_strukturcabang.setTag(this.loadedBitmap);
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" + this.personalInfo.getUrlfoto())));

        Picasso.with(this.context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);

        this.tv_name_strukturcabang.setText(this.personalInfo.getNama());

        this.rv_bawahan_strukturcabang_adapter = new BawahanListAdapter(this.context, this.bawahanList, this);
        this.rv_bawahan_strukturcabang.setAdapter(this.rv_bawahan_strukturcabang_adapter);

        this.civ_pict_strukturcabang.setOnClickListener(new ShowPersonalInfo());
    }

    @Override
    public void onBawahanListAdapterCallback(int position) {
        this.callback.showPersonalInfoFromSrukturCabangViews(this.bawahanList.get(position));
        dismiss();
    }

    public class ShowPersonalInfo implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            callback.showPersonalInfoFromSrukturCabangViews(personalInfo);
            dismiss();
        }
    }

    private class CloseStrukturCabang implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
}
