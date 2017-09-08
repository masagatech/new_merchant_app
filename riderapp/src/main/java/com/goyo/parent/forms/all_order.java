package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.AllOrdersAdapter;
import com.goyo.parent.database.SQLBase;
import com.goyo.parent.database.Tables;
import com.goyo.parent.gloabls.Global;
import com.goyo.parent.model.model_completed;
import com.goyo.parent.model.model_tag;
import com.goyo.parent.model.model_tag_db;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.goyo.parent.gloabls.Global.urls.getTagEmployeeMap;

public class all_order extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.parent.adapters.AllOrdersAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
   private int []Stat;
    private ArrayList<model_tag_db> data;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setLogo(R.drawable.rider_del);
//            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle(getResources().getString(R.string.All_Order));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);

        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                DataFromServer();
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server
                DataFromServer();
            }
        });
    }

    private void DataFromServer() {

//        loader = new ProgressDialog(this);
//        loader.setCancelable(false);
//        loader.setMessage(getResources().getString(R.string.wait_msg));
//        loader.show();

        data= populateList();

        if (data.size() > 0) {
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTimeLineAdapter = new AllOrdersAdapter(data, mOrientation, mWithLinePadding,Stat);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);

//        JsonObject json = new JsonObject();
//        json.addProperty("enttid", Global.loginusr.getEnttid());
//        json.addProperty("flag", "all");
//        Ion.with(this)
//                .load(getTagDetails.value)
//                .setJsonObjectBody(json)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        // do stuff with the result or error
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            // JSONObject jsnobject = new JSONObject(jsond);
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                            SavetoDb(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });



//        Ion.with(this)
//                .load("GET", getOrders.value)
//                .addQuery("flag", "completed")
//                .addQuery("status", "0")
//                .addQuery("subflag", "smry")
//                .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                .addQuery("stat","0")
//
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//                            Stat = new int[result.get("data").getAsJsonArray().size()];
//                            for (int i=0;i<result.get("data").getAsJsonArray().size();i++){
//                                JsonObject Data = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
//                                Stat[i]=Data.get("stsi").getAsInt();
//                            }
//                            bindCurrentTrips(events,Stat);
//
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        loader.hide();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });

    }

    private void DatafromServer(){

        loader = new ProgressDialog(this);
        loader.setCancelable(false);
        loader.setMessage(this.getString(R.string.wait_msg));
        loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("enttid", Global.loginusr.getEnttid());
        json.addProperty("empid", Global.loginusr.getDriverid());
        json.addProperty("flag", "byemp");
        Ion.with(this)
                .load(getTagEmployeeMap.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_completed>>() {
                            }.getType();
                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
                            SavetoDb(events);
                            DataFromServer();
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
                    }
                });
    }



    private ArrayList<model_tag_db> populateList(){
        SQLBase db = new SQLBase(this);
        ArrayList<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String,String>> d = db.Get_Tags();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db( d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title),d.get(i).get(Tables.tbltags.Tag_remark_1),d.get(i).get(Tables.tbltags.Tag_remark_2),d.get(i).get(Tables.tbltags.Tag_remark_3),d.get(i).get(Tables.tbltags.Tag_Creat_On),d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        return data;
    }


    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

//    private void bindCurrentTrips(List<model_completed> lst) {
//        if (lst.size() > 0) {
//            findViewById(R.id.txtNodata).setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//            mTimeLineAdapter = new AllOrdersAdapter(lst, mOrientation, mWithLinePadding,Stat);
//            mRecyclerView.setAdapter(mTimeLineAdapter);
//            mTimeLineAdapter.notifyDataSetChanged();
//
//        } else {
//            mRecyclerView.setVisibility(View.INVISIBLE);
//            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
//        }
//    }

    private void SavetoDb(List<model_completed> lst) {
        String Empl_Id= String.valueOf(Global.loginusr.getDriverid());
        SQLBase db = new SQLBase(this);
        if (lst.size() > 0) {
            for (int i = 0; i <= lst.size() - 1; i++) {
                //checking if tag alredy exist
                if (!db.ISTAG_ALREDY_EXIST(lst.get(i).tagnm)) {
                    db.TAG_ADDTAG(new model_tag(lst.get(i).tagnm, lst.get(i).remark2, lst.get(i).remark3, lst.get(i).remark33, Empl_Id, lst.get(i).createdby, "2"));
                }
            }
        }

    }

    public void SendOfflineTagstoServer() {
        SQLBase db = new SQLBase(this);
        final List<HashMap<String,String>> d = db. Get_Tags_Offline();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                   final int pos=i;
                    JsonObject json = new JsonObject();
                    json.addProperty("tagnm", d.get(i).get(Tables.tbltags.Tag_Title));
                    json.addProperty("remark1", d.get(i).get(Tables.tbltags.Tag_remark_1));
                    json.addProperty("remark2", d.get(i).get(Tables.tbltags.Tag_remark_2));
                    json.addProperty("remark3", d.get(i).get(Tables.tbltags.Tag_remark_3));
                    json.addProperty("cuid", d.get(i).get(Tables.tbltags.Tag_Creat_On));
                    json.addProperty("enttid", Global.loginusr.getEnttid()+"");
                    json.addProperty("tagtype","m");
                    Ion.with(this)
                            .load(Global.urls.saveTagInfo.value)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    // do stuff with the result or error
                                    try {
                                        SQLBase db = new SQLBase(all_order.this);
                                        db. TAG_UPDATE(d.get(pos).get(Tables.tbltags.Tag_Title),"0");

                                    } catch (Exception ea) {
                                        ea.printStackTrace();
                                    }


                                }
                            });
            }
        }

//        return data;
    }

    //set action bar button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_info_view_activity, menu);
        return true;
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

            case R.id.menu_driver_info_view_add:
                Intent intent=new Intent(this,AddTags.class);
                startActivity(intent);
                return true;

            case R.id.Sync:
//                mSwipeRefreshLayout.setRefreshing(true);
//                SendOfflineTagstoServer();
                DatafromServer();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}