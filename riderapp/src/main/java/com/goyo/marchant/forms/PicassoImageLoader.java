package com.goyo.marchant.forms;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

/**
 * Created by mis on 02-Oct-17.
 */

public class PicassoImageLoader implements MediaLoader {

    private String url;

    public PicassoImageLoader(String url) {
        this.url = url;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
        Glide.with(context).load(url)
                .asBitmap().fitCenter().into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation)
            {
                imageView.setImageBitmap(bitmap);
                callback.onSuccess();
            }
        });
    }

    @Override
    public void loadThumbnail(Context context,final ImageView thumbnailView, final MediaLoader.SuccessCallback callback) {
        Glide.with(context).load(url)
                .asBitmap().override(200,200).centerCrop().fitCenter().into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation)
            {
                thumbnailView.setImageBitmap(bitmap);
                callback.onSuccess();
            }
        });
    }

}
