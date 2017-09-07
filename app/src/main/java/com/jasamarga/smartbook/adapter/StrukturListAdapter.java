package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.jasamarga.smartbook.widget.TextOptionsHandler;

import java.util.List;

import su.whs.watl.ui.TextViewEx;

/**
 * Created by apridosandyasa on 8/17/16.
 */
public class StrukturListAdapter extends RecyclerView.Adapter<StrukturListAdapter.StrukturListViewHolder> {

    private Context context;
    private List<CharSequence> strukturList;
    private TextOptionsHandler opts;

    public StrukturListAdapter(Context context, List<CharSequence> objects) {
        this.context = context;
        this.strukturList = objects;
    }

    @Override
    public StrukturListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_struktur, parent, false);
        StrukturListViewHolder holder = new StrukturListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StrukturListViewHolder holder, int position) {
        holder.iv_medal_item_struktur.setImageResource(R.drawable.icon_medal);
        this.opts = new TextOptionsHandler(this.context, holder.tv_content_item_struktur);
        holder.tv_content_item_struktur.setText(Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.strukturList.get(position).toString()))));
    }

    @Override
    public int getItemCount() {
        return this.strukturList.size();
    }

    public static class StrukturListViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_medal_item_struktur;
        private TextViewEx tv_content_item_struktur;

        StrukturListViewHolder(View view) {
            super(view);
            this.iv_medal_item_struktur = (ImageView) view.findViewById(R.id.iv_medal_item_struktur);
            this.tv_content_item_struktur = (TextViewEx) view.findViewById(R.id.tv_content_item_struktur);
        }
    }
}
