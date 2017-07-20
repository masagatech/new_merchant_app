package com.goyo.traveltracker.forms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_tag;

import java.text.DateFormat;
import java.util.Date;

public class AddTags extends AppCompatActivity {
    private EditText Tag_Name,Tag_Remark_1,Tag_Remark_2,Tag_Remark_3;
     SQLBase db = new SQLBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);

        this.setTitle("Add Tags");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        List<HashMap<String,String>> d = db.Get_Tags();
//        if(d.size()>0) {
//            for (int i = 0; i <= d.size() - 1; i++) {
//                String log = "Id: " + d.get(i).get(Tables.tbltags.Tag_Id) + " ,Title: " + d.get(i).get(Tables.tbltags.Tag_Title) + " ,Body: " + d.get(i).get(Tables.tbltags.Tag_remark_1 + " ,Lat: " + d.get(i).get(Tables.tbltags.Tag_remark_2) + " ,Lon: " + d.get(i).get(Tables.tbltags.Tag_remark_3) + " ,EMP ID: " + d.get(i).get(Tables.tbltags.Tag_Empl_Id) + " ,Created On: " + d.get(i).get(Tables.tbltags.Tag_Creat_On) + " ,Is Server Send: " + d.get(i).get(Tables.tbltags.Is_Server_Send));
//                // Writing Contacts to log
//                Log.e("Name: ", log);
//                Toast.makeText(this, log, Toast.LENGTH_SHORT).show();
//            }
//        }
        //tags
        Tag_Name=(EditText)findViewById(R.id.tag_name);
        Tag_Remark_1=(EditText)findViewById(R.id.tag_remark_1);
        Tag_Remark_2=(EditText)findViewById(R.id.tag_remark_2);
        Tag_Remark_3=(EditText)findViewById(R.id.tag_remark_3);
    }

    private void SavetoDb(){
        String  tag_name,tag_remark_1,tag_remark_2,tag_remark_3,currentDateTimeString,Empl_Id;
        currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        tag_name=Tag_Name.getText().toString();
        Empl_Id= String.valueOf(Global.loginusr.getDriverid());
        tag_remark_1=Tag_Remark_1.getText().toString();
        tag_remark_2=Tag_Remark_2.getText().toString();
        tag_remark_3=Tag_Remark_3.getText().toString();

        db.TAG_ADDTAG(new model_tag(tag_name,tag_remark_1,tag_remark_2,tag_remark_3,Empl_Id,currentDateTimeString,0));
    }


    //set action bar button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_info_activity, menu);
        return true;
    }


    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_driver_info_save:
                SavetoDb();
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
               onBackPressed();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
