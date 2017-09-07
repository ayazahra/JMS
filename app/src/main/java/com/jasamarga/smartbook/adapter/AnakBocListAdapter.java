package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.object.BocData;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomBaseAdapter;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 9/22/16.
 */
public class AnakBocListAdapter extends CustomBaseAdapter {

    private Context context;
    private List<BocData> bocDataList;
    private AnakBocListViewHolder holder;
    private Target loadedBitmap;

    public AnakBocListAdapter(Context context, List<BocData> objects) {
        this.context = context;
        this.bocDataList = objects;
    }

    @Override
    public int getCount() {
        return this.bocDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.bocDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_anakboc, parent, false);

            this.holder = new AnakBocListViewHolder();

            this.holder.rl_container_item_anakboc = (RelativeLayout) convertView.findViewById(R.id.rl_container_item_search);
            this.holder.civ_pict_item_anakboc = (CircleImageView) convertView.findViewById(R.id.civ_pict_item_search);
            this.holder.tv_title_item_anakboc = (CustomTextView) convertView.findViewById(R.id.tv_title_item_search);
            this.holder.tv_position_item_anakboc = (CustomTextView) convertView.findViewById(R.id.tv_position_item_search);

            convertView.setTag(this.holder);
        }else {
            this.holder = (AnakBocListViewHolder) convertView.getTag();
        }

        if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                holder.civ_pict_item_anakboc.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                notifyItemChanged(position);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        this.holder.civ_pict_item_anakboc.setTag(this.loadedBitmap);
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" + this.bocDataList.get(position).getUrl())));
        Picasso.with(context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);

        this.holder.tv_title_item_anakboc.setText(this.bocDataList.get(position).getNama());
        this.holder.tv_position_item_anakboc.setText(this.bocDataList.get(position).getJabatan());

        return convertView;
    }

    private static class AnakBocListViewHolder {
        private RelativeLayout rl_container_item_anakboc;
        private CircleImageView civ_pict_item_anakboc;
        private CustomTextView tv_title_item_anakboc, tv_position_item_anakboc;
    }

}
