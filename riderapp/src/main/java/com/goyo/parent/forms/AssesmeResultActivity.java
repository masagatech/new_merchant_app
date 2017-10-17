package com.goyo.parent.forms;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import static com.goyo.parent.forms.dashboard.SclId;

public class AssesmeResultActivity extends AppCompatActivity {

    List<String> Student_Name;
    List<String> Student_Id;
//    public String AseeID="";
//    public  String AseeType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesme_result);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setElevation(0);
        }

        //getting Group Id and Name
//        Intent intent = getIntent();
//        String AsseName = intent.getExtras().getString("AsseName");
//        AseeID=intent.getExtras().getString("AseeID");
//        AseeType=intent.getExtras().getString("AsseType");

//        setTitle(AsseName);
//
//        GetStudent();
    }

    private void GetStudent() {
        JsonObject json = new JsonObject();
        json.addProperty("uid", Preferences.getValue_String(getApplicationContext(), Preferences.USER_ID));
        json.addProperty("flag", "student");
        json.addProperty("enttid", SclId + "");
        Ion.with(this)
                .load(Global.urls.getParentDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            Student_Name = new ArrayList<String>();
                            Student_Id = new ArrayList<String>();
                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                Student_Name.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("studentname").getAsString());
                                Student_Id.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("autoid").getAsString());
                            }
                            SetStudent(Student_Name,Student_Id);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });
    }

    private void SetStudent(List<String> lst,List<String> ID){

        if(lst.size()==1) {
            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(lst.get(0), FragAsseResult.class,new Bundler().putString("ID", ID.get(0)).get())
                    .create());

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
        }


        if(lst.size()==2) {
            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(lst.get(0), FragAsseResult.class,new Bundler().putString("ID", ID.get(0)).get())
                    .add(lst.get(1), FragAsseResult.class,new Bundler().putString("ID", ID.get(1)).get())
                    .create());

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
        }

        if(lst.size()==3) {
            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(lst.get(0), FragAsseResult.class,new Bundler().putString("ID", ID.get(0)).get())
                    .add(lst.get(1), FragAsseResult.class,new Bundler().putString("ID", ID.get(1)).get())
                    .add(lst.get(2), FragAsseResult.class,new Bundler().putString("ID", ID.get(2)).get())
                    .create());

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
        }

        if(lst.size()==4) {
            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(lst.get(0), FragAsseResult.class,new Bundler().putString("ID", ID.get(0)).get())
                    .add(lst.get(1), FragAsseResult.class,new Bundler().putString("ID", ID.get(1)).get())
                    .add(lst.get(2), FragAsseResult.class,new Bundler().putString("ID", ID.get(2)).get())
                    .add(lst.get(3), FragAsseResult.class,new Bundler().putString("ID", ID.get(3)).get())
                    .create());

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
        }

        if(lst.size()==5) {
            FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                    getSupportFragmentManager(), FragmentPagerItems.with(this)
                    .add(lst.get(0), FragAsseResult.class,new Bundler().putString("ID", ID.get(0)).get())
                    .add(lst.get(1), FragAsseResult.class,new Bundler().putString("ID", ID.get(1)).get())
                    .add(lst.get(2), FragAsseResult.class,new Bundler().putString("ID", ID.get(2)).get())
                    .add(lst.get(3), FragAsseResult.class,new Bundler().putString("ID", ID.get(3)).get())
                    .add(lst.get(4), FragAsseResult.class,new Bundler().putString("ID", ID.get(4)).get())
                    .create());

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);
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
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
