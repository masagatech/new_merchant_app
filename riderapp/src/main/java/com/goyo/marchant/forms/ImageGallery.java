package com.goyo.marchant.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.goyo.marchant.R;
import com.goyo.marchant.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.ArrayList;
import java.util.List;

import static com.goyo.marchant.R.id.txtNodata;
import static com.goyo.marchant.forms.GalleryInfo.AlbumID;
import static com.goyo.marchant.forms.dashboard.SclId;
import static com.goyo.marchant.gloabls.Global.IMAGE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageGallery extends Fragment{


    private ArrayList<String> images ;

//            = new ArrayList<>(Arrays.asList(
//            "http://img1.goodfon.ru/original/1920x1080/d/f5/aircraft-jet-su-47-berkut.jpg",
//            "http://www.dishmodels.ru/picture/glr/13/13312/g13312_7657277.jpg",
//            "http://img2.goodfon.ru/original/1920x1080/b/c9/su-47-berkut-c-37-firkin.jpg"
//    ));

    private ScrollGalleryView scrollGalleryView;
    private View view;

    public ImageGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_image_gallery, container, false);
        scrollGalleryView = (ScrollGalleryView) view.findViewById(R.id.scroll_gallery_view);

        GetImages();

        return view;
    }

    private void GetImages() {
        JsonObject json = new JsonObject();
        json.addProperty("albumid",AlbumID);
        json.addProperty("flag", "byentt");
        json.addProperty("enttid", SclId + "");
        Ion.with(this)
                .load(Global.urls.getgallerydetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            images = new ArrayList<String>();
                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                if(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("ghead").getAsString().equals("photo")) {
                                    images.add(IMAGE_URL+result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("url").getAsString());
                                }
                            }
                            SetImage(images);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });
    }

    private void SetImage(ArrayList<String> images){
        if(images.size()>0) {
            List<MediaInfo> infos = new ArrayList<>(images.size());
            for (String url : images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));
            view.findViewById(txtNodata).setVisibility(View.GONE);
            scrollGalleryView.setVisibility(View.VISIBLE);
            scrollGalleryView
                    .setThumbnailSize(250)
                    .setZoom(true)
                    .setFragmentManager(getFragmentManager())
//                .addMedia(MediaInfo.mediaLoader(new DefaultVideoLoader(movieUrl, R.mipmap.default_video)))
                    .addMedia(infos);
        }else {
            scrollGalleryView.setVisibility(View.GONE);
            view.findViewById(txtNodata).setVisibility(View.VISIBLE);
        }

    }

}
