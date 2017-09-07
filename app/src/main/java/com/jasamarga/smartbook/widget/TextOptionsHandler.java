package com.jasamarga.smartbook.widget;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import su.whs.watl.text.ContentView;
import su.whs.watl.ui.ITextView;

/**
 * Created by igor n. boulliev on 17.06.15.
 */
public class TextOptionsHandler {
    private ITextView textView;
    private Context ctx;

    public TextOptionsHandler(Context context, ITextView tv) {
        ctx = context;
        textView = tv;
    }

    public boolean onOptionsItemSelected() {
        ContentView.Options opts = textView.getOptions();
        setFontSize(android.R.style.TextAppearance_Medium);
        opts.setFilterEmptyLines(true).apply();
        return true;
    }

    MenuItem mi_textBig;
    MenuItem mi_textMed;
    MenuItem mi_textSmall;

    public void restoreState() {
        ContentView.Options opts = textView.getOptions();
        opts.isFilterEmptyLines();
        opts.getNewLineLeftMargin();
    }

    private void setFontSize(int appearance) {
        Button b =new Button(ctx);
        b.setTextAppearance(ctx,appearance);
        float size = b.getTextSize();
        ContentView.Options opts = textView.getOptions();
        opts.setTextSize(size).apply();
    }

    private void uncheckAll() {
        if (mi_textBig==null||mi_textMed==null||mi_textSmall==null) {
            Log.e("TextOptionsHandler", "missing call restoreState(menu) - check onCreateOptionsMenu()");
            return;
        }
        mi_textBig.setChecked(false);
        mi_textMed.setChecked(false);
        mi_textSmall.setChecked(false);
    }
}
