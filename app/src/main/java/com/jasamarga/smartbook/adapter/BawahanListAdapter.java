package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.BawahanListAdapterCallback;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public class BawahanListAdapter extends RecyclerView.Adapter<BawahanListAdapter.BawahanListViewHolder> {

    private Context context;
    private List<PersonalInfo> bawahanList;
    private BawahanListAdapterCallback callback;

    public BawahanListAdapter(Context context, List<PersonalInfo> objects, BawahanListAdapterCallback listener) {
        this.context = context;
        this.bawahanList = objects;
        this.callback = listener;
    }

    @Override
    public BawahanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bawahan, parent, false);
        BawahanListViewHolder holder = new BawahanListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BawahanListViewHolder holder, final int position) {
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" + this.bawahanList.get(position).getUrlfoto())));

        Picasso.with(this.context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(holder.loadedBitmap);

        holder.tv_nama_item_bawahan.setText(this.bawahanList.get(position).getNama());
        holder.rl_container_item_bawahan.setOnClickListener(new ActionClick(position));
    }

    @Override
    public int getItemCount() {
        return this.bawahanList.size();
    }

    private class ActionClick implements View.OnClickListener {
        private int mPosition;

        public ActionClick(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            callback.onBawahanListAdapterCallback(mPosition);
        }
    }

    public static class BawahanListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_container_item_bawahan;
        private CircleImageView civ_pict_item_bawahan;
        private CustomTextView tv_nama_item_bawahan;
        private Target loadedBitmap;

        BawahanListViewHolder(View view) {
            super(view);
            this.rl_container_item_bawahan = (RelativeLayout) view.findViewById(R.id.rl_container_item_bawahan);
            this.civ_pict_item_bawahan = (CircleImageView) view.findViewById(R.id.civ_pict_item_bawahan);
            this.tv_nama_item_bawahan = (CustomTextView) view.findViewById(R.id.tv_nama_item_bawahan);
            if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    civ_pict_item_bawahan.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            this.civ_pict_item_bawahan.setTag(this.loadedBitmap);

        }
    }

//    @Override
//    public int getCount() {
//        return this.bawahanList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return this.bawahanList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bawahan, parent, false);
//
//        BawahanListViewHolder holder = new BawahanListViewHolder();
//
//        holder.rl_container_item_bawahan = (RelativeLayout) convertView.findViewById(R.id.rl_container_item_bawahan);
//        holder.civ_pict_item_bawahan = (CircleImageView) convertView.findViewById(R.id.civ_pict_item_bawahan);
//        holder.tv_nama_item_bawahan = (TextView) convertView.findViewById(R.id.tv_nama_item_bawahan);
//
//        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.bawahanList.get(position).getBawahanUrl())));
//
//        Picasso.with(this.context)
//                .load(url.toString())
//                .placeholder(R.drawable.placeholder)
//                .into(holder.civ_pict_item_bawahan);
//
//        holder.tv_nama_item_bawahan.setText(this.bawahanList.get(position).getBawahanNama());
//
//        convertView.setTag(holder);
//
//        return convertView;
//    }
//
//    private static class BawahanListViewHolder {
//        private RelativeLayout rl_container_item_bawahan;
//        private CircleImageView civ_pict_item_bawahan;
//        private TextView tv_nama_item_bawahan;
//    }
}
