package com.jasamarga.smartbook.callback;

import android.graphics.Bitmap;

import com.jasamarga.smartbook.object.PersonalInfoSearch;

import java.util.List;

/**
 * Created by apridosandyasa on 8/12/16.
 */
public interface PersonalInfoAdapterCallback {
    void onPersonalInfoAdapterCallback(List<PersonalInfoSearch> personalInfoSearchList, int position);
}
