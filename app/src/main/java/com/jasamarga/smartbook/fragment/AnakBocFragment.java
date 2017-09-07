package com.jasamarga.smartbook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.adapter.AnakBocListAdapter;
import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 9/22/16.
 */
public class AnakBocFragment extends DialogFragment {

    private Context context;
    private View view;
    private CustomTextView tv_header_anakboc;
    private ImageView iv_close_anakboc, iv_pict_popup_anakboc;
    private ListView lv_list_anakboc;
    private AnakBocListAdapter lv_list_anakboc_adapter;
    private SwingBottomInAnimationAdapter lv_anakboc_animation_adapter;
    private List<BocData> bocDataList = new ArrayList<>();
    private String mode;
    private String mTitle;
    private int clickedPos = 0;

    public AnakBocFragment() {

    }

    @SuppressLint("ValidFragment")
    public AnakBocFragment(Context context, List<BocData> objects, String m, String t) {
        this.context = context;
        this.bocDataList = objects;
        this.mode = m;
        this.mTitle = t;
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

        this.view = inflater.inflate(R.layout.content_anakboc, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        this.tv_header_anakboc = (CustomTextView) view.findViewById(R.id.tv_header_anakboc);
        this.lv_list_anakboc = (ListView) view.findViewById(R.id.lv_list_anakboc);
        this.iv_close_anakboc = (ImageView) view.findViewById(R.id.iv_close_anakboc);
        this.iv_pict_popup_anakboc = (ImageView) view.findViewById(R.id.iv_pict_popup_anakboc);

        this.tv_header_anakboc.setText(mTitle);

        this.lv_list_anakboc_adapter = new AnakBocListAdapter(this.context, this.bocDataList);
//            this.lv_anakboc_animation_adapter = new SwingBottomInAnimationAdapter(this.lv_list_anakboc_adapter);
//            this.lv_anakboc_animation_adapter.setAbsListView(this.lv_list_anakboc);
        this.lv_list_anakboc.setAdapter(this.lv_list_anakboc_adapter);
        this.lv_list_anakboc.setOnItemClickListener(new ShowPopupPict());

        this.iv_pict_popup_anakboc.setOnClickListener(new HidePopupPict());
        this.iv_close_anakboc.setOnClickListener(new CloseAnakBoc());
    }

    private class ShowPopupPict implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public" +  bocDataList.get(position).getUrl())));
            Picasso.with(context)
                    .load(url.toString())
                    .placeholder(R.drawable.placeholder)
                    .into(iv_pict_popup_anakboc);
            if (clickedPos == position) {
                if (iv_pict_popup_anakboc.isShown())
                    iv_pict_popup_anakboc.setVisibility(View.INVISIBLE);
                else
                    iv_pict_popup_anakboc.setVisibility(View.VISIBLE);
            }else{
                clickedPos = position;
                iv_pict_popup_anakboc.setVisibility(View.VISIBLE);
            }

        }
    }

    private class HidePopupPict implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            iv_pict_popup_anakboc.setVisibility(View.INVISIBLE);
        }
    }

    private class CloseAnakBoc implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

}
