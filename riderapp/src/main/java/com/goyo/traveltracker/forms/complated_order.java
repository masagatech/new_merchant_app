package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.ComplatedOrderAdapter;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_completed;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import static com.goyo.traveltracker.gloabls.Global.urls.getTripStops;

public class complated_order extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private com.goyo.traveltracker.adapters.ComplatedOrderAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complated_order);

        //setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setLogo(R.drawable.rider_del);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle(getResources().getString(R.string.Complated_Order));

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


    private void DataFromServer(){
//
//            loader = new ProgressDialog(this);
//            loader.setCancelable(false);
//            loader.setMessage(getResources().getString(R.string.wait_msg));
//            loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("uid", Global.loginusr.getDriverid());
        json.addProperty("flag", "uid");
        Ion.with(this)
                .load(getTripStops.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_completed>>() {
                            }.getType();
                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
//                        if(flag==1){
//                            loader.hide();
//                        }else {
                        mSwipeRefreshLayout.setRefreshing(false);
//                        }

                    }
                });



//        Ion.with(this)
//                .load("GET", getOrders.value)
//                .addQuery("flag", "completed")
//                .addQuery("subflag", "smry")
//                .addQuery("rdid", Global.loginusr.getDriverid() + "")
//                .addQuery("stat","1")
//
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_completed>>() {
//                            }.getType();
//                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        if(flag==1){
////                            loader.hide();
////                        }else {
//                            mSwipeRefreshLayout.setRefreshing(false);
////                        }
//
//                    }
//                });
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }
    private void bindCurrentTrips(List<model_completed> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new ComplatedOrderAdapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }

    //set action bar button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            case R.id.today_visit_map:
                Intent i = new Intent(complated_order.this, TodayVisitsMapsActivity.class);
                startActivity(i);
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
