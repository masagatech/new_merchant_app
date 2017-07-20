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
import com.goyo.traveltracker.adapters.AllOrdersAdapter;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_completed;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import static com.goyo.traveltracker.gloabls.Global.urls.getOrders;

public class all_order extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.traveltracker.adapters.AllOrdersAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
   private int []Stat;
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

        Ion.with(this)
                .load("GET", getOrders.value)
                .addQuery("flag", "completed")
                .addQuery("status", "0")
                .addQuery("subflag", "smry")
                .addQuery("rdid", Global.loginusr.getDriverid() + "")
                .addQuery("stat","0")

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_completed>>() {
                            }.getType();
                            List<model_completed> events = (List<model_completed>) gson.fromJson(result.get("data"), listType);
                            Stat = new int[result.get("data").getAsJsonArray().size()];
                            for (int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                JsonObject Data = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
                                Stat[i]=Data.get("stsi").getAsInt();
                            }
                            bindCurrentTrips(events,Stat);

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
//                        loader.hide();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void bindCurrentTrips(List<model_completed> lst,int []Stat) {
        if (lst.size() > 0) {
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTimeLineAdapter = new AllOrdersAdapter(lst, mOrientation, mWithLinePadding,Stat);
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
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}