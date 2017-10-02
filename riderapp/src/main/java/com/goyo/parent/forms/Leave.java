package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.goyo.parent.forms.dashboard.SclId;

public class Leave extends Fragment {

    private  List<Date> Dates;
    private ProgressDialog loader;
    CaldroidFragment caldroidFragment;
    List<String> Absent;
    List<String> Present;
    List<String> Holiday;
    private View view;
    String ID="";



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leave);

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view= inflater.inflate(R.layout.activity_leave, container, false);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                ID = bundle.getString("ID");
            }


//        if(getSupportActionBar()!=null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setTitle("Attendance");


       caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

            FragmentTransaction t =getChildFragmentManager() .beginTransaction();
        t.replace(R.id.cal, caldroidFragment);
        t.commit();


        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
//                Toast.makeText(getApplicationContext(), formatter.format(date),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                GetDates(month,year);
            }
        };

        caldroidFragment.setCaldroidListener(listener);
            return view;

    }

    private void SetDates( List<String> Present, List<String> Absent, List<String> Holiday){
        ColorDrawable orenge = new ColorDrawable(getResources().getColor(R.color.orange_light));
        ColorDrawable red = new ColorDrawable(getResources().getColor(R.color.red_light));
        ColorDrawable green = new ColorDrawable(getResources().getColor(R.color.green_light));

        if(Present.size()>0) {
            for (int i = 0; i < Present.size() - 1; i++) {
                DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
                Date Presents = null;

                try {
                    Presents = df1.parse(Present.get(i));
                    caldroidFragment.setBackgroundDrawableForDate(green, Presents);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if(Absent.size()>0) {
            for (int i = 0; i < Absent.size() - 1; i++) {
                DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
                Date Absents = null;

                try {
                    Absents = df1.parse(Absent.get(i));
                    caldroidFragment.setBackgroundDrawableForDate(red, Absents);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if(Holiday.size()>0) {
            for (int i = 0; i < Holiday.size() - 1; i++) {
                DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
                Date Holidayts = null;

                try {
                    Holidayts = df1.parse(Holiday.get(i));
                    caldroidFragment.setBackgroundDrawableForDate(orenge, Holidayts);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        caldroidFragment.refreshView();

    }
    private void GetDates(final int Month,final int Year){

        String MonthName=String.valueOf(Month)+"-"+String.valueOf(Year);
        loader = new ProgressDialog(getActivity());
        loader.setCancelable(false);
        loader.setMessage(this.getString(R.string.wait_msg));
        loader.show();

       JsonObject json = new JsonObject();
        json.addProperty("flag", "student");
        json.addProperty("uid", Preferences.getValue_String(getActivity(), Preferences.USER_ID));
        json.addProperty("schoolid", SclId+"");
        json.addProperty("monthname", MonthName);
        Ion.with(this)
                .load(Global.urls.getAttendanceReports.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            Absent= new ArrayList<String>();
                            Present= new ArrayList<String>();
                            Holiday= new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                if(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("stdid").getAsString().equals(ID)) {
                                    for (int j = 1; j < 40; j++) {
                                        if (result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf(j)).getAsString() != null) {
                                            String Data = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf(j)).getAsString();

                                            //present data
                                            if (Data.equals("P") || Data.equals("PA") || Data.equals("BP") || Data.equals("PD") || Data.equals("NP")) {

                                                Calendar c = Calendar.getInstance();
                                                c.set(Year, Month - 1, j, 0, 0);
                                                Date Date = c.getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                String Dates = df.format(Date);
                                                Present.add(Dates);
                                            }

                                            //absent data
                                            if (Data.equals("L") || Data.equals("A") || Data.equals("DA") || Data.equals("AS")) {

                                                Calendar c = Calendar.getInstance();
                                                c.set(Year, Month - 1, j, 0, 0);
                                                Date Date = c.getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                String Dates = df.format(Date);
                                                Absent.add(Dates);
                                            }

                                            //holiday data
                                            if (Data.equals("H") || Data.equals("HD")) {
                                                Calendar c = Calendar.getInstance();
                                                c.set(Year, Month - 1, j, 0, 0);
                                                Date Date = c.getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                String Dates = df.format(Date);
                                                Holiday.add(Dates);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        SetDates(Present,Absent,Holiday);
                        loader.hide();


                    }
                });

    }


//    private void SavetoDb(List<modal_leave> lst) {
//        SQLBase db = new SQLBase(this);
//        if (lst.size() > 0) {
//            for (int i = 0; i <= lst.size() - 1; i++) {
//
//                if (db.ISLeave_ALREDY_EXIST((lst.get(i).mob_createdon))) {
//                    db.Leave_UPDATE(lst.get(i).statusdesc,lst.get(i).mob_createdon);
//                }
//            }
//        }

//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_driver_info_view_activity, menu);
//        return true;
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
////            case R.id.menu_driver_info_view_add:
////                Intent intent=new Intent(this,AddLeave.class);
////                startActivity(intent);
////                return true;
////
////            case R.id.Sync:
////                GetStatus();
////                return true;
//            default:
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
