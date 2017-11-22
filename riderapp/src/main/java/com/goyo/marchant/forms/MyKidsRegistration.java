package com.goyo.marchant.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.marchant.R;
import com.goyo.marchant.adapters.MyKidsRegListAdapter;
import com.goyo.marchant.common.Preferences;
import com.goyo.marchant.gloabls.Global;
import com.goyo.marchant.model.MyKidsModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

public class MyKidsRegistration extends AppCompatActivity {

    ListView lstmykids;
    private List<MyKidsModel> lstmykidsd;
    private MyKidsModel mykid;
    private ProgressDialog loader;
    MenuItem menu_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kids_registration);


        setTitle("Add Student");
        initUI();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu_refresh = menu.findItem(R.id.menu_refresh);
        return true;
    }

    //set action bar button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todaystrips_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_refresh:
                bindListView();
            default:
        }
        return super.onOptionsItemSelected(item);
    }



    private void initUI() {
//        customDialog = new CustomDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstmykids = (ListView) findViewById(R.id.lstmykids);
//        addListners();
        bindListView();

    }

//    private void addListners() {
//        lstmykids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mykid = ((MyKidsModel) parent.getItemAtPosition(position));
//                Intent in = new Intent(MyKidsRegistration.this, clnt_mykidstrips.class);
////                in.putExtra("tripid",mykid.tripid);
//                in.putExtra("stdid", mykid.StudId);
//                startActivity(in);
//            }
//        });
//
//
//    }

    private void bindListView() {

        loader = new ProgressDialog(this);
        loader.setCancelable(false);
        loader.setMessage(this.getString(R.string.wait_msg));
        loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("flag", "mykids");
//        customDialog.show();
        Ion.with(this)
                .load(Global.urls.getmykids.value)
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
                            Type listType = new TypeToken<List<MyKidsModel>>() {
                            }.getType();
                            lstmykidsd = (List<MyKidsModel>) gson.fromJson(result.get("data"), listType);
                            bindCreawData(lstmykidsd);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        loader.hide();
//                        customDialog.hide();
                    }
                });
    }





    MyKidsRegListAdapter _clnt_mykidsReg_listAdapter;
    List<MyKidsModel> _mykidlst;

    private void bindCreawData(List<MyKidsModel> lst) {
        if (lst.size() > 0) {
            _mykidlst = lst;
            findViewById(R.id.txtNodata).setVisibility(View.GONE);
            _clnt_mykidsReg_listAdapter = new MyKidsRegListAdapter(this, lst);
            lstmykids.setVisibility(View.VISIBLE);
            lstmykids.setAdapter(_clnt_mykidsReg_listAdapter);

            notifyCrewChanges();

        } else {
            lstmykids.setVisibility(View.GONE);
            findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }

    private void notifyCrewChanges() {
        if (_clnt_mykidsReg_listAdapter != null) {
            /*Collections.sort(_crewlst, new Comparator<model_crewdata>() {
                public int compare(model_crewdata o1, model_crewdata o2) {
                    return o1.stsi.compareToIgnoreCase(o2.stsi);
                }
            });*/
            _clnt_mykidsReg_listAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }



}
