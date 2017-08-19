package com.goyo.traveltracker.forms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.database.Tables;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.model_expense;
import com.goyo.traveltracker.model.model_tag_db;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pchmn.materialchips.ChipsInput;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.goyo.traveltracker.Service.NetworkStateReceiver.IsMobailConnected;
import static com.goyo.traveltracker.gloabls.Global.urls.saveExpenseDetails;

public class expense extends AppCompatActivity {

    private EditText Exp_Disc,Exp_value;
    private Spinner Exp_Name;
        List<String> Expense;
    List<String> Expense_Id;
    private String selectedExpense;
    private ChipsInput chipsInput;
    String Selected_Exp,Selected_Value,Selected_Disc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        this.setTitle("Add Expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Exp_Disc=(EditText)findViewById(R.id.exp_disc);
        Exp_value=(EditText)findViewById(R.id.exp_value);


        SQLBase db = new SQLBase(this);

        List<model_tag_db> data = new ArrayList<model_tag_db>();
        List<HashMap<String,String>> d = db.Get_Tags();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                data.add(new model_tag_db( d.get(i).get(Tables.tbltags.Tag_Id), d.get(i).get(Tables.tbltags.Tag_Title),d.get(i).get(Tables.tbltags.Tag_remark_1),d.get(i).get(Tables.tbltags.Tag_remark_2),d.get(i).get(Tables.tbltags.Tag_remark_3),d.get(i).get(Tables.tbltags.Tag_Creat_On),d.get(i).get(Tables.tbltags.Is_Server_Send)));
            }
        }

        chipsInput = (ChipsInput) findViewById(R.id.chip);
        chipsInput.setFilterableList(data);


        //spinner
        Exp_Name=(Spinner)findViewById(R.id.expense_name);

        GetfromDb();




    }


    private void Update(final String Selected_Exp, final String Selected_Value, final String Selected_Disc, List<String> Tags){

        String tag= Joiner.on(",").join(Tags);

        //JSONArray jsArray = new JSONArray(Tags);

        JsonObject json = new JsonObject();
        json.addProperty("expid", Selected_Exp);
        json.addProperty("expdesc", Selected_Disc);
        json.addProperty("expval", Selected_Value);
        json.addProperty("enttid",Global.loginusr.getEnttid() + "");
        json.addProperty("cuid", Global.loginusr.getDriverid() + "");
        json.addProperty("tag","{" + tag + "}");

        Ion.with(this)
                .load(saveExpenseDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            if (result != null) Log.v("result", result.toString());

                            JsonObject o = result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_expensedetails").getAsJsonObject();
                            Toast.makeText(expense.this, o.get("msg").toString(), Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent=new Intent(expense.this,rejected_order.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        catch (Exception ea) {
                            ea.printStackTrace();
                        }

                    }
                });

    }




    private void GetfromDb(){
        //getting expense name from db and setting in spinner
        SQLBase db = new SQLBase(expense.this);

        Expense = new ArrayList<String>();
        Expense_Id = new ArrayList<String>();
        List<HashMap<String,String>> d = db.Get_Expenses_Display();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                Expense.add(d.get(i).get(Tables.tblexpense.Expense_Name));
                Expense_Id.add(d.get(i).get(Tables.tblexpense.Exp_ID));
            }
            bindCurrentTrips3(Expense);
        }
    }


    private void bindCurrentTrips3(List<String> Expense) {
        if (Expense.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Expense);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Exp_Name.setAdapter(dataAdapter);
        }
    }



    public void SendOfflineExpensstoServer() {
        SQLBase db = new SQLBase(this);
        final List<HashMap<String,String>> d = db.Get_Expenses_Offline();
        if(d.size()>0) {
            for (int i = 0; i <= d.size() - 1; i++) {
                final int pos=i;
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> TagsArray = gson.fromJson(d.get(i).get(Tables.tblexpense.Expense_Is_Active), type);
                String tag= Joiner.on(",").join(TagsArray);



                JsonObject json = new JsonObject();
                json.addProperty("expid", d.get(i).get(Tables.tblexpense.Exp_ID));
                json.addProperty("expdesc", d.get(i).get(Tables.tblexpense.Expense_Disc));
                json.addProperty("expval", d.get(i).get(Tables.tblexpense.Expense_Value));
                json.addProperty("enttid",Global.loginusr.getEnttid() + "");
                json.addProperty("cuid", Global.loginusr.getDriverid() + "");
                json.addProperty("tag","{" + tag + "}");

                Ion.with(this)
                        .load(Global.urls.saveExpenseDetails.value)
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                try {
                                    SQLBase db = new SQLBase(expense.this);
                                    db. EXPENSE_UPDATE(d.get(pos).get(Tables.tblexpense.Expense_Id),"2");

                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                }


                            }
                        });
            }
        }

//        return data;
    }



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
                selectedExpense= Exp_Name.getSelectedItem().toString();
                if (selectedExpense.equals("")||selectedExpense==null){
                    Toast.makeText(this, "Please Select an Expense!", Toast.LENGTH_SHORT).show();
                }else {

                    //getting selected tag
                    List<model_tag_db> contactsSelected = (List<model_tag_db>) chipsInput.getSelectedChipList();
//                    String Tags[]=new String[50];
                    List<String> Tags = new ArrayList<String>();
                    if(contactsSelected.size()>0) {
                        for (int i = 0; i <= contactsSelected.size() - 1; i++) {
                            Tags.add(contactsSelected.get(i).getLabel());
                        }
                    }

                    //getting selected value and disc,type
                    int Pos = Exp_Name.getSelectedItemPosition();
                    if(Expense_Id.size()>0) {
                        Selected_Exp = Expense_Id.get(Pos);
                    }
                    Selected_Value = Exp_value.getText().toString();
                    Selected_Disc = Exp_Disc.getText().toString();
//                    SavetoDb();
//                    SentToServer();

                    //time
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                   String time = dateFormat.format(new Date()).toString();

                    //date
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c.getTime());
//                    String TimenDate=formattedDate+", "+time;


                    if(IsMobailConnected){
                        SQLBase db = new SQLBase(this);

                        //tags
                        Gson gson = new Gson();
                        String TagString= gson.toJson(Tags);

                        db.ADDEXPENSE(new model_expense(time,selectedExpense,Selected_Disc, Selected_Value, formattedDate,TagString,"2"));
                        Update(Selected_Exp,Selected_Value,Selected_Disc,Tags);
                    }else {
                            SQLBase db = new SQLBase(this);

                        //tags
                        Gson gson = new Gson();
                        String TagString= gson.toJson(Tags);

                            db.ADDEXPENSE(new model_expense(time,selectedExpense,Selected_Disc, Selected_Value, formattedDate,TagString,"1"));
                            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
                           finish();
                        Intent intent=new Intent(expense.this,rejected_order.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                }
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
