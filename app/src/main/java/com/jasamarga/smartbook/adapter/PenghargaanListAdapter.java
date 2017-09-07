package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.object.Penghargaan;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.util.List;

/**
 * Created by apridosandyasa on 9/26/16.
 */

public class PenghargaanListAdapter extends RecyclerView.Adapter<PenghargaanListAdapter.PenghargaanListViewHolder> {

    private Context context;
    private List<Penghargaan> penghargaanList;

    public PenghargaanListAdapter(Context context, List<Penghargaan> objects) {
        this.context = context;
        this.penghargaanList = objects;
    }

    @Override
    public PenghargaanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_penghargaan, parent, false);
        PenghargaanListViewHolder holder = new PenghargaanListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PenghargaanListViewHolder holder, int position) {
        holder.tv_title_item_penghargaan.setText(this.penghargaanList.get(position).getTitle());
        holder.tv_detail_item_penghargaan.setText(this.penghargaanList.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return this.penghargaanList.size();
    }

    public static class PenghargaanListViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tv_title_item_penghargaan, tv_detail_item_penghargaan;

        PenghargaanListViewHolder(View view) {
            super(view);
            this.tv_title_item_penghargaan = (CustomTextView) view.findViewById(R.id.tv_title_item_penghargaan);
            this.tv_detail_item_penghargaan = (CustomTextView) view.findViewById(R.id.tv_detail_item_penghargaan);
        }
    }
}
