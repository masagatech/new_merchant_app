package com.goyo.traveltracker.forms;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.roomorama.caldroid.CaldroidFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Holyday extends AppCompatActivity {
  private ArrayList<String> Dates;
    private List<Date> NewDate =new ArrayList<Date>();
    CaldroidFragment caldroidFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holyday);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Holy Days");


        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.call, caldroidFragment);
        t.commit();

        GetHolyDays();

    }


    private void GetHolyDays(){
        JsonObject json = new JsonObject();

        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("flag", "byemp");
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        Ion.with(this)
                .load(Global.urls.getHoliday.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            Dates =new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                Dates.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("frmdt").getAsString());
                            }
                            SetHolyday(Dates);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }

    private void SetHolyday(ArrayList<String> Dates){
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.orange_light));
        for(int j = 0; j <= Dates.size() - 1; j++){
            try {
                DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                Date date= df.parse(Dates.get(j));
                NewDate.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        for(int j = 0; j <= Dates.size() - 1; j++){
            caldroidFragment.setBackgroundDrawableForDate(red, NewDate.get(j));
        }
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

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
