package com.goyo.traveltracker.forms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.goyo.traveltracker.R;
import com.goyo.traveltracker.database.SQLBase;
import com.goyo.traveltracker.gloabls.Global;
import com.goyo.traveltracker.model.modal_leave;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.goyo.traveltracker.Service.NetworkStateReceiver.IsMobailConnected;

public class AddLeave extends AppCompatActivity {
    EditText Leave_From,Leave_To,Details;
    Calendar myCalendar,myCalendar1;
    List<String> Leave_Type;
    Spinner LeaveType;
    Button ApplyLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_leave);


        this.setTitle("Apply Leave");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Leave_From = (EditText) findViewById(R.id.leave_from);
        Details = (EditText) findViewById(R.id.leave_details);
        Leave_To = (EditText) findViewById(R.id.leave_to);
        LeaveType = (Spinner) findViewById(R.id.leave_type);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        Leave_From.setText(formattedDate);
        Leave_To.setText(formattedDate);


        Leave_Type = new ArrayList<String>();
        Leave_Type.add("Physical Reason");
        Leave_Type.add("Emergency");
        Leave_Type.add("Other Reasons");

        if (Leave_Type.size() > 0) {

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Leave_Type);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            LeaveType.setAdapter(dataAdapter);
        }

        ApplyLeave = (Button) findViewById(R.id.leave_apply);
        ApplyLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details=Details.getText().toString();
                if (details.equals("")){
                    Toast.makeText(AddLeave.this, "Please Enter Details!", Toast.LENGTH_SHORT).show();
                }else {
                    SavetoDb();
                    SentToServer();
                }
            }
        });



        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        Leave_From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddLeave.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        myCalendar1 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };

        Leave_To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddLeave.this, date2, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Leave_From.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Leave_To.setText(sdf.format(myCalendar1.getTime()));
    }


    private void SentToServer(){
        String  details,leave_from,leave_to,selected_leave_type,currentDateTimeString;
        selected_leave_type=LeaveType.getSelectedItem().toString();
        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        leave_from=Leave_From.getText().toString();
        leave_to=Leave_To.getText().toString();
        details=Details.getText().toString();

        JsonObject json = new JsonObject();
        json.addProperty("empid",Global.loginusr.getDriverid()+"");
        json.addProperty("frmdt", leave_from);
        json.addProperty("todt", leave_to);
        json.addProperty("restype", selected_leave_type);
        json.addProperty("cuid", currentDateTimeString);
        json.addProperty("enttid", Global.loginusr.getEnttid()+"");
        json.addProperty("reason",details);
        Ion.with(this)
                .load(Global.urls.saveLeaveEmployee.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            JsonObject o= result.get("data").getAsJsonArray().get(0).getAsJsonObject().get("funsave_employeeleave").getAsJsonObject();
                            Toast.makeText(AddLeave.this, o.get("msg").toString(), Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent=new Intent(AddLeave.this,rejected_order.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });

    }

    private void SavetoDb(){
        String  details,leave_from,leave_to,selected_leave_type,currentDateTimeString;
        selected_leave_type=LeaveType.getSelectedItem().toString();
        currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        leave_from=Leave_From.getText().toString();
        leave_to=Leave_To.getText().toString();
        details=Details.getText().toString();

        SQLBase db = new SQLBase(this);
        if(IsMobailConnected){
                db.ADDLeave(new modal_leave(leave_from, leave_to, selected_leave_type, details, currentDateTimeString,"0"));
        }else {

              db.ADDLeave(new modal_leave(leave_from, leave_to, selected_leave_type, details, currentDateTimeString,"1"));
                finish();
                Intent intent=new Intent(AddLeave.this,rejected_order.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(AddLeave.this, "Saved successfully", Toast.LENGTH_SHORT).show();
        }
    }



    //action bar menu button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
