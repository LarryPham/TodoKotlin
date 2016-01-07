package com.capsule.apps.todokotlin.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */
public class EllipsizedTextView extends OpenTextView {
    private static final int MAX_ELLIPSE_LINES = 100;
    private int mMaxLines;

    public EllipsizedTextView(Context context) {
        super(context);
    }

    public EllipsizedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray array = context.obtainStyledAttributes(attrs, new int[] {android.R.attr.maxLines, defStyleAttr, 0});
        mMaxLines = array.getInteger(0, 1);
        array.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        CharSequence newText = getWidth() == 0 || mMaxLines > MAX_ELLIPSE_LINES  ? text : TextUtils.ellipsize(text, getPaint(), getWidth() * mMaxLines,
                TextUtils.TruncateAt.END, false, null);
        super.setText(text, type);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (width > 0 && oldWidth != width) {
            setText(getText());
        }
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        mMaxLines = maxLines;
    }
}
