package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.goyo.parent.forms.dashboard.SclId;

public class Schedule extends Fragment {
    private WeekView mWeekView;
    private ProgressDialog loader;
    private  List<Date> Dates;
    private View view;
    String ID="";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schedule);
//
//        if(getSupportActionBar()!=null)
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//        setTitle("Schedule");

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view= inflater.inflate(R.layout.activity_schedule, container, false);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                ID = bundle.getString("ID");
            }

        mWeekView = (WeekView)view.findViewById(R.id.weekView);


        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

                List<WeekViewEvent> events= GetSchedule(newMonth,newYear,ID);
                // Populate the week view with some events.
//            List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 3);
//            startTime.set(Calendar.MINUTE, 0);
//            startTime.set(Calendar.DATE,26);
//            startTime.set(Calendar.MONTH, newMonth - 1);
//            startTime.set(Calendar.YEAR, newYear);
//            Calendar endTime = (Calendar) startTime.clone();
//            endTime.add(Calendar.HOUR, 1);
//            endTime.set(Calendar.MONTH, newMonth - 1);
//            WeekViewEvent event = new WeekViewEvent(1, "Event Blue", startTime, endTime);
//            event.setColor(getResources().getColor(R.color.blue_light));
//            events.add(event);
//
//            startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 3);
//            startTime.set(Calendar.MINUTE, 30);
//            startTime.set(Calendar.MONTH, newMonth-1);
//            startTime.set(Calendar.YEAR, newYear);
//            endTime = (Calendar) startTime.clone();
//            endTime.set(Calendar.HOUR_OF_DAY, 4);
//            endTime.set(Calendar.MINUTE, 30);
//            endTime.set(Calendar.MONTH, newMonth-1);
//            event = new WeekViewEvent(10, "Event Orenge", startTime, endTime);
//            event.setColor(getResources().getColor(R.color.orange_light));
//            events.add(event);
//
//            startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 4);
//            startTime.set(Calendar.MINUTE, 20);
//            startTime.set(Calendar.MONTH, newMonth-1);
//            startTime.set(Calendar.YEAR, newYear);
//            endTime = (Calendar) startTime.clone();
//            endTime.set(Calendar.HOUR_OF_DAY, 5);
//            endTime.set(Calendar.MINUTE, 0);
//            event = new WeekViewEvent(10, "Event Red", startTime, endTime);
//            event.setColor(getResources().getColor(R.color.red_light));
//            events.add(event);

