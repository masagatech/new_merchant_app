package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.PushOrderAdapter;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.model.model_expense;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.goyo.parent.R.id.recyclerView;
import static com.goyo.parent.gloabls.Global.urls.getParentDetails;


public class PushOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.parent.adapters.PushOrderAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private  String Status = "0";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<model_expense> data;
    private FloatingActionButton btbtnAddNew;
//    @BindView(R.id.bottomBars)
//    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (getSupportActionBar() != null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle("          Schools");

//        bottomBar.setDefaultTab(R.id.tab_new);
        mRecyclerView = (RecyclerView) findViewById(recyclerView);
        btbtnAddNew=(FloatingActionButton)findViewById(R.id.btbtnAddNew);

        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

//        SetPush(Status);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(getLinearLayoutManager());
//        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.Refresh);


        //adding student
        btbtnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PushOrder.this,MyKidsRegistration.class);
                startActivity(intent);
            }
        });

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
        json.addProperty("flag", "school");
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        Ion.with(this)
                .load(getParentDetails.value)
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
                            Type listType = new TypeToken<List<model_expense>>() {
                            }.getType();
                            List<model_expense> events = (List<model_expense>) gson.fromJson(result.get("data"), listType);
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
    private void bindCurrentTrips(List<model_expense> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new PushOrderAdapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }


//    private void bindCurrentTrips() {
//
//        data= populateList();
//
//        if (data.size() > 0) {
//            mRecyclerView.setVisibility(View.VISIBLE);
//            findViewById(R.id.txtNodata).setVisibility(View.GONE);
//            mTimeLineAdapter = new PushOrderAdapter(data, mOrientation, mWithLinePadding);
//            mRecyclerView.setAdapter(mTimeLineAdapter);
//            mTimeLineAdapter.notifyDataSetChanged();
//
//        } else {
//            mRecyclerView.setVisibility(View.INVISIBLE);
//            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
//        }
//
//        mSwipeRefreshLayout.setRefreshing(false);
//    }




    //action bar menu button click
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //Menu
//        switch (item.getItemId()) {
//            //When home is clicked
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
////            case R.id.menu_refresh:
////                GetExpenseData();
////                return true;
//            default:
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mTimeLineAdapter!=null){
//            mTimeLineAdapter.Kill();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mTimeLineAdapter!=null){
//            mTimeLineAdapter.Kill();
//        }
    }
}
