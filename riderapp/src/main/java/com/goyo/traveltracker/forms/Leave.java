package com.goyo.traveltracker.forms;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.model.modal_leave;
import com.roomorama.caldroid.CaldroidFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Leave extends AppCompatActivity {

    private  List<Date> Dates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);



        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.Rejected_Order));


        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.cal, caldroidFragment);
        t.commit();



        SQLBase db = new SQLBase(this);

        ArrayList<modal_leave> data = new ArrayList<modal_leave>();
            List<HashMap<String, String>> d = db.Get_Leave();
            if (d.size() > 0) {
                for (int i = 0; i <= d.size() - 1; i++) {
                    data.add(new modal_leave(d.get(i).get(Tables.tblleave.Leave_From), d.get(i).get(Tables.tblleave.Leave_To), d.get(i).get(Tables.tblleave.Leave_Type), d.get(i).get(Tables.tblleave.Leave_Details), d.get(i).get(Tables.tblleave.Leave_Created_By), d.get(i).get(Tables.tblleave.Leave_Server), d.get(i).get(Tables.tblleave.Leave_Status)));

                    Dates=getDates(d.get(i).get(Tables.tblleave.Leave_From),d.get(i).get(Tables.tblleave.Leave_To));
                    ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue_light));

                    for(int j = 0; j <= Dates.size() - 1; j++){
                        caldroidFragment.setBackgroundDrawableForDate(blue, Dates.get(j));
                    }
                }
                }
            }

    private static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_info_view_activity, menu);
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

            case R.id.menu_driver_info_view_add:
                Intent intent=new Intent(this,AddLeave.class);
                startActivity(intent);
                return true;

            case R.id.Sync:
//                mSwipeRefreshLayout.setRefreshing(true);
//                DatafromServer();
//                DataFromServer();
//                mSwipeRefreshLayout.setRefreshing(false);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
