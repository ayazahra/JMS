package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.HeaderListAdapterCallback;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.util.List;

/**
 * Created by apridosandyasa on 8/25/16.
 */
public class HeaderListAdapter extends RecyclerView.Adapter<HeaderListAdapter.HeaderListViewHolder> {

    private Context context;
    private List<Cabang> headerList;
    private int mainMenuId;
    private HeaderListAdapterCallback callback;
    private int selectedPosition = 0;

    public HeaderListAdapter(Context context, List<Cabang> objects, int mainMenuId, HeaderListAdapterCallback listener, int p) {
        this.context = context;
        this.headerList = objects;
        this.mainMenuId = mainMenuId;
        this.callback = listener;
        this.selectedPosition = p;
    }

    @Override
    public HeaderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
        HeaderListViewHolder holder = new HeaderListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HeaderListViewHolder holder, int position) {
        if (this.mainMenuId == 2) {
            if (this.selectedPosition == position) {
                ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorRedAP));
            }else {
                ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
            }
        }else if (this.mainMenuId == 3 || this.mainMenuId == 4) {
            if (this.selectedPosition == position) {
                ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorOrange400));
            }else {
                ((GradientDrawable) holder.rl_container_item_header.getBackground()).setColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
            }
        }
        holder.tv_title_item_header.setText(this.headerList.get(position).getCabangDesc());
        holder.rl_container_item_header.setOnClickListener(new HeaderListViewHolderActionClick(position));
    }

    @Override
    public int getItemCount() {
        return this.headerList.size();
    }

    private class HeaderListViewHolderActionClick implements View.OnClickListener {

        private int mPosition;

        public HeaderListViewHolderActionClick(int pos) {
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            if (mainMenuId == 2) {
                notifyItemChanged(selectedPosition);
                selectedPosition = mPosition;
                notifyItemChanged(selectedPosition);
                callback.onHeaderListAdapterCallback(headerList.get(mPosition), mainMenuId, mPosition);
            }else if (mainMenuId == 3 || mainMenuId == 4) {
                notifyItemChanged(selectedPosition);
                selectedPosition = mPosition;
                notifyItemChanged(selectedPosition);
                callback.onHeaderListAdapterCallback(headerList.get(mPosition), mainMenuId, mPosition);
            }
        }
    }

    public static class HeaderListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_container_item_header;
        private CustomTextView tv_title_item_header;

        HeaderListViewHolder(View view) {
            super(view);
            this.rl_container_item_header = (RelativeLayout) view.findViewById(R.id.rl_container_item_header);
            this.tv_title_item_header = (CustomTextView) view.findViewById(R.id.tv_title_item_header);
        }
    }
}
