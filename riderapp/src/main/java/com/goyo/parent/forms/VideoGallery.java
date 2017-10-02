package com.goyo.parent.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.adapters.VideoListAdapter;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import static com.goyo.parent.R.id.txtNodata;
import static com.goyo.parent.forms.GalleryInfo.AlbumID;
import static com.goyo.parent.forms.dashboard.SclId;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoGallery extends Fragment {

    ListView listView;
    private View view;

    ArrayList<String> videoUrls;
    ArrayList<String> videoThumbs;

    public VideoGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_video_gallery, container, false);
        listView = (ListView) view.findViewById(R.id.listview);

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
                           final String YoutubeURL="http://img.youtube.com/vi/";
                            videoUrls = new ArrayList<String>();
                            videoThumbs = new ArrayList<String>();
                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                if(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("ghead").getAsString().equals("video")) {
                                    videoUrls.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("gurl").getAsString());
                                    videoThumbs.add(YoutubeURL+result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("thumb").getAsString());
                                }
                            }
                            SetImage(videoUrls,videoThumbs);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });
    }

    private void SetImage(ArrayList<String> videoUrls,ArrayList<String> videoThumbs) {

        if(videoUrls.size()>0) {
            view.findViewById(txtNodata).setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new VideoListAdapter(getActivity(),
                    videoUrls,
                    videoThumbs));
        }else {
            listView.setVisibility(View.GONE);
            view.findViewById(txtNodata).setVisibility(View.VISIBLE);
        }
    }

}
