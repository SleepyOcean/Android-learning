package com.sleepy.box.diary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by gehou on 2017/9/25.
 */

public class NavigationEditorLayout extends LinearLayout {
    public NavigationEditorLayout(Context context) {
        super(context);
    }

    public NavigationEditorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationEditorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
