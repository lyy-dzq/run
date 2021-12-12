package com.llw.run.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommUtils {

    private static InputMethodManager imm;

    public static void hide(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
