package com.example.tcpplayground;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class InstantAutoComplete extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public InstantAutoComplete(Context context) {
        super(context);
    }

    public InstantAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InstantAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
            showDropDown();
        }
    }
}