//
//            //AllDay event
//            startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 0);
//            startTime.set(Calendar.MINUTE, 0);
//            startTime.set(Calendar.MONTH, newMonth-1);
//            startTime.set(Calendar.YEAR, newYear);
//            endTime = (Calendar) startTime.clone();
//            endTime.add(Calendar.HOUR_OF_DAY, 23);
//            event = new WeekViewEvent(7, "a",null, startTime, endTime, true);
//            event.setColor(getResources().getColor(R.color.blue_light));
//            events.add(event);
//            events.add(event);
//
//
//            // All day event until 00:00 next day
//            startTime = Calendar.getInstance();
//            startTime.set(Calendar.DAY_OF_MONTH, 10);
//            startTime.set(Calendar.HOUR_OF_DAY, 0);
//            startTime.set(Calendar.MINUTE, 0);
//            startTime.set(Calendar.SECOND, 0);
//            startTime.set(Calendar.MILLISECOND, 0);
//            startTime.set(Calendar.MONTH, newMonth-1);
//            startTime.set(Calendar.YEAR, newYear);
//            endTime = (Calendar) startTime.clone();
//            endTime.set(Calendar.DAY_OF_MONTH, 11);
//            event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, true);
//            event.setColor(getResources().getColor(R.color.blue_light));
//            events.add(event);

            return events;
        }

    };

        mWeekView.setMonthChangeListener(mMonthChangeListener);
            return view;
    }

    private List<WeekViewEvent> GetSchedule(final int Month, final int Year,String Id){

        final List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar c = Calendar.getInstance();
        c.set(Year, Month-1, 1, 0, 0);
        Date Date = c.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String FromDate = df.format(Date);

        Calendar c2 = Calendar.getInstance();
        c2.set(Year, Month-1, 1, 0, 0);
        c2.add(Calendar.DATE,c2.getActualMaximum(Calendar.DATE));
        Date Date2 = c2.getTime();
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
        String ToDate = df2.format(Date2);

//        loader = new ProgressDialog(getActivity());
//        loader.setCancelable(false);
//        loader.setMessage(this.getString(R.string.wait_msg));
//        loader.show();

        JsonObject json = new JsonObject();
        json.addProperty("flag", "weekly");
        json.addProperty("uid", Preferences.getValue_String(getActivity(), Preferences.USER_ID));
        json.addProperty("enttid", SclId+"");
        json.addProperty("studid", "1");
        json.addProperty("classid", "0");
        json.addProperty("tchrid", "0");
        json.addProperty("frmdt", FromDate);
        json.addProperty("todt", ToDate);
        json.addProperty("ctype", "parent");
        Ion.with(this)
                .load(Global.urls.getClassSchedule.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
//                            Absent= new ArrayList<String>();
//                            Present= new ArrayList<String>();
//                            Holiday= new ArrayList<String>();
                            for(int i=0;i<result.get("data").getAsJsonArray().size();i++){
                                String FromDate =result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("frmdt")).getAsString();
                                String ToDate =result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("todt")).getAsString();
                                String FromTime =result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("frmtm")).getAsString();
                                String ToTime =result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("totm")).getAsString();
                                String Subject =result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("mon")).getAsString();

                                Dates = getDates(FromDate, ToDate);

                                //converting time to int
                                Calendar cal = Calendar.getInstance();
                                Calendar cal2 = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                                Date date = sdf.parse(FromTime);
                                cal.setTime(date);

                                Date date2 = sdf.parse(ToTime);
                                cal2.setTime(date2);

                                int Startmins = cal.get(Calendar.MINUTE);
                                int StartHour= cal.get(Calendar.HOUR);

                                int Endmins = cal2.get(Calendar.MINUTE);
                                int EndHour= cal2.get(Calendar.HOUR);

                                for (int j = 0; j <= Dates.size() - 1; j++) {
                                    Calendar StartTime = Calendar.getInstance();
                                    StartTime.setTime(Dates.get(j));
                                    StartTime.set(Calendar.HOUR_OF_DAY, StartHour);
                                    StartTime.set(Calendar.MINUTE, Startmins);

                                    Calendar EndTime = (Calendar) StartTime.clone();
                                    StartTime.set(Calendar.HOUR_OF_DAY, EndHour);
                                    StartTime.set(Calendar.MINUTE, Endmins);


                                    WeekViewEvent event = new WeekViewEvent(1, Subject, StartTime, EndTime);
                                    event.setColor(getResources().getColor(R.color.blue_light));
                                    events.add(event);

                                }







//                                startTime = Calendar.getInstance();
//                                startTime.set(Calendar.HOUR_OF_DAY, 3);
//                                startTime.set(Calendar.MINUTE, 30);
//                                startTime.set(Calendar.MONTH, newMonth-1);
//                                startTime.set(Calendar.YEAR, newYear);
//                                endTime = (Calendar) startTime.clone();
//                                endTime.set(Calendar.HOUR_OF_DAY, 4);
//                                endTime.set(Calendar.MINUTE, 30);
//                                endTime.set(Calendar.MONTH, newMonth-1);
//                                event = new WeekViewEvent(10, "Event Orenge", startTime, endTime);
//                                event.setColor(getResources().getColor(R.color.orange_light));
//                                events.add(event);


                            }

//                                    for (int j = 1; j < 40; j++) {
//                                        if (result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf(j)).getAsString() != null) {
//                                            String Data = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf(j)).getAsString();
//
//                                            //present data
//                                            if (Data.equals("P") || Data.equals("PA") || Data.equals("BP") || Data.equals("PD") || Data.equals("NP")) {
//
//                                                Calendar c = Calendar.getInstance();
//                                                c.set(Year, Month - 1, j, 0, 0);
//                                                Date Date = c.getTime();
//                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                                                String Dates = df.format(Date);
//                                                Present.add(Dates);
//                                            }
//
//                                            //absent data
//                                            if (Data.equals("L") || Data.equals("A") || Data.equals("DA") || Data.equals("AS")) {
//
//                                                Calendar c = Calendar.getInstance();
//                                                c.set(Year, Month - 1, j, 0, 0);
//                                                Date Date = c.getTime();
//                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                                                String Dates = df.format(Date);
//                                                Absent.add(Dates);
//                                            }
//
//                                            //holiday data
//                                            if (Data.equals("H") || Data.equals("HD")) {
//                                                Calendar c = Calendar.getInstance();
//                                                c.set(Year, Month - 1, j, 0, 0);
//                                                Date Date = c.getTime();
//                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//                                                String Dates = df.format(Date);
//                                                Holiday.add(Dates);
//                                            }
//                                        }
//                                    }

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
//                        SetDates(Present,Absent,Holiday);
//                        loader.hide();


                    }
                });

        return events;

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

}
