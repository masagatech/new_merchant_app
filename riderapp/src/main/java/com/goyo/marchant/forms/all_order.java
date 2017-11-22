package com.goyo.marchant.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.marchant.R;
import com.goyo.marchant.adapters.AllOrdersAdapter;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.model.modal_assignment;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import static com.goyo.marchant.forms.dashboard.SclId;
import static com.goyo.marchant.gloabls.Global.urls.getAssignmentDetails;

public class all_order extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.marchant.adapters.AllOrdersAdapter mTimeLineAdapter;
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
        JsonObject json = new JsonObject();
        json.addProperty("flag", "byparents");
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("enttid", SclId+"");
        Ion.with(this)
                .load(getAssignmentDetails.value)
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
                            Type listType = new TypeToken<List<modal_assignment>>() {
                            }.getType();
                            List<modal_assignment> events = (List<modal_assignment>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

//                    }
                });
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void bindCurrentTrips(List<modal_assignment> lst) {
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


//    //set action bar button menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_driver_info_view_activity, menu);
//        return true;
//    }


    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;

//            case R.id.menu_driver_info_view_add:
//                Intent intent=new Intent(this,AddTags.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.Sync:
////                mSwipeRefreshLayout.setRefreshing(true);
////                SendOfflineTagstoServer();
//                DatafromServer();
//                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}