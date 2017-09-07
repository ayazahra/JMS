package com.jasamarga.smartbook.adapter;

import android.content.Context;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jasamarga.smartbook.R;
import com.jasamarga.smartbook.object.MainMenu;
import com.jasamarga.smartbook.utility.ConstantAPI;
import com.jasamarga.smartbook.utility.Utility;
import com.jasamarga.smartbook.widget.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public class NavigationViewAdapter extends BaseAdapter {

    private final String TAG = NavigationViewAdapter.class.getSimpleName();
    private Context context;
    private List<MainMenu> mainMenuList;

    public NavigationViewAdapter(Context context, List<MainMenu> mainMenuList) {
        this.context = context;
        this.mainMenuList = mainMenuList;
    }

    @Override
    public int getCount() {
        return this.mainMenuList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navigationview, parent, false);

        NavigationViewHolder holder = new NavigationViewHolder();
        holder.rl_group_item_navigationview = (RelativeLayout) convertView.findViewById(R.id.rl_group_item_navigationmenu);
        holder.rl_child_item_navigationview = (RelativeLayout) convertView.findViewById(R.id.rl_child_item_navigationmenu);
        holder.iv_icongroup_item_navigationview = (ImageView) convertView.findViewById(R.id.iv_icongroup_item_navigationmenu);
        holder.iv_iconchild_item_navigationview = (ImageView) convertView.findViewById(R.id.iv_iconchild_item_navigationmenu);
        holder.tv_titlegroup_item_navigationview = (CustomTextView) convertView.findViewById(R.id.tv_titlegroup_item_navigationmenu);
        holder.tv_titlechild_item_navigationview = (CustomTextView) convertView.findViewById(R.id.tv_titlechild_item_navigationmenu);

        CharSequence url = Utility.CharSequenceUtility.noTrailingwhiteLines(TextUtils.concat(Html.fromHtml(this.mainMenuList.get(position).getMainMenuUrl())));
        Log.d(TAG, "url " + ConstantAPI.BASE_URL + url.toString());

        if (this.mainMenuList.get(position).getMainMenuId() != 2) {
            holder.rl_child_item_navigationview.setVisibility(View.GONE);
            holder.rl_group_item_navigationview.setVisibility(View.VISIBLE);
            holder.tv_titlegroup_item_navigationview.setText(this.mainMenuList.get(position).getMainMenuDesc());
            if (!url.toString().equals("")) {
                Picasso.with(this.context)
                        .load(ConstantAPI.BASE_URL + "/public" + url.toString())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.iv_icongroup_item_navigationview);
            }else{
                holder.iv_icongroup_item_navigationview.setVisibility(View.GONE);
            }
        }else{
            holder.rl_group_item_navigationview.setVisibility(View.GONE);
            holder.rl_child_item_navigationview.setVisibility(View.VISIBLE);
            holder.tv_titlechild_item_navigationview.setText(this.mainMenuList.get(position).getMainMenuDesc());
            if (!url.toString().equals("")) {
                Picasso.with(this.context)
                        .load(ConstantAPI.BASE_URL + "/public" + url.toString())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.iv_iconchild_item_navigationview);
            }
        }

        convertView.setTag(holder);
        return convertView;
    }

    private static class NavigationViewHolder {
        private RelativeLayout rl_group_item_navigationview, rl_child_item_navigationview;
        private ImageView iv_icongroup_item_navigationview, iv_iconchild_item_navigationview;
        private CustomTextView tv_titlegroup_item_navigationview, tv_titlechild_item_navigationview;
    }

}
