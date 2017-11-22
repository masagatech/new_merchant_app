package com.goyo.marchant.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goyo.marchant.R;
import com.goyo.marchant.forms.GalleryInfo;
import com.goyo.marchant.model.modal_gallery;

import java.util.ArrayList;

import static com.goyo.marchant.gloabls.Global.IMAGE_URL;

/**
 * Created by mis on 02-Oct-17.
 */

public class AlbumAdapter extends BaseAdapter {
    Context c;
    ArrayList<modal_gallery> spacecrafts;
    public AlbumAdapter(Context c, ArrayList<modal_gallery> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }
    @Override
    public int getCount() {
        return spacecrafts.size();
    }
    @Override
    public Object getItem(int i) {
        return spacecrafts.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.album_item,viewGroup,false);
        }
        final modal_gallery s= (modal_gallery) this.getItem(i);
        ImageView Album_Cover= (ImageView) view.findViewById(R.id.Album_Cover);
        TextView Album_Disc= (TextView) view.findViewById(R.id.Album_Disc);
        TextView Album_Title= (TextView) view.findViewById(R.id.Album_Title);
        TextView Album_Vid= (TextView) view.findViewById(R.id.Album_Vid);
        TextView Album_Aud =(TextView) view.findViewById(R.id.Album_Aud);
        TextView Album_Date =(TextView) view.findViewById(R.id.Album_Date);
        TextView Album_Img =(TextView) view.findViewById(R.id.Album_Img);
        FrameLayout background =(FrameLayout) view.findViewById(R.id.background);

        //BIND

        String imgUrl = IMAGE_URL+s.coverph;
        Glide.with(c).load(imgUrl)
                .placeholder(R.drawable.im)
                .into(Album_Cover);

        Album_Title.setText(s.title);
        Album_Disc.setText(s.desc);
        Album_Vid.setText(s.countvd);
        Album_Aud.setText(s.countad);
        Album_Date.setText(s.date);
        Album_Img.setText(s.countph);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(c, GalleryInfo.class);
                intent.putExtra("AlbumID",s.albumid);
                c.startActivity(intent);
            }
        });


        return view;
    }
}
