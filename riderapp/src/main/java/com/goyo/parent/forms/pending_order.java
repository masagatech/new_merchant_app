package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.pending_order_adapter;
import com.goyo.parent.database.SQLBase;
import com.goyo.parent.database.Tables;
import com.goyo.parent.gloabls.Global;
import com.goyo.parent.model.model_pending;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.goyo.parent.R.id.txtNodata;
import static com.goyo.parent.Service.RiderStatus.Rider_Lat;
import static com.goyo.parent.Service.RiderStatus.Rider_Long;
import static com.goyo.parent.forms.dashboard.TripId;
import static com.goyo.parent.gloabls.Global.urls.getTaskAllocate;
import static com.goyo.parent.gloabls.Global.urls.setTripAction;


public class pending_order extends AppCompatActivity {

    @BindView(R.id.bottomBars)
    BottomBar bottomBar;


    private RecyclerView mRecyclerView;
    private ImageButton StartRide,BackButton;
    private com.goyo.parent.adapters.pending_order_adapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static int Status_Task=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);
        ButterKnife.bind(this);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setCustomView(R.layout.pending_order_item);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);




//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;


        setTitle(getResources().getString(R.string.Pending_Order));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomBar.setDefaultTab(R.id.tab_waiting);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(getLinearLayoutManager());
//        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.Refresh);



        //tab list
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_waiting) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                   final String formattedDate = df.format(c.getTime());

                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                            Status_Task=0;
                            //api call
                            DataFromServer(Status_Task,formattedDate);
//                            SetPush(Status);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            Status_Task=0;
                            // Refresh items and get data from server
                            DataFromServer(Status_Task,formattedDate);
                        }
                    });
//                    mRecyclerView.setLayoutManager(getLinearLayoutManager());
//                    mRecyclerView.setHasFixedSize(true);

                }else if(tabId == R.id.tab_allocated)
                {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    final String formattedDate = df.format(c.getTime());

//                    mRecyclerView.invalidate();
                    //refresh data at first time
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {

                            Status_Task=1;
                            //api call
                            DataFromServer(Status_Task,formattedDate);
                        }
                    });

                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            findViewById(txtNodata).setVisibility(View.VISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);

                            Status_Task=1;
                            // Refresh items and get data from server
                            DataFromServer(Status_Task,formattedDate);
                        }
                    });
                }

            }
        });

        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c.getTime());
                mSwipeRefreshLayout.setRefreshing(true);
                Status_Task=0;
                //api call
                DataFromServer(Status_Task,formattedDate);
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c.getTime());
                Status_Task=0;
                // Refresh items and get data from server
                DataFromServer(Status_Task,formattedDate);
            }
        });

//        StartRide = (ImageButton) findViewById(R.id.startRide);
//        BackButton = (ImageButton) findViewById(R.id.back);

//        BackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//
//            }
//        });


