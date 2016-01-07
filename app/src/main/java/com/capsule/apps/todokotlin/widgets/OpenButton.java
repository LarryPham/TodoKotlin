package com.capsule.apps.todokotlin.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import com.capsule.apps.todokotlin.R;

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */
public class OpenButton extends Button {
    public boolean mFixWindowWordEnabled = false;

    public OpenButton(Context context) {
        super(context);
        TypefaceCache.setCustomTypeface(context, this, null);
    }

    public OpenButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypefaceCache.setCustomTypeface(context, this, attrs);
        readCustomAttributes(context, attrs);
    }

    public OpenButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypefaceCache.setCustomTypeface(context, this, attrs);
        readCustomAttributes(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!mFixWindowWordEnabled) {
            super.setText(text, type);
            return;
        }

        Spannable out;
        int lastSpace = String.valueOf(text).lastIndexOf(' ');
        if (lastSpace != -1 && lastSpace < text.length() - 1) {
            CharSequence tmpText = replaceCharacter(text, lastSpace, "\u00A0");
            out = new SpannableString(tmpText);

            if (text instanceof Spanned) {
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(), null, out, 0);
            }
        } else {
            out = new SpannableString(text);
        }
        super.setText(out, type);
    }

    private void readCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OpenTextView, 0, 0);
        if (array != null) {
            mFixWindowWordEnabled = array.getBoolean(R.styleable.OpenTextView_fixWidowWords, false);

            if (mFixWindowWordEnabled) {
                setText(getText());
            }
        }
    }

    private CharSequence replaceCharacter(CharSequence source, int charIndex, CharSequence replacement) {
        if (charIndex != -1 && charIndex < source.length() - 1) {
            return TextUtils.concat(source.subSequence(0, charIndex), replacement, source.subSequence(charIndex + 1, source.length()));
        }
        return source;
    }
}
