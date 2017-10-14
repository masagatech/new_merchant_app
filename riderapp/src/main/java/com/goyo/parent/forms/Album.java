package com.goyo.parent.forms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.AlbumAdapter;
import com.goyo.parent.model.modal_gallery;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.goyo.parent.forms.dashboard.SclId;
import static com.goyo.parent.gloabls.Global.urls.getalbumdetails;

public class Album extends AppCompatActivity {
    AlbumAdapter adapter;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Photo Gallery");

        DataFromServer();
    }


    private void DataFromServer() {
        JsonObject json = new JsonObject();
        json.addProperty("flag", "byentt");
        json.addProperty("enttid", SclId + "");
        Ion.with(this)
                .load(getalbumdetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            // JSONObj                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ect jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<ArrayList<modal_gallery>>() {
                            }.getType();
                            ArrayList<modal_gallery> events = (ArrayList<modal_gallery>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }

//                    }
                });
    }


        private void bindCurrentTrips(ArrayList<modal_gallery> lst) {
            if (lst.size() > 0) {
//                findViewById(R.id.txtNodata).setVisibility(View.GONE);
                gv= (GridView) findViewById(R.id.gv);
                adapter=new AlbumAdapter(this,lst);
                gv.setAdapter(adapter);
               adapter.notifyDataSetChanged();

            } else {
//                mRecyclerView.setVisibility(View.INVISIBLE);
//                findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
            }
        }


    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
