package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.object.PersonalInfo;
import com.jasamarga.smartbook.object.PersonalInfoSearch;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomBaseAdapter;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class PersonalInfoSearchAdapter extends CustomBaseAdapter {

    private final static String TAG = PersonalInfoSearchAdapter.class.getSimpleName();
    private Context context;
    private List<PersonalInfo> personalInfoList;
    private PersonalInfoSearchViewHolder holder;
    private Target loadedBitmap;

    public PersonalInfoSearchAdapter(Context context, List<PersonalInfo> objects) {
        this.context = context;
        this.personalInfoList = objects;
    }

    @Override
    public int getCount() {
        return this.personalInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);

            this.holder = new PersonalInfoSearchViewHolder();

            this.holder.rl_container_item_search = (RelativeLayout) convertView.findViewById(R.id.rl_container_item_search);
            this.holder.civ_pict_item_search = (CircleImageView) convertView.findViewById(R.id.civ_pict_item_search);
            this.holder.tv_title_item_search = (CustomTextView) convertView.findViewById(R.id.tv_title_item_search);
            this.holder.tv_position_item_search = (CustomTextView) convertView.findViewById(R.id.tv_position_item_search);

            convertView.setTag(this.holder);
        }else {
            this.holder = (PersonalInfoSearchViewHolder) convertView.getTag();
        }

        if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                holder.civ_pict_item_search.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                notifyItemChanged(position);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        this.holder.civ_pict_item_search.setTag(this.loadedBitmap);

        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(ConstantAPI.BASE_URL + "/public/" + this.personalInfoList.get(position).getUrlfoto())));
        Log.d(TAG, "urlPersonalInfoListItems " + url.toString());
        Picasso.with(this.context)
                .load(url.toString())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);

        this.holder.tv_title_item_search.setText(this.personalInfoList.get(position).getNama());
        this.holder.tv_position_item_search.setText(this.personalInfoList.get(position).getJabatan());

        return convertView;
    }

    private static class PersonalInfoSearchViewHolder {
        private RelativeLayout rl_container_item_search;
        private CircleImageView civ_pict_item_search;
        private CustomTextView tv_title_item_search, tv_position_item_search;
    }

//    public static class PersonalInfoSearchViewHolder extends RecyclerView.ViewHolder {
//        private RelativeLayout rl_container_item_search;
//        private CircleImageView civ_pict_item_search;
//        private CustomTextView tv_title_item_search, tv_position_item_search;
//
//        PersonalInfoSearchViewHolder(View view) {
//            super(view);
//            this.rl_container_item_search = (RelativeLayout) view.findViewById(R.id.rl_container_item_search);
//            this.civ_pict_item_search = (CircleImageView) view.findViewById(R.id.civ_pict_item_search);
//            this.tv_title_item_search = (CustomTextView) view.findViewById(R.id.tv_title_item_search);
//            this.tv_position_item_search = (CustomTextView) view.findViewById(R.id.tv_position_item_search);
//        }
//    }
}
