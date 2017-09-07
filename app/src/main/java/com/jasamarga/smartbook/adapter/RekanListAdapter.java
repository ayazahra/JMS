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
import android.widget.LinearLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.callback.RekanListAdapterCallback;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public class RekanListAdapter extends RecyclerView.Adapter<RekanListAdapter.RekanListViewHolder> {

    private final static String TAG = RekanListAdapter.class.getSimpleName();
    private Context context;
    private List<PersonalInfo> rekanList;
    private RekanListAdapterCallback callback;

    public RekanListAdapter(Context context, List<PersonalInfo> objects, RekanListAdapterCallback listener) {
        this.context = context;
        this.rekanList = objects;
        this.callback = listener;
    }

    @Override
    public RekanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rekan, parent, false);
        RekanListViewHolder holder = new RekanListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RekanListViewHolder holder, final int position) {
        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" +this.rekanList.get(position).getUrlfoto())));

        Picasso.with(this.context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(holder.loadedBitmap);

        holder.ll_container_item_rekan.setOnClickListener(new ActionClick(position));
    }

    @Override
    public int getItemCount() {
        return this.rekanList.size();
    }

    private class ActionClick implements View.OnClickListener {
        private int mPosition;

        public ActionClick(int position) {
            this.mPosition = position;
        }
        @Override
        public void onClick(View v) {
            callback.onRekanListAdapterCallback(mPosition);
        }
    }

    public static class RekanListViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_container_item_rekan;
        private CircleImageView civ_pict_item_rekan;
        private Target loadedBitmap;

        RekanListViewHolder(View view) {
            super(view);
            this.ll_container_item_rekan = (LinearLayout) view.findViewById(R.id.ll_container_item_rekan);
            this.civ_pict_item_rekan = (CircleImageView) view.findViewById(R.id.civ_pict_item_rekan);
            if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    civ_pict_item_rekan.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            this.civ_pict_item_rekan.setTag(this.loadedBitmap);

        }

    }
}
