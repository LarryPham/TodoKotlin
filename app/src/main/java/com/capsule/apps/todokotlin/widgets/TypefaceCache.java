package com.capsule.apps.todokotlin.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcel;
import android.util.AttributeSet;
import android.widget.TextView;

import com.capsule.apps.todokotlin.R;

import java.util.Hashtable;

/**
 * @author Larry Pham
 * @date: ${SHORT_NAME_MONTH}.07.2016
 * <br/>
 * Email: larrypham.vn@gmail.com.
 * Copyright (C) 2016 Capsule Inc. All rights reserved.
 */
public class TypefaceCache {

    public static final int VARIATION_NORMAL = 0;

    private static final Hashtable<String, Typeface> mTypefaceCache = new Hashtable<>();

    public static Typeface getTypeface(Context context) {
        return getTypeface(context, Typeface.NORMAL, VARIATION_NORMAL);
    }

    public static Typeface getTypeface(Context context, int fontStyle, int variation) {
        if (context == null) {
            return null;
        }

        String typefaceName = "Bitter-Regular.otf";
        if (variation == VARIATION_NORMAL) {
            switch (fontStyle) {
                case Typeface.ITALIC: {
                    typefaceName = "Bitter-Italic.otf";
                    break;
                }
                case Typeface.BOLD: {
                    typefaceName = "Bitter-Bold.otf";
                    break;
                }
                case Typeface.BOLD_ITALIC: {
                    typefaceName = "Bitter-BoldItalic.otf";
                    break;
                }
                case Typeface.NORMAL: {
                    typefaceName = "Bitter-Regular.otf";
                    break;
                }
            }
        }
        return getTypefaceForTypefaceName(context, typefaceName);
    }

    private static Typeface getTypefaceForTypefaceName(Context context, String typefaceName) {
        if (!mTypefaceCache.containsKey(typefaceName)) {
            Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/" + typefaceName);
            if (typeface != null) {
                mTypefaceCache.put(typefaceName, typeface);
            }
        }
        return mTypefaceCache.get(typefaceName);
    }

    /**
     * Sets the typeface for TextView (or TextView descendant such as EditText or Button) based on the passed attributes, default to normal typeface
     * @param context The Application Context.
     * @param textView The TextView's instance.
     * @param attrs The passed TextView's attributes.
     */
    public static void setCustomTypeface(Context context, TextView textView, AttributeSet attrs) {
        if (context == null || textView == null) {
            return;
        }

        if (textView.isInEditMode()) {
            return;
        }

        int variation = TypefaceCache.VARIATION_NORMAL;
        if (attrs != null) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OpenTextView, 0, 0);
            if (array != null) {
                try {
                    variation = array.getInteger(R.styleable.OpenTextView_fontVariation, TypefaceCache.VARIATION_NORMAL);
                } finally {
                    array.recycle();
                }
            }
        }

        final int fontStyle;
        if (textView.getTypeface() != null) {
            boolean isBold = textView.getTypeface().isBold();
            boolean isItalic = textView.getTypeface().isItalic();

            if (isBold && isItalic) {
                fontStyle = Typeface.BOLD_ITALIC;
            } else if (isBold) {
                fontStyle = Typeface.BOLD;
            } else if (isItalic) {
                fontStyle = Typeface.ITALIC;
            } else {
                fontStyle = Typeface.NORMAL;
            }
        } else {
            fontStyle = Typeface.NORMAL;
        }

        Typeface typeface = getTypeface(context, fontStyle, variation);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }
}
