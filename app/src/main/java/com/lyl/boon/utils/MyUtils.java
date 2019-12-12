package com.lyl.boon.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.lyl.boon.R;

import java.util.Random;

/**
 * Wing_Li
 * 2016/6/21.
 */
public class MyUtils {

    public static int[] mTextColors = {R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color
            .color6, R.color.color7, R.color.color8, R.color.color9, R.color.color10};

    public static int getColors() {
        return mTextColors[new Random().nextInt(6)];
    }

    public static void setClipText(Context context, String str) {
        ClipboardManager clipboardManager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("test", str);
        clipboardManager.setPrimaryClip(clipData);
    }
}
