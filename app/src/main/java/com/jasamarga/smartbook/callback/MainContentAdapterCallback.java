package com.jasamarga.smartbook.callback;

import com.jasamarga.smartbook.object.MainContentData;
import com.jasamarga.smartbook.object.Submenu;

import java.util.List;

/**
 * Created by apridosandyasa on 8/8/16.
 */
public interface MainContentAdapterCallback {
    void onMainContentAdapterCallback(List<Submenu> object, int position, int mainMenuId);
}
