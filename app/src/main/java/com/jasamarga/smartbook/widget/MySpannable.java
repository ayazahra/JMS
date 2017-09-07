package com.jasamarga.smartbook.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by apridosandyasa on 7/22/16.
 */
public class MySpannable extends ClickableSpan {
    private boolean isUnderline = true;

    /**
     * Constructor
     */
    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {

        ds.setUnderlineText(isUnderline);

    }

    @Override
    public void onClick(View widget) {

    }

}
