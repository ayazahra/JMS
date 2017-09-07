package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.StrukturYearListAdapterCallback;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.util.List;

/**
 * Created by apridosandyasa on 9/15/16.
 */
public class StrukturYearListAdapter extends RecyclerView.Adapter<StrukturYearListAdapter.StrukturYearListViewHolder> {

    private Context context;
    private List<String> strukturYearList;
    private StrukturYearListAdapterCallback callback;
    private int selectedPosition = 0;

    public StrukturYearListAdapter(Context context, List<String> objects, StrukturYearListAdapterCallback listener) {
        this.context = context;
        this.strukturYearList = objects;
        this.callback = listener;
    }

    @Override
    public StrukturYearListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
        StrukturYearListViewHolder holder = new StrukturYearListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StrukturYearListViewHolder holder, int position) {
        holder.tv_title_item_header.setTextAppearance(this.context, android.R.style.TextAppearance_Medium);
        if (this.selectedPosition == position) {
            ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
            holder.tv_title_item_header.setTextColor(this.context.getResources().getColor(R.color.colorWhite));
        }else {
            ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorTransparent));
            holder.tv_title_item_header.setTextColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
        }
        holder.tv_title_item_header.setText(this.strukturYearList.get(position));
        holder.rl_container_item_header.setOnClickListener(new ActionPenghargaanYear(position));
    }

    @Override
    public int getItemCount() {
        return this.strukturYearList.size();
    }

    private class ActionPenghargaanYear implements View.OnClickListener {

        private int vPosition;

        public ActionPenghargaanYear(int position) {
            this.vPosition = position;
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPosition);
            selectedPosition = vPosition;
            notifyItemChanged(selectedPosition);
            callback.ShowPenghargaanListBasedOnYear(vPosition);
        }
    }

    public static class StrukturYearListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_container_item_header;
        private CustomTextView tv_title_item_header;

        StrukturYearListViewHolder(View view) {
            super(view);
            this.rl_container_item_header = (RelativeLayout) view.findViewById(R.id.rl_container_item_header);
            this.tv_title_item_header = (CustomTextView) view.findViewById(R.id.tv_title_item_header);
        }
    }

}
