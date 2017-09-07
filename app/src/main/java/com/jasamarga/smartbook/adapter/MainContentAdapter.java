package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.MainContentAdapterCallback;
import com.jasamarga.smartbook.object.MainContentData;
import com.jasamarga.smartbook.object.Submenu;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.MainContentViewHolder> {
    private Context context;
    private List<Submenu> submenuList;
    private int mainMenuId;
    private MainContentAdapterCallback mainContentAdapterCallback;

    public MainContentAdapter(Context context, List<Submenu> objects, int mainMenuId, MainContentAdapterCallback listener) {
        this.context = context;
        this.submenuList = objects;
        this.mainMenuId = mainMenuId;
        this.mainContentAdapterCallback = listener;
    }

    @Override
    public MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new MainContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainContentViewHolder holder, int position) {
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.submenuList.get(position).getSubMenuUrl())));
        Log.d("TAG", "url content icon " + ConstantAPI.BASE_URL + url.toString());

        Picasso.with(this.context)
                .load(ConstantAPI.BASE_URL + "/public" + url.toString())
                .placeholder(R.drawable.placeholder)
                .into(holder.civ_pict_item_main);

        if (android.os.Build.VERSION.SDK_INT > 20)
            holder.civ_pict_item_main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        holder.tv_title_item_main.setText(this.submenuList.get(position).getSubMenuNama());
        holder.ll_container_item_main.setOnClickListener(new ActionClick(this.submenuList, position));
    }

    @Override
    public int getItemCount() {
        return this.submenuList.size();
    }

    private class ActionClick implements View.OnClickListener {

        private List<Submenu> data;
        private int mPosition;

        public ActionClick(List<Submenu> object, int pos) {
            this.data = object;
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            mainContentAdapterCallback.onMainContentAdapterCallback(this.data, this.mPosition, mainMenuId);
        }
    }

    public static class MainContentViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_container_item_main;
        private CircleImageView civ_pict_item_main;
        private CustomTextView tv_title_item_main;

        MainContentViewHolder(View view) {
            super(view);
            this.ll_container_item_main = (LinearLayout) view.findViewById(R.id.ll_container_item_main);
            this.civ_pict_item_main = (CircleImageView) view.findViewById(R.id.civ_pict_item_main);
            this.tv_title_item_main = (CustomTextView) view.findViewById(R.id.tv_title_item_main);
        }
    }
}
