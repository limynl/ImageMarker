package com.wangjie.rapidfloatingactionbutton.expandingcollection;

import android.graphics.BitmapFactory;

/**
 * Bitmap Factory Options with inScaled flag disabled by default
 */
public class BitmapFactoryOptions extends BitmapFactory.Options {
    public BitmapFactoryOptions() {
        this.inScaled = false;
    }
}
