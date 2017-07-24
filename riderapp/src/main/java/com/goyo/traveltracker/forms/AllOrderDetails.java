package com.goyo.traveltracker.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.adapters.OrderDetailsAdapter;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_order_details;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.goyo.traveltracker.gloabls.Global.urls.getTripReports;

public class AllOrderDetails extends AppCompatActivity {
    ListView AllDetails;
    private ProgressDialog loader;
    private ArrayList<model_order_details> data;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order_details);

        if(getSupportActionBar()!=null)
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AllDetails=(ListView)findViewById(R.id.all_details);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_details, AllDetails,
                false);
        AllDetails.addHeaderView(header, null, false);

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

//        data= populateList();
//
//
//        OrderDetailsAdapter orderDetailsAdapter=new OrderDetailsAdapter(data,this);
//        AllDetails.setAdapter(orderDetailsAdapter);
//        orderDetailsAdapter.notifyDataSetChanged();


        JsonObject json = new JsonObject();
        json.addProperty("uid", Global.loginusr.getDriverid());
        Ion.with(this)
                .load(getTripReports.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_order_details>>() {
                            }.getType();
                            ArrayList<model_order_details> events = (ArrayList<model_order_details>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);

                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
//                        loader.hide();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    public static ArrayList<model_order_details> populateList(){
        ArrayList<model_order_details> mRiderList = new ArrayList<model_order_details>();
        mRiderList.add(new model_order_details("12-5-2017","03","09","10.00","8.00","06"));
        mRiderList.add(new model_order_details("12-5-2017","10","20","06.00","10.00","09"));
        mRiderList.add(new model_order_details("12-5-2017","05","09","08.00","8.00","06"));
        mRiderList.add(new model_order_details("12-5-2017","03","09","10.00","8.00","06"));
        mRiderList.add(new model_order_details("12-5-2017","03","09","10.00","8.00","07"));
        mRiderList.add(new model_order_details("12-5-2017","03","09","10.00","8.00","06"));
        mRiderList.add(new model_order_details("12-5-2017","03","09","10.00","8.00","08"));

        return mRiderList;
    }



    private void bindCurrentTrips(ArrayList<model_order_details> lst) {
        if (lst.size() > 0) {
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            AllDetails.setVisibility(View.VISIBLE);
            OrderDetailsAdapter orderDetailsAdapter=new OrderDetailsAdapter(lst,this);
            AllDetails.setAdapter(orderDetailsAdapter);
            orderDetailsAdapter.notifyDataSetChanged();
        } else {
            AllDetails.setVisibility(View.INVISIBLE);
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
}
