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
import com.goyo.marchant.adapters.NewOrderAdapter;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.gloabls.Global;
import com.goyo.marchant.model.model_notification;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.goyo.marchant.R.id.txtNodata;
import static com.goyo.marchant.forms.dashboard.SclId;

public class newOrder extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private com.goyo.marchant.adapters.NewOrderAdapter mTimeLineAdapter;
    List<model_notification> lst;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog loader;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getting if user starts activity from app or push notification and show actionbar accordigly


        setContentView(R.layout.activity_new_order);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if(getSupportActionBar()!=null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setLogo(R.drawable.rider_del);
//            getSupportActionBar().setDisplayUseLogoEnabled(true);
            }

            setTitle(getResources().getString(R.string.New_Order));



        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;


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
        json.addProperty("enttid", SclId+"");
        json.addProperty("sendtype", "parents");
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        Ion.with(this)
                .load(Global.urls.getNotification.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<model_notification>>() {
                            }.getType();
                            List<model_notification> events = (List<model_notification>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    private void bindCurrentTrips(List<model_notification> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            findViewById(txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new NewOrderAdapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();

            mRecyclerView.setLayoutManager(getLinearLayoutManager());
            mRecyclerView.setHasFixedSize(true);

        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            findViewById(txtNodata).setVisibility(View.VISIBLE);
        }
    }



    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
    public void onResume(){
        super.onResume();

//        loader = new ProgressDialog(this);
//        loader.setCancelable(false);
//        loader.setMessage(getResources().getString(R.string.wait_msg));
//        loader.show();
//        if(lst == null){
//            lst = new ArrayList<>();
//            setDataListItems();
//            initView();
//            loader.hide();
//        }else {
//            setDataListItems();
//            if(mTimeLineAdapter!=null)
//            {
//                mTimeLineAdapter.notifyDataSetChanged();
//            }
//            loader.hide();
//        }
    }

    @Override
    public void onStop(){
        super.onStop();
//        if(mTimeLineAdapter!=null){
//            mTimeLineAdapter.Kill();
//        }
        //db.close();
    }
}
