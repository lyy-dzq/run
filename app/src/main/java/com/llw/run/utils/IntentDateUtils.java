package com.llw.run.utils;

import java.util.List;

public class IntentDateUtils {
    private static IntentDateUtils intentDateUtils;

    private List<String> images;

    public static IntentDateUtils getInstance() {
        if (intentDateUtils == null) {
            intentDateUtils = new IntentDateUtils();
        }

        return intentDateUtils;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
