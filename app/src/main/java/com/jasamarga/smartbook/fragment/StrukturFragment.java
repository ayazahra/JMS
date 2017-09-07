package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.PenghargaanListAdapter;
import com.jasamarga.smartbook.adapter.StrukturListAdapter;
import com.jasamarga.smartbook.adapter.StrukturYearListAdapter;
import com.jasamarga.smartbook.callback.StrukturYearListAdapterCallback;
import com.jasamarga.smartbook.object.Penghargaan;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apridosandyasa on 8/9/16.
 */
public class StrukturFragment extends DialogFragment implements StrukturYearListAdapterCallback {

    private final static String TAG = StrukturFragment.class.getSimpleName();
    private Context context;
    private View view;
    private CustomTextView tv_header_struktur;
    private RecyclerView rv_2012_struktur, rv_2013_struktur;
    private LinearLayoutManager rv_2012_struktur_llm, rv_2013_struktur_llm;
    private StrukturYearListAdapter rv_2012_struktur_adapter;
    private PenghargaanListAdapter rv_2013_struktur_adapter;
    private ImageView iv_close_struktur;
    private HashMap<String, Object> data;
    private String mTitle;
    private List<String> penghargaanYearList = new ArrayList<>();
    private List<Penghargaan> penghargaanList = new ArrayList<>();

    public StrukturFragment() {

    }

    @SuppressLint("ValidFragment")
    public StrukturFragment(Context context, HashMap<String, Object> object, String title) {
        this.context = context;
        this.data = object;
        this.mTitle = title;
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

        this.view = inflater.inflate(R.layout.content_struktur, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);
        Log.d(TAG, String.valueOf(this.data.toString()));

        try {
            this.penghargaanYearList = Utility.HashMapUtility.getStrukturYearListFromHashMap(this.data);
            this.penghargaanList = Utility.HashMapUtility.getStrukturListFromHashMap(this.data, this.penghargaanYearList.get(0));
        }catch (JSONException e) {
            Log.d(TAG, "Struktur JSONException " + e.getMessage());
        }

        this.tv_header_struktur = (CustomTextView) view.findViewById(R.id.tv_header_struktur);
        this.rv_2012_struktur = (RecyclerView) view.findViewById(R.id.rv_2012_struktur);
        this.rv_2013_struktur = (RecyclerView) view.findViewById(R.id.rv_2013_struktur);
        this.iv_close_struktur = (ImageView) view.findViewById(R.id.iv_close_struktur);

        this.rv_2012_struktur_llm = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        this.rv_2012_struktur.setHasFixedSize(true);
        this.rv_2012_struktur.setLayoutManager(this.rv_2012_struktur_llm);
        this.rv_2012_struktur.setScrollContainer(false);
        this.rv_2012_struktur.setNestedScrollingEnabled(false);

        this.rv_2013_struktur_llm = new LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false);
        this.rv_2013_struktur.setHasFixedSize(true);
        this.rv_2013_struktur.setLayoutManager(this.rv_2013_struktur_llm);
        this.rv_2013_struktur.setScrollContainer(false);
        this.rv_2013_struktur.setNestedScrollingEnabled(false);

        this.tv_header_struktur.setText(this.mTitle);

        this.rv_2012_struktur_adapter = new StrukturYearListAdapter(this.context, this.penghargaanYearList, this);
        this.rv_2012_struktur.setAdapter(this.rv_2012_struktur_adapter);

        this.rv_2013_struktur_adapter = new PenghargaanListAdapter(this.context, this.penghargaanList);
        this.rv_2013_struktur.setAdapter(this.rv_2013_struktur_adapter);

        this.iv_close_struktur.setOnClickListener(new CloseDialog());
    }

    @Override
    public void ShowPenghargaanListBasedOnYear(int position) {
        this.rv_2013_struktur.setAdapter(null);
        this.rv_2013_struktur_adapter = null;
        this.penghargaanList.clear();
        try {
            this.penghargaanList = Utility.HashMapUtility.getStrukturListFromHashMap(this.data, this.penghargaanYearList.get(position));
            this.rv_2013_struktur_adapter = new PenghargaanListAdapter(this.context, this.penghargaanList);
            this.rv_2013_struktur.setAdapter(this.rv_2013_struktur_adapter);
        }catch (JSONException e) {
            Log.d(TAG, "Struktur JSONException " + e.getMessage());
        }
    }

    private class CloseDialog implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
}
