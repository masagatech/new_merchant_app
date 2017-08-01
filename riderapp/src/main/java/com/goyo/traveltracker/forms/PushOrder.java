package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
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
import com.goyo.traveltracker.adapters.PushOrderAdapter;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_completed;
import com.goyo.traveltracker.model.model_expense;
import com.goyo.traveltracker.model.model_push_order;
import com.goyo.traveltracker.model.model_tag;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

import static com.goyo.traveltracker.R.id.recyclerView;
import static com.goyo.traveltracker.gloabls.Global.urls.getPushTagDetails;


public class PushOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.traveltracker.adapters.PushOrderAdapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private  String Status = "0";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<model_expense> data;
//    @BindView(R.id.bottomBars)
//    BottomBar bottomBar;


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

//        bottomBar.setDefaultTab(R.id.tab_new);
        mRecyclerView = (RecyclerView) findViewById(recyclerView);

        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

//        SetPush(Status);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(getLinearLayoutManager());
//        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);


        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                bindCurrentTrips();
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server
                bindCurrentTrips();
            }
        });
        
        //tab list
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_new) {
//
//                    Status="0";
//
//                    //refresh data at first time
//                    mSwipeRefreshLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mSwipeRefreshLayout.setRefreshing(true);
//                            //api call
//                            SetPush(Status);
//                        }
//                    });
//
//                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            // Refresh items and get data from server
//                            SetPush(Status);
//                        }
//                    });
////                    mRecyclerView.setLayoutManager(getLinearLayoutManager());
////                    mRecyclerView.setHasFixedSize(true);
//
//                }else if(tabId == R.id.tab_waiting)
//                {
//                    Status="1";
////                    mRecyclerView.invalidate();
//                    //refresh data at first time
//                    mSwipeRefreshLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mSwipeRefreshLayout.setRefreshing(true);
//                            //api call
//                            SetPush(Status);
//                        }
//                    });
//
//                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            // Refresh items and get data from server
//                            SetPush(Status);
//                        }
//                    });
//                }else if(tabId == R.id.tab_allocated){
//
//                    Status="2";
////                    mRecyclerView.invalidate();
//                    //refresh data at first time
//                    mSwipeRefreshLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mSwipeRefreshLayout.setRefreshing(true);
//                            //api call
//                            SetPush(Status);
//                        }
//                    });
//
//                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            // Refresh items and get data from server
//                            SetPush(Status);
//                        }
//                    });
//
//                }else if(tabId == R.id.tab_all){
//
//                    Status="3";
////                    mRecyclerView.invalidate();
//                    //refresh data at first time
//                    mSwipeRefreshLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mSwipeRefreshLayout.setRefreshing(true);
//                            //api call
//                            SetPush(Status);
//                        }
//                    });
//
//                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            // Refresh items and get data from server
//                            SetPush(Status);
//                        }
//                    });
//                }
//            }
//        });



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
//                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


    private void DatafromServer(){

        JsonObject json = new JsonObject();
        json.addProperty("enttid", Global.loginusr.getEnttid());
        json.addProperty("empid", Global.loginusr.getDriverid());
        json.addProperty("flag", "byemp");
        Ion.with(this)
                .load(getPushTagDetails.value)
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
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });
    }


    private void SavetoDb(List<model_completed> lst) {
        String Empl_Id= String.valueOf(Global.loginusr.getDriverid());
        SQLBase db = new SQLBase(this);
        if (lst.size() > 0) {
            for (int i = 0; i <= lst.size() - 1; i++) {
                //checking if tag alredy exist
                if (!db.ISTAG_ALREDY_EXIST(lst.get(i).tagnm)) {
                    db.TAG_ADDTAG(new model_tag(lst.get(i).tagnm, lst.get(i).remark2, lst.get(i).remark3, lst.get(i).remark33, Empl_Id, lst.get(i).createdon, "2"));
                }
            }
        }

    }



    private ArrayList<model_expense> populateList(){
        SQLBase db = new SQLBase(this);
        ArrayList<model_expense> data = new ArrayList<model_expense>();
        List<HashMap<String,String>> d = db.Get_Expenses_Display();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_expense(d.get(i).get(Tables.tblexpense.Exp_ID), d.get(i).get(Tables.tblexpense.Expense_Name), d.get(i).get(Tables.tblexpense.Expense_Disc),d.get(i).get(Tables.tblexpense.Expense_Value),d.get(i).get(Tables.tblexpense.Expense_Code),d.get(i).get(Tables.tblexpense.Expense_Is_Active),d.get(i).get(Tables.tblexpense.Expense_Server)));
            }
        }

        return data;
    }

    private void GetExpenseData(){
        JsonObject json = new JsonObject();
        json.addProperty("flag", "byemp");
        json.addProperty("empid", Global.loginusr.getDriverid()+"");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getExpenseDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) Log.v("result", result.toString());

                        try {

                            //getting data and storing in db
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_expense>>() {
                            }.getType();
                            List<model_expense> events = (List<model_expense>) gson.fromJson(result.get("data"), listType);
                            SavetoDbs(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });

    }

    private void SavetoDbs(List<model_expense> lst) {
        SQLBase db = new SQLBase(this);
        if (lst.size() > 0) {
            for (int i = 0; i <= lst.size() - 1; i++) {
                //checking if expense alredy exist
                if (!db.ISEXPENSE_ALREDY_EXIST(lst.get(i).expnm)) {
                    db.ADDEXPENSE(new model_expense(lst.get(i).expid,lst.get(i).expnm, lst.get(i).expdesc, "", lst.get(i).expcd,String.valueOf(lst.get(i).isactive),"0"));
                }
            }
        }

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

    private void bindCurrentTrips() {

        data= populateList();

        if (data.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new PushOrderAdapter(data, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todaystrips_activity, menu);
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

            case R.id.menu_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                GetExpenseData();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

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
