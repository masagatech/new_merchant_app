package com.goyo.parent;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by mis on 02-Oct-17.
 */

public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
