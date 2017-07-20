package com.goyo.traveltracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.goyo.traveltracker.model.model_task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mis on 14-Jul-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Tasks";

    // Contacts table name
    private static final String TABLE_TASK = "Task";

    // Contacts Table Columns names
    private static final String Task_Id = "id";
    private static final String Task_Title = "task_title";
    private static final String Task_Body = "task_body";
    private static final String Task_Lat = "task_lat";
    private static final String Task_Lon = "task_lon";
    private static final String Task_Empl_Id = "task_empl_id";
    private static final String Task_Creat_On = "task_creat_on";
    private static final String Is_Server_Send = "is_server_send";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                + Task_Id + " INTEGER PRIMARY KEY," + Task_Title + " TEXT,"
                + Task_Body + " TEXT," + Task_Lat + " TEXT," + Task_Lon + " TEXT," + Task_Empl_Id + " TEXT," + Task_Creat_On + " TEXT," + Is_Server_Send + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
   public void addTask(model_task Task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Task_Title, Task.get_title());
        values.put(Task_Body, Task.get_body());
        values.put(Task_Lat, Task.get_lat());
        values.put(Task_Lon, Task.get_lon());
        values.put(Task_Empl_Id, Task.get_empl_id());
        values.put(Task_Creat_On, Task.get_creat_on());
        values.put(Is_Server_Send, Task.get_is_server_send());

        // Inserting Row
        db.insert(TABLE_TASK, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    model_task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASK, new String[] { Task_Id,
                        Task_Title, Task_Body,Task_Lat,Task_Lon,Task_Empl_Id,Task_Creat_On,Is_Server_Send }, Task_Id + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        model_task task = new model_task(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7));
        // return contact
        return task;
    }

    // Getting All Contacts
    public List<model_task> getAllContacts() {
        List<model_task> taskList = new ArrayList<model_task>();
        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_TASK;

        String selectQuery= "SELECT TOP 10 * FROM " + TABLE_TASK +" WHERE "+Is_Server_Send+" = '0'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                model_task task = new model_task();
                task.set_id(Integer.parseInt(cursor.getString(0)));
                task.set_title(cursor.getString(1));
                task.set_body(cursor.getString(2));
                task.set_lat(cursor.getString(3));
                task.set_lon(cursor.getString(4));
                task.set_empl_id(cursor.getString(5));
                task.set_creat_on(cursor.getString(6));
                task.set_is_server_send(cursor.getInt(7));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

    // Updating single contact
    public int updateTask(model_task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Task_Title, task.get_title());
        values.put(Task_Body, task.get_body());
        values.put(Task_Lat, task.get_lat());
        values.put(Task_Lon, task.get_lon());
        values.put(Task_Empl_Id, task.get_empl_id());
        values.put(Task_Creat_On, task.get_creat_on());
        values.put(Is_Server_Send, task.get_is_server_send());
//        values.put(Task,task.get_is_server_send());

        // updating row
        return db.update(TABLE_TASK, values, Task_Id + " = ?",
                new String[] { String.valueOf(task.get_id()) });
    }

    // Deleting single contact
    public void deleteTask(model_task contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, Task_Id + " = ?",
                new String[] { String.valueOf(contact.get_id()) });
        db.close();
    }


    // Getting contacts Count
    public int getTasksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
