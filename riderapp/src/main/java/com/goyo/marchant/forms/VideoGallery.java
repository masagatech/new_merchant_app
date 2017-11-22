package com.goyo.marchant.forms;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.gson.JsonObject;
import com.goyo.marchant.R;
import com.goyo.marchant.adapters.VideoListAdapter;
import com.goyo.marchant.adapters.YouTubeVideo;
import com.goyo.marchant.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static com.goyo.marchant.R.id.txtNodata;
import static com.goyo.marchant.forms.GalleryInfo.AlbumID;
import static com.goyo.marchant.forms.dashboard.SclId;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoGallery extends Fragment {

    private List<YouTubeVideo> ITEMS ;

    /**
     * A map of YouTube videos, by ID.
     */

    ListView listView;
    private View view;


    public VideoGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_video_gallery, container, false);
        listView = (ListView) view.findViewById(R.id.listview);

        //Check for any issues
        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());

        if (result != YouTubeInitializationResult.SUCCESS) {
            //If there are any issues we can show an error dialog.
            result.getErrorDialog(getActivity(), 0).show();
        }


        GetImages();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context context = getActivity();
                final String DEVELOPER_KEY = getString(R.string.DEVELOPER_KEY);
                final YouTubeVideo video = ITEMS.get(position);
                startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        DEVELOPER_KEY, video.id, 0, true, true));


            }
        });


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
                        ITEMS = new ArrayList<>();
                        // do stuff with the result or error
                        try {
                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                if(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("ghead").getAsString().equals("video")) {
                                    ITEMS.add(new YouTubeVideo(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("thumbid").getAsString()));
                                }
                            }

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        SetImage(ITEMS);

                    }
                });
    }

    private void SetImage(List<YouTubeVideo> ITEMS) {

        if(ITEMS.size()>0) {
            view.findViewById(txtNodata).setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(new VideoListAdapter(getActivity(),ITEMS));
        }else {
            listView.setVisibility(View.GONE);
            view.findViewById(txtNodata).setVisibility(View.VISIBLE);
        }
    }

}
