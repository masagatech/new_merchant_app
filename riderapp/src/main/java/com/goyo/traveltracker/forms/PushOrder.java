package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.PushOrderAdapter;

import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_push_order;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.goyo.traveltracker.R.id.recyclerView;


public class PushOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.traveltracker.adapters.PushOrderAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private  String Status = "0";
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.bottomBars)
    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_order);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle(getResources().getString(R.string.Push_Order));

        bottomBar.setDefaultTab(R.id.tab_new);
        mRecyclerView = (RecyclerView) findViewById(recyclerView);

//        SetPush(Status);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(getLinearLayoutManager());
//        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);
        
        //tab list
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_new) {

                    Status="0";

                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            //api call
                            SetPush(Status);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            // Refresh items and get data from server
                            SetPush(Status);
                        }
                    });
//                    mRecyclerView.setLayoutManager(getLinearLayoutManager());
//                    mRecyclerView.setHasFixedSize(true);

                }else if(tabId == R.id.tab_waiting)
                {
                    Status="1";
//                    mRecyclerView.invalidate();
                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            //api call
                            SetPush(Status);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            // Refresh items and get data from server
                            SetPush(Status);
                        }
                    });
                }else if(tabId == R.id.tab_allocated){

                    Status="2";
//                    mRecyclerView.invalidate();
                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            //api call
                            SetPush(Status);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            // Refresh items and get data from server
                            SetPush(Status);
                        }
                    });

                }else if(tabId == R.id.tab_all){

                    Status="3";
//                    mRecyclerView.invalidate();
                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            //api call
                            SetPush(Status);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            // Refresh items and get data from server
                            SetPush(Status);
                        }
                    });
                }
            }
        });



    }
    private void SetPush(final String Status){

//        loader = new ProgressDialog(this);
//        loader.setCancelable(false);
//        loader.setMessage(getResources().getString(R.string.wait_msg));
//        loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("flag", "dashboard");
        json.addProperty("status", Status);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");
        json.addProperty("hsid", Global.loginusr.getHsid());
        Ion.with(this)
                .load(Global.urls.getOrderDash.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_push_order>>() {
                            }.getType();
                            List<model_push_order> events = (List<model_push_order>) gson.fromJson(result.get("data").getAsJsonArray().get(0), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


//    private void notifyDataChanges() {
//        if (mTimeLineAdapter != null) {
//            /*Collections.sort(_crewlst, new Comparator<model_crewdata>() {
//                public int compare(model_crewdata o1, model_crewdata o2) {
//                    return o1.stsi.compareToIgnoreCase(o2.stsi);
//                }
//            });*/
//            //_picup_listAdapter.notifyDataSetChanged();
//            mTimeLineAdapter.getFilter().filter(CurrentFilter);
//        }
//    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void bindCurrentTrips(List<model_push_order> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new PushOrderAdapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

            mRecyclerView.setLayoutManager(getLinearLayoutManager());
            mRecyclerView.setHasFixedSize(true);


        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimeLineAdapter!=null){
            mTimeLineAdapter.Kill();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mTimeLineAdapter!=null){
            mTimeLineAdapter.Kill();
        }
    }
}
