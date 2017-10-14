package com.goyo.parent.forms;

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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.RejectedOrderAdapter;
import com.goyo.parent.model.model_expense;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.goyo.parent.forms.dashboard.SclId;
import static com.goyo.parent.gloabls.Global.urls.getHoliday;

public class rejected_order extends AppCompatActivity {
    private com.goyo.parent.adapters.RejectedOrderAdapter mTimeLineAdapter;
    private RecyclerView mRecyclerView;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private ArrayList<model_expense> data;
    private SwipeRefreshLayout mSwipeRefreshLayout;
   private String SelectedDate;
    private  ArrayList<model_expense> datas;
    TextView Exp,Stops,Tasks,Totals;
    private CheckBox Check_Task,Check_stops;
    private ImageButton Back,Add_Expense;
    String Status="Both_Selected";
    Boolean StopChecked=true,TaskChecked=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_order);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setCustomView(R.layout.exp_actionbar);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;

        setTitle("Holidays");


        //Action Bar


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
        json.addProperty("flag", "byentt");
        json.addProperty("enttid", SclId+"");
        Ion.with(this)
                .load(getHoliday.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_expense>>() {
                            }.getType();
                            List<model_expense> events = (List<model_expense>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    private void bindCurrentTrips(List<model_expense> data){

        if (data.size() > 0) {
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTimeLineAdapter = new RejectedOrderAdapter(data, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calander, menu);
        return true;
    }


    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.calander:
                Intent intent = new Intent(this, Holyday.class);
                startActivity(intent);
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}