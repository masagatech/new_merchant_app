package com.goyo.parent.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goyo.parent.R;

import java.util.ArrayList;

/**
 * Created by mis on 02-Oct-17.
 */

public class VideoListAdapter  extends BaseAdapter  {

    public static final String TAG = "JiaoZiVideoPlayer";

    Context context;

    ArrayList<String> videoUrls;
    ArrayList<String> videoThumbs;

    public VideoListAdapter(Context context, ArrayList<String> videoUrls, ArrayList<String> videoThumbs) {
        this.context = context;
        this.videoUrls = videoUrls;
        this.videoThumbs = videoThumbs;
    }

    @Override
    public int getCount() {
        return videoUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_videoview, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.Video = (ImageView) convertView.findViewById(R.id.videoplayer);

        Glide.with(convertView.getContext()).load(videoThumbs.get(position))
                .thumbnail(0.5f)
                .placeholder(R.drawable.im)
                .into(viewHolder.Video);


        viewHolder.Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrls.get(position))));
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView Video;
//        JZVideoPlayerStandard jzVideoPlayer;
    }
}
