package com.jasamarga.smartbook.widget;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.nhaarman.listviewanimations.appearance.SingleAnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by apridosandyasa on 10/2/16.
 */

public class CustomSwingBottomInAnimationAdapter extends SingleAnimationAdapter {

    private static final String TRANSLATION_Y = "translationY";

    public CustomSwingBottomInAnimationAdapter(@NonNull final android.widget.BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    @NonNull
    protected Animator getAnimator(@NonNull final ViewGroup parent, @NonNull final View view) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 1, 0);
    }

}
