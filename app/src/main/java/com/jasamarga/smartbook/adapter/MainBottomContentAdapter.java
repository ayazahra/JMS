package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.MainBottomContentAdapterCallback;
import com.jasamarga.smartbook.object.Cabang;
import com.jasamarga.smartbook.widget.CustomTextView;

import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class MainBottomContentAdapter extends RecyclerView.Adapter<MainBottomContentAdapter.MainBottomContentViewHolder> {

    private Context context;
    private List<Cabang> cabangList;
    private MainBottomContentAdapterCallback callback;

    public MainBottomContentAdapter(Context context, List<Cabang> cabangList, MainBottomContentAdapterCallback listener) {
        this.context = context;
        this.cabangList = cabangList;
        this.callback = listener;
    }

    @Override
    public MainBottomContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bottom_main, parent, false);
        MainBottomContentViewHolder mainBottomContentViewHolder = new MainBottomContentViewHolder(view);
        return mainBottomContentViewHolder;
    }

    @Override
    public void onBindViewHolder(MainBottomContentViewHolder holder, int position) {
        holder.tv_title_item_bottom_main.setText(this.cabangList.get(position).getCabangDesc());
        holder.ll_container_item_bottom_main.setOnClickListener(new ActionMainBottomContentClick(position));
    }

    @Override
    public int getItemCount() {
        return this.cabangList.size();
    }

    private class ActionMainBottomContentClick implements View.OnClickListener {

        private int mPosition;

        public ActionMainBottomContentClick(int pos) {
            this.mPosition = pos;
        }

        @Override
        public void onClick(View v) {
            callback.onHideMainBottomContent(cabangList.get(mPosition), mPosition);
        }
    }

    public static class MainBottomContentViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_container_item_bottom_main;
        private CustomTextView tv_title_item_bottom_main;

        MainBottomContentViewHolder(View view) {
            super(view);
            this.ll_container_item_bottom_main = (LinearLayout) view.findViewById(R.id.ll_container_item_bottom_main);
            this.tv_title_item_bottom_main = (CustomTextView) view.findViewById(R.id.tv_title_item_bottom_main);
        }
    }
}