//        StartRide.setImageResource(R.drawable.start_trip);
//        StartRide.setBackgroundColor(Color.parseColor("#ff99cc00"));
//        StartRide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TripId.equals("0")) {
//                    new AlertDialog.Builder(pending_order.this)
//                            .setTitle(R.string.starttrip_head)
//                            .setMessage(R.string.starttrip_body)
//                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startTrip();
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.rider).show();
//
//                } else {
//                    new AlertDialog.Builder(pending_order.this)
//                            .setTitle(R.string.stoptrip_head)
//                            .setMessage(R.string.stoptrip_body)
//                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//
//                                    stopTrip();
//
//
//                                }
//                            })
//                            .setNegativeButton(R.string.alert_no_text, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(R.drawable.stop_trip).show();
//                }
//            }
//        });



    }

    private void DataFromServer(int Status,String Date) {
//
//        loader = new ProgressDialog(this);
//        loader.setCancelable(false);
//        loader.setMessage(getResources().getString(R.string.wait_msg));
//        loader.show();
//
//        Ion.with(this)
//                .load("GET", getAllocateTask.value)
//                .addQuery("flag", "byemp")
//                .addQuery("empid","1")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//
//                            if (result != null) Log.v("result", result.toString());
//                            Gson gson = new Gson();
//                            Type listType = new TypeToken<List<model_pending>>() {
//                            }.getType();
//                            List<model_pending> events = (List<model_pending>) gson.fromJson(result.get("data"), listType);
//                            bindCurrentTrips(events);
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
////                        loader.hide();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });

        JsonObject json = new JsonObject();
        json.addProperty("flag", "byemp");
        json.addProperty("empid",Global.loginusr.getDriverid());
        json.addProperty("tskstatus",Status);
        json.addProperty("enttid",Global.loginusr.getEnttid());
        json.addProperty("fdate",Date);
        json.addProperty("ddate",Date);

        Ion.with(this)
                .load(getTaskAllocate.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_pending>>() {
                            }.getType();
                            List<model_pending> events = (List<model_pending>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

//    private void startTrip(){
//
//        JsonObject json = new JsonObject();
//        json.addProperty("flag", "start");
//        json.addProperty("loc", Rider_Lat+","+Rider_Long);
//        json.addProperty("tripid", TripId);
//        json.addProperty("rdid", Global.loginusr.getDriverid() + "");
//
//        Ion.with(this)
//                .load(setTripAction.value)
//                .setJsonObjectBody(json)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//
//                        try {
//                            if (result != null) Log.v("result", result.toString());
//                          JsonObject Data=  result.get("data").getAsJsonObject();
//                            if(Data.get("status").getAsBoolean()){
//                                TripId=Data.get("tripid").toString();
//                                StartRide.setBackgroundColor(Color.RED);
//                                Toast.makeText(getApplicationContext(),"Your Ride Has started"
//                                        ,Toast.LENGTH_SHORT).show();
//                                StartRide.setBackgroundColor(Color.parseColor("#ffff4444"));
//                                StartRide.setImageResource(R.drawable.end_trip);
//                                mTimeLineAdapter.tripid = TripId;
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
//                                        ,Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        catch (Exception ea) {
//                            ea.printStackTrace();
//                        }
//                    }
//                });
//
//    }


    public void SendOfflineTaskstoServer() {
        SQLBase db = new SQLBase(this);
        final List<HashMap<String,String>> d = db. Get_Tasks_Offline_Pending();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                final int pos=i;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> TagsArray = gson.fromJson(d.get(i).get(Tables.tbltasks.Task_Tags), type);
                String tag= Joiner.on(",").join(TagsArray);


                JsonObject json = new JsonObject();

                json.addProperty("tntype", d.get(i).get(Tables.tbltasks.Task_Nature));
                json.addProperty("tstype", d.get(i).get(Tables.tbltasks.Task_Status));
                json.addProperty("value", d.get(i).get(Tables.tbltasks.Task_Value));
                json.addProperty("remark", d.get(i).get(Tables.tbltasks.Task_Remark));
                json.addProperty("cuid", d.get(i).get(Tables.tbltasks.Task_Creat_On)+", "+d.get(i).get(Tables.tbltasks.TIME));
                json.addProperty("tskid", d.get(i).get(Tables.tbltasks.Tks_id));
                json.addProperty("trpid", TripId);
                json.addProperty("tag","{" + tag + "}");
                json.addProperty("expid", d.get(i).get(Tables.tbltasks.EXP_ID));
                json.addProperty("expval", d.get(i).get(Tables.tbltasks.EXP_Value));
                json.addProperty("expdesc",d.get(i).get(Tables.tbltasks.EXP_Disc));

                Ion.with(this)
                        .load(Global.urls.saveTaskNature.value)
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                try {
                                    SQLBase db = new SQLBase(pending_order.this);
                                    db. TASK_UPDATE(d.get(pos).get(Tables.tbltasks.Tks_id),"1");

                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                }


                            }
                        });
            }
        }

//        return data;
    }



    public void stopTrip(){

        JsonObject json = new JsonObject();
        json.addProperty("flag", "stop");
        json.addProperty("loc", Rider_Lat+","+Rider_Long);
        json.addProperty("tripid", TripId);
        json.addProperty("rdid", Global.loginusr.getDriverid() + "");

        Ion.with(this)
                .load(setTripAction.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            if (result != null) Log.v("result", result.toString());
                            if(result.get("data").getAsJsonObject().get("status").getAsBoolean()){
                                TripId="0";
                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                                   onBackPressed();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),result.get("data").getAsJsonObject().get("msg").toString()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                    }
                });

    }

    private void bindCurrentTrips(List<model_pending> lst) {
        if (lst.size() > 0) {
//            TripId = lst.get(0).tripid;
//            if(TripId.equals("0")){
//                //greeen
//                StartRide.setImageResource(R.drawable.start_trip);
//                StartRide.setBackgroundColor(Color.parseColor("#ff99cc00"));
//            }else {
//                //red
//                StartRide.setBackgroundColor(Color.parseColor("#ffff4444"));
//                StartRide.setImageResource(R.drawable.end_trip);
//            }
//
//
//            StartRide.setVisibility(View.VISIBLE);

//            for (int i =0; i<=lst.size()-1 ;i ++){
//                if(!lst.get(i).stats.equals("0")){
//                    lst.remove(i);
//                    i-=1;
//                }
//            }
//
//            if(!TripId.equals("0") && lst.size() ==0){
//                TextView Text =(TextView)findViewById(txtNodata);
//                findViewById(txtNodata).setVisibility(View.VISIBLE);
//                mRecyclerView.setVisibility(View.INVISIBLE);
//                Text.setText(R.string.stoptrip_msg);
//            }
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new pending_order_adapter(lst, mOrientation, mWithLinePadding);
            mTimeLineAdapter.tripid = TripId;
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

            mRecyclerView.setLayoutManager(getLinearLayoutManager());
            mRecyclerView.setHasFixedSize(true);

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
//            StartRide.setVisibility(View.GONE);
            findViewById(txtNodata).setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        if (mTimeLineAdapter!=null){
            mTimeLineAdapter.notifyDataSetChanged();
        }
    }
}
