package com.goyo.parent.forms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.goyo.parent.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule extends AppCompatActivity {
    private WeekView mWeekView;
    private ProgressDialog loader;
    private List<Date> Dates;
    String ID = "";
    private String FromDate;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("Schedule");

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.activity_schedule, container, false);
//
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            ID = bundle.getString("ID");
//        }

        mWeekView = (WeekView) findViewById(R.id.weekView);



        mWeekView.setMonthChangeListener(mMonthChangeListener);
    }

    private void GetSchedule(final int Month, final int Year, String Id) {


    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(final  int Year, final int Month) {

//                List<WeekViewEvent> events= GetSchedule(newMonth,Month,ID);


            Calendar c = Calendar.getInstance();
            c.set(Year, Month - 1, 1, 0, 0);
            Date Date = c.getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            FromDate = df.format(Date);

            Calendar c2 = Calendar.getInstance();
            c2.set(Year, Month - 1, 1, 0, 0);
            c2.add(Calendar.DATE, c2.getActualMaximum(Calendar.DATE));
            Date Date2 = c2.getTime();
            SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
            String ToDate = df2.format(Date2);

//                Dates = getDates(FromDate, ToDate);

//            JsonObject json = new JsonObject();
//            json.addProperty("flag", "schedule");
//            json.addProperty("uid", Preferences.getValue_String(Schedule.this, Preferences.USER_ID));
//            json.addProperty("enttid", SclId + "");
//            json.addProperty("studid", "1");
//            json.addProperty("classid", "0");
//            json.addProperty("tchrid", "0");
//            json.addProperty("frmdt", FromDate);
//            json.addProperty("todt", ToDate);
//            json.addProperty("ctype", "parent");
//            Ion.with(Schedule.this)
//                    .load(Global.urls.getClassSchedule.value)
//                    .setJsonObjectBody(json)
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            try {
//                                for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
//                                    String Date = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("caldate")).getAsString();
//                                    String Off = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get(String.valueOf("rsttyp")).getAsString();
//                                    if (Date.equals(FromDate)) {
//                                        if (!Off.equals("off")) {
//                                            JsonObject results = result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("schedule").getAsJsonArray().get(i).getAsJsonObject();
//
//                                            String FromTime = results.get("frmtm").getAsString();
//                                            String ToTime = results.get("totm").getAsString();
//                                            String Subject = results.get("subname").getAsString();
//
//                                            //converting time to int
//                                            Calendar cal = Calendar.getInstance();
//                                            Calendar cal2 = Calendar.getInstance();
//                                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//
//                                            Date date = sdf.parse(FromTime);
//                                            cal.setTime(date);
//
//                                            Date date2 = sdf.parse(ToTime);
//                                            cal2.setTime(date2);
//
//                                            int Startmins = cal.get(Calendar.MINUTE);
//                                            int StartHour = cal.get(Calendar.HOUR);
//
//                                            int Endmins = cal2.get(Calendar.MINUTE);
//                                            int EndHour = cal2.get(Calendar.HOUR);
//
//                                            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
//                                            Date Dates = format.parse(FromDate);
//
//                                            Calendar StartTime = Calendar.getInstance();
//                                            StartTime.setTime(Dates);
//                                            StartTime.set(Calendar.HOUR_OF_DAY, StartHour);
//                                            StartTime.set(Calendar.MINUTE, Startmins);
//
//                                            Calendar EndTime = (Calendar) StartTime.clone();
//                                            StartTime.set(Calendar.HOUR_OF_DAY, EndHour);
//                                            StartTime.set(Calendar.MINUTE, Endmins);
//
//
//                                            WeekViewEvent event = new WeekViewEvent(1, Subject, StartTime, EndTime);
//                                            event.setColor(getResources().getColor(R.color.blue_light));
//                                            events.add(event);

//                                        } else {
//                                            //Weekly off
//                                        }
//                                    }
//                                }
//
//                            } catch (Exception ea) {
//                                ea.printStackTrace();
//                            }
//                        }
//                    });
            if (Month == 10){
                Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            startTime.set(Calendar.MONTH, Month - 1);
            startTime.set(Calendar.YEAR, Year);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);
            endTime.set(Calendar.MONTH, Month - 1);
            WeekViewEvent event = new WeekViewEvent(1, "Test", startTime, endTime);
            event.setColor(getResources().getColor(R.color.red_light));
            events.add(event);
        }
            return events;
        }
    };



    private static List<Date> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

}
