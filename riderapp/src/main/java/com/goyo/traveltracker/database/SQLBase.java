package com.goyo.traveltracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.goyo.traveltracker.model.model_tag;
import com.goyo.traveltracker.model.model_task;
import com.goyo.traveltracker.utils.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mTech on 03-Mar-2017.
 */
public class SQLBase  {
    private static final String DATABASE_NAME = "goyoriderv1.db";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    public static SQLiteDatabase sqLiteDB;
    private SQLiteStatement insertStmt;
    DataBaseHelper openHelper;

    public SQLBase(Context context) {
        SQLBase.context = context;
        try{
            this.openHelper = new DataBaseHelper(SQLBase.context);
            SQLBase.sqLiteDB = openHelper.getWritableDatabase();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*Diver Info*/

    public void DRIVER_INFO_INSERT(String arthinm,String mob1,String mob2,String adharno , String ownrship , String vehno
            , String yrsold , String btchno , String howmny , String driving , String alruseing, String goyointr ,
                                   String doyohv   , String prefloc  , String createon , String createdby ,
                                   String sendToServer , String vehtype , String remarks , String lat , String lon,
                                   String profpic ,String profpic_upload ,
                                   String doc1 , String doc1_upload , String doc2 , String doc2_upload , String doc3 , String doc3_upload
            ,String doc4,String doc4_upload
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        ContentValues values = new ContentValues();
        values.put(Tables.tbl_driver_info.mibuid , "" + System.currentTimeMillis());
        values.put(Tables.tbl_driver_info.sarthinm, arthinm);
        values.put(Tables.tbl_driver_info.mob1, mob1);
        values.put(Tables.tbl_driver_info.mob2 , mob2);
        values.put(Tables.tbl_driver_info.adharno, adharno);
        values.put(Tables.tbl_driver_info.ownrship , ownrship);
        values.put(Tables.tbl_driver_info.vehno , vehno);
        values.put(Tables.tbl_driver_info.yrsold , yrsold);
        values.put(Tables.tbl_driver_info.btchno , btchno);
        values.put(Tables.tbl_driver_info.howmny , howmny);
        values.put(Tables.tbl_driver_info.driving , driving);
        values.put(Tables.tbl_driver_info.alruseing, alruseing);
        values.put(Tables.tbl_driver_info.goyointr, goyointr);
        values.put(Tables.tbl_driver_info.doyohv , doyohv);
        values.put(Tables.tbl_driver_info.prefloc , prefloc);
        values.put(Tables.tbl_driver_info.createon , createon);
        values.put(Tables.tbl_driver_info.createdby , createdby);
        values.put(Tables.tbl_driver_info.sentToserver, sendToServer);
        values.put(Tables.tbl_driver_info.vehtype , vehtype);
        values.put(Tables.tbl_driver_info.remarks , remarks);
        values.put(Tables.tbl_driver_info.lat , lat);
        values.put(Tables.tbl_driver_info.lon , lon);
        values.put(Tables.tbl_driver_info.profpic , profpic);
        values.put(Tables.tbl_driver_info.profpic_upload , profpic_upload);
        values.put(Tables.tbl_driver_info.doc1 , doc1);
        values.put(Tables.tbl_driver_info.doc1_upload , doc1_upload);
        values.put(Tables.tbl_driver_info.doc2 , doc2);
        values.put(Tables.tbl_driver_info.doc1_upload , doc2_upload);
        values.put(Tables.tbl_driver_info.doc3 , doc3);
        values.put(Tables.tbl_driver_info.doc3_upload , doc3_upload);
        values.put(Tables.tbl_driver_info.doc4, doc4);
        values.put(Tables.tbl_driver_info.doc4_upload, doc4_upload);
        sqLiteDB.insert(Tables.tbl_driver_info.name,null,values);
        sqLiteDB.close();
    }

    //update
    public void DRIVER_INFO_UPDATE(String autoid,String arthinm,String mob1,String mob2,String adharno  , String ownrship , String vehno
            , String yrsold   , String btchno   , String howmny   , String driving  , String alruseing, String goyointr ,
                                   String doyohv   , String prefloc  , String createon , String createdby,
                                   String sendToServer , String vehtype, String remarks, String lat, String lon,
                                   String profpic,String profpic_upload,
                                   String doc1,String doc1_upload,String doc2,String doc2_upload,String doc3,String doc3_upload
            ,String doc4,String doc4_upload){

        ContentValues values = new ContentValues();
        values.put(Tables.tbl_driver_info.sarthinm, arthinm);
        values.put(Tables.tbl_driver_info.mob1, mob1);
        values.put(Tables.tbl_driver_info.mob2 , mob2);
        values.put(Tables.tbl_driver_info.adharno, adharno);
        values.put(Tables.tbl_driver_info.ownrship , ownrship);
        values.put(Tables.tbl_driver_info.vehno , vehno);
        values.put(Tables.tbl_driver_info.yrsold , yrsold);
        values.put(Tables.tbl_driver_info.btchno , btchno);
        values.put(Tables.tbl_driver_info.howmny , howmny);
        values.put(Tables.tbl_driver_info.driving , driving);
        values.put(Tables.tbl_driver_info.alruseing, alruseing);
        values.put(Tables.tbl_driver_info.goyointr, goyointr);
        values.put(Tables.tbl_driver_info.doyohv , doyohv);
        values.put(Tables.tbl_driver_info.prefloc , prefloc);
        values.put(Tables.tbl_driver_info.sentToserver, sendToServer);
        values.put(Tables.tbl_driver_info.vehtype , vehtype);
        values.put(Tables.tbl_driver_info.remarks , remarks);
        values.put(Tables.tbl_driver_info.profpic , profpic);
        values.put(Tables.tbl_driver_info.profpic_upload , profpic_upload);
        values.put(Tables.tbl_driver_info.doc1 , doc1);
        values.put(Tables.tbl_driver_info.doc1_upload , doc1_upload);
        values.put(Tables.tbl_driver_info.doc2 , doc2);
        values.put(Tables.tbl_driver_info.doc1_upload , doc2_upload);
        values.put(Tables.tbl_driver_info.doc3 , doc3);
        values.put(Tables.tbl_driver_info.doc3_upload , doc3_upload);
        values.put(Tables.tbl_driver_info.doc4, doc4);
        values.put(Tables.tbl_driver_info.doc4_upload, doc4_upload);

        sqLiteDB.update(Tables.tbl_driver_info.name, values, Tables.tbl_driver_info.autoid + " = ?",
                new String[] { String.valueOf(autoid) });
    }

    public List<HashMap<String, String>> DRIVER_INFO_GET(String Query){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbl_driver_info.autoid
                +"," + Tables.tbl_driver_info.mibuid +","
                + Tables.tbl_driver_info.sarthinm +","
                + Tables.tbl_driver_info.mob1 +","
                + Tables.tbl_driver_info.mob2
                +","+ Tables.tbl_driver_info.sentToserver
                +","+  Tables.tbl_driver_info.ownrship
                +","+ Tables.tbl_driver_info.vehno
                +","+ Tables.tbl_driver_info.yrsold +","
                + Tables.tbl_driver_info.btchno
                +","+ Tables.tbl_driver_info.howmny
                +","+ Tables.tbl_driver_info.driving +","
                + Tables.tbl_driver_info.alruseing
                +","+ Tables.tbl_driver_info.goyointr
                +","+ Tables.tbl_driver_info.doyohv +","
                + Tables.tbl_driver_info.prefloc
                +","+ Tables.tbl_driver_info.createon
                +","+ Tables.tbl_driver_info.createdby
                +","+ Tables.tbl_driver_info.vehtype+","
                + Tables.tbl_driver_info.remarks+","
                + Tables.tbl_driver_info.lat+","
                + Tables.tbl_driver_info.lon+","
                + Tables.tbl_driver_info.profpic+","
                + Tables.tbl_driver_info.profpic_upload
                +"," + Tables.tbl_driver_info.doc1
                +"," + Tables.tbl_driver_info.doc1_upload
                +"," + Tables.tbl_driver_info.doc2
                +"," + Tables.tbl_driver_info.doc2_upload
                +"," + Tables.tbl_driver_info.doc3
                +"," + Tables.tbl_driver_info.doc3_upload
                +"," + Tables.tbl_driver_info.doc4
                +"," + Tables.tbl_driver_info.doc4_upload
                +","+ Tables.tbl_driver_info.adharno

                + " FROM " + Tables.tbl_driver_info.name + " " + Query;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbl_driver_info.autoid, cursor.getString(0));
                map.put(Tables.tbl_driver_info.mibuid, cursor.getString(1));
                map.put(Tables.tbl_driver_info.sarthinm, cursor.getString(2));
                map.put(Tables.tbl_driver_info.mob1, cursor.getString(3));
                map.put(Tables.tbl_driver_info.mob2, cursor.getString(4));
                map.put(Tables.tbl_driver_info.sentToserver, cursor.getString(5));
                map.put(Tables.tbl_driver_info.ownrship, cursor.getString(6));
                map.put(Tables.tbl_driver_info.vehno, cursor.getString(7));
                map.put(Tables.tbl_driver_info.yrsold, cursor.getString(8));
                map.put(Tables.tbl_driver_info.btchno, cursor.getString(9));
                map.put(Tables.tbl_driver_info.howmny, cursor.getString(10));
                map.put(Tables.tbl_driver_info.driving, cursor.getString(11));
                map.put(Tables.tbl_driver_info.alruseing, cursor.getString(12));
                map.put(Tables.tbl_driver_info.goyointr, cursor.getString(13));
                map.put(Tables.tbl_driver_info.doyohv, cursor.getString(14));
                map.put(Tables.tbl_driver_info.prefloc, cursor.getString(15));
                map.put(Tables.tbl_driver_info.createon, cursor.getString(16));
                map.put(Tables.tbl_driver_info.createdby, cursor.getString(17));
                map.put(Tables.tbl_driver_info.vehtype, cursor.getString(18));
                map.put(Tables.tbl_driver_info.remarks, cursor.getString(19));
                map.put(Tables.tbl_driver_info.lat, cursor.getString(20));
                map.put(Tables.tbl_driver_info.lon, cursor.getString(21));
                map.put(Tables.tbl_driver_info.profpic, cursor.getString(22));
                map.put(Tables.tbl_driver_info.profpic_upload, cursor.getString(23));
                map.put(Tables.tbl_driver_info.doc1, cursor.getString(24));
                map.put(Tables.tbl_driver_info.doc1_upload, cursor.getString(25));
                map.put(Tables.tbl_driver_info.doc2, cursor.getString(26));
                map.put(Tables.tbl_driver_info.doc2_upload, cursor.getString(27));
                map.put(Tables.tbl_driver_info.doc3, cursor.getString(28));
                map.put(Tables.tbl_driver_info.doc3_upload, cursor.getString(29));
                map.put(Tables.tbl_driver_info.doc4, cursor.getString(30));
                map.put(Tables.tbl_driver_info.doc4_upload, cursor.getString(31));
                map.put(Tables.tbl_driver_info.adharno, cursor.getString(32));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }


    public List<HashMap<String, String>> DRIVER_INFO_GET_GRID(String Query){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbl_driver_info.autoid
                + ","  + Tables.tbl_driver_info.mibuid
                + ","  + Tables.tbl_driver_info.sarthinm
                + ","  + Tables.tbl_driver_info.mob1
                + "," + Tables.tbl_driver_info.createon
                + ","  + Tables.tbl_driver_info.sentToserver
                + ","  +  Tables.tbl_driver_info.profpic
                + " FROM " + Tables.tbl_driver_info.name + " " + Query;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbl_driver_info.autoid, cursor.getString(0));
                map.put(Tables.tbl_driver_info.mibuid, cursor.getString(1));
                map.put(Tables.tbl_driver_info.sarthinm, cursor.getString(2));
                map.put(Tables.tbl_driver_info.mob1, cursor.getString(3));
                map.put(Tables.tbl_driver_info.createon, cursor.getString(4));
                map.put(Tables.tbl_driver_info.sentToserver, cursor.getString(5));
                map.put(Tables.tbl_driver_info.profpic, cursor.getString(6));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }


    //update offline
    public void DRIVER_INFO_UPDATE_OFFLINE(String _uniqueid, String status, String servid){
        sqLiteDB.execSQL("UPDATE " + Tables.tbl_driver_info.name + " SET "
                +  Tables.tbl_driver_info.sentToserver + "=" + "'true'"+ ","
                +  Tables.tbl_driver_info.servrid + "=" + "'" + servid  + "'"

                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + _uniqueid);
    }

    //update offline
    public void DRIVER_INFO_UPDATE_File_OFFLINE(String _uniqueid){
        sqLiteDB.execSQL("UPDATE " + Tables.tbl_driver_info.name + " SET "
                +  Tables.tbl_driver_info.profpic_upload + "=" + "'true'"
                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + _uniqueid);
    }


    //############################################################################################################
    // notifications

    public void NOTIFICATION_INSERT(String data, Integer exptm
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        ContentValues values = new ContentValues();


        values.put(Tables.tblnotification.createon , "" + common.dateandtime(context));
        values.put(Tables.tblnotification.data, data);
        values.put(Tables.tblnotification.exp, exptm + "");
        sqLiteDB.insert(Tables.tblnotification.name,null,values);
        //sqLiteDB.close();
    }

    public void NOTIFICATION_DELETE(String autoid
    ){
        //insertStmt = sqLiteDB.compileStatement(Procedures.tbl_driver_info.INSERT);
        sqLiteDB.execSQL("DELETE FROM " + Tables.tblnotification.name
                + " WHERE " + Tables.tbl_driver_info.autoid + "=" + autoid);
        //sqLiteDB.close();
    }




    public List<HashMap<String, String>> NOTIFICATION_GET(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblnotification.autoid
                +"," + Tables.tblnotification.createon +","
                + Tables.tblnotification.data+","
                + Tables.tblnotification.exp

                + " FROM " + Tables.tblnotification.name ;
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblnotification.autoid, cursor.getString(0));
                map.put(Tables.tblnotification.createon, cursor.getString(1));
                map.put(Tables.tblnotification.data, cursor.getString(2));
                map.put(Tables.tblnotification.exp, cursor.getString(3));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data;
    }

    //############################################################################################################################
    //######################################Offline Tasks#########################################################################
    //############################################################################################################################
        // Adding new contact
        public void OFFLINE_TASK_ADDTASK(model_task Task) {

            ContentValues values = new ContentValues();
            values.put(Tables.tblofflinetask.Task_Title, Task.get_title());
            values.put(Tables.tblofflinetask.Task_Body, Task.get_body());
            values.put(Tables.tblofflinetask.Task_Lat, Task.get_lat());
            values.put(Tables.tblofflinetask.Task_Lon, Task.get_lon());
            values.put(Tables.tblofflinetask.Task_Tags, Task.get_tags());
            values.put(Tables.tblofflinetask.Task_Creat_On, Task.get_creat_on());
            values.put(Tables.tblofflinetask.Is_Server_Send, Task.get_is_server_send());
            values.put(Tables.tblofflinetask.Task_Time, Task.get_time());

            // Inserting Row
            sqLiteDB.insert(Tables.tblofflinetask.name, null, values);
//            sqLiteDB.close(); // Closing database connection
        }



        public List<HashMap<String, String>> Get_TASK(){
            List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//            String selectEvents = "SELECT "
//                    + Tables.tblofflinetask.Task_Id
//                    + Tables.tblofflinetask.Task_Title +","
//                    + Tables.tblofflinetask.Task_Body +","
//                    + Tables.tblofflinetask.Task_Lat +","
//                    + Tables.tblofflinetask.Task_Lon +","
//                    + Tables.tblofflinetask.Task_Tags +","
//                    + Tables.tblofflinetask.Task_Creat_On+","
//                    + Tables.tblofflinetask.Is_Server_Send+","
//                    + Tables.tblofflinetask.Task_Time
//                    + " FROM " + Tables.tblnotification.name +" WHERE "
//                    + Tables.tblofflinetask.Is_Server_Send + " = '0'";
            Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tblofflinetask.name, null);
//            Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(Tables.tblofflinetask.Task_Id, cursor.getString(0));
                    map.put(Tables.tblofflinetask.Task_Title, cursor.getString(1));
                    map.put(Tables.tblofflinetask.Task_Body, cursor.getString(2));
                    map.put(Tables.tblofflinetask.Task_Lat, cursor.getString(3));
                    map.put(Tables.tblofflinetask.Task_Lon, cursor.getString(4));
                    map.put(Tables.tblofflinetask.Task_Tags, cursor.getString(5));
                    map.put(Tables.tblofflinetask.Task_Creat_On, cursor.getString(6));
                    map.put(Tables.tblofflinetask.Is_Server_Send, cursor.getString(7));
                    map.put(Tables.tblofflinetask.Task_Time, cursor.getString(8));
                    data.add(map);
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }
            return data;
        }

    public Boolean ISTASK_ALREDY_EXIST(String Tag_Name){

        String Query = "SELECT "
                + Tables.tblofflinetask.Task_Id+","
                +Tables.tblofflinetask.Task_Title+","
                + Tables.tblofflinetask.Task_Body +","
                + Tables.tblofflinetask.Task_Lat +","
                + Tables.tblofflinetask.Task_Lon +","
                + Tables.tblofflinetask.Task_Tags +","
                + Tables.tblofflinetask.Task_Creat_On +","
                + Tables.tblofflinetask.Is_Server_Send+","
                + Tables.tblofflinetask.Task_Time
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Task_Title + " = '" + Tag_Name+ "'";
        Cursor cursor = sqLiteDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public List<HashMap<String, String>> Get_Tasks_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tblofflinetask.Task_Id+","
                +Tables.tblofflinetask.Task_Title+","
                + Tables.tblofflinetask.Task_Body +","
                + Tables.tblofflinetask.Task_Lat +","
                + Tables.tblofflinetask.Task_Lon +","
                + Tables.tblofflinetask.Task_Tags +","
                + Tables.tblofflinetask.Task_Creat_On +","
                + Tables.tblofflinetask.Is_Server_Send+","
                + Tables.tblofflinetask.Task_Time
                + " FROM " + Tables.tblofflinetask.name +" WHERE "
                + Tables.tblofflinetask.Is_Server_Send+ " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tblofflinetask.Task_Id, cursor.getString(0));
                map.put(Tables.tblofflinetask.Task_Title, cursor.getString(1));
                map.put(Tables.tblofflinetask.Task_Body, cursor.getString(2));
                map.put(Tables.tblofflinetask.Task_Lat, cursor.getString(3));
                map.put(Tables.tblofflinetask.Task_Lon, cursor.getString(4));
                map.put(Tables.tblofflinetask.Task_Tags, cursor.getString(5));
                map.put(Tables.tblofflinetask.Task_Creat_On, cursor.getString(6));
                map.put(Tables.tblofflinetask.Is_Server_Send, cursor.getString(7));
                map.put(Tables.tblofflinetask.Task_Time, cursor.getString(8));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }

    //update offline
    public void OFFLINE_TASK_UPDATE(String _taskName, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tblofflinetask.name + " SET "
                +  Tables.tblofflinetask.Is_Server_Send + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tblofflinetask.Task_Title + " = '" + _taskName+ "'");
    }

    public long getProfilesCount() {
        sqLiteDB = openHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(sqLiteDB, Tables.tblofflinetask.name);
        return cnt;
    }

    //update offline
    public void OFFLINE_TASK_DELETE(String _uniqueid){
        sqLiteDB.execSQL("DELETE  FROM " + Tables.tblofflinetask.name
                + " WHERE " + Tables.tblofflinetask.Task_Id + "=" + _uniqueid);
    }


    //adding Tags
    //############################################################################################################################
    public void TAG_ADDTAG(model_tag Tag) {

        ContentValues values = new ContentValues();
        values.put(Tables.tbltags.Tag_Title, Tag.get_title());
        values.put(Tables.tbltags.Tag_remark_1, Tag.get_rem_1());
        values.put(Tables.tbltags.Tag_remark_2, Tag.get_rem_2());
        values.put(Tables.tbltags.Tag_remark_3, Tag.get_rem_3());
        values.put(Tables.tbltags.Tag_Empl_Id, Tag.get_empl_id());
        values.put(Tables.tbltags.Tag_Creat_On, Tag.get_creat_on());
        values.put(Tables.tbltags.Is_Server_Send, Tag.get_is_server_send());

        // Inserting Row
        sqLiteDB.insert(Tables.tbltags.name, null, values);
//        sqLiteDB.close(); // Closing database connection
    }

   public Boolean ISTAG_ALREDY_EXIST(String Tag_Name){

       String Query = "SELECT "
               + Tables.tbltags.Tag_Id+","
               + Tables.tbltags.Tag_Title+","
               + Tables.tbltags.Tag_remark_1 +","
               + Tables.tbltags.Tag_remark_2 +","
               + Tables.tbltags.Tag_remark_3 +","
               + Tables.tbltags.Tag_Empl_Id +","
               + Tables.tbltags.Tag_Creat_On +","
               + Tables.tbltags.Is_Server_Send
               + " FROM " + Tables.tbltags.name +" WHERE "
               + Tables.tbltags.Tag_Title + " = '" + Tag_Name+ "'";
           Cursor cursor = sqLiteDB.rawQuery(Query, null);
           if(cursor.getCount() <= 0){
               cursor.close();
               return false;
           }
           cursor.close();
           return true;
    }

    public List<HashMap<String, String>> Get_Tags(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
//        String selectEvents = "SELECT "
//                + Tables.tbltags.Tag_Id
//                + Tables.tbltags.Tag_Title+","
//                + Tables.tbltags.Tag_remark_1 +","
//                + Tables.tbltags.Tag_remark_2 +","
//                + Tables.tbltags.Tag_remark_3 +","
//                + Tables.tbltags.Tag_Creat_On +","
//                + Tables.tbltags.Is_Server_Send
//                + " FROM " + Tables.tbltags.name +" WHERE "
//                + Tables.tbltags.Is_Server_Send + " = '0'";
//        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltags.Tag_Id, cursor.getString(0));
                map.put(Tables.tbltags.Tag_Title, cursor.getString(1));
                map.put(Tables.tbltags.Tag_remark_1, cursor.getString(2));
                map.put(Tables.tbltags.Tag_remark_2, cursor.getString(3));
                map.put(Tables.tbltags.Tag_remark_3, cursor.getString(4));
                map.put(Tables.tbltags.Tag_Empl_Id, cursor.getString(5));
                map.put(Tables.tbltags.Tag_Creat_On, cursor.getString(6));
                map.put(Tables.tbltags.Is_Server_Send, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    public List<HashMap<String, String>> Get_Tags_Offline(){
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String selectEvents = "SELECT "
                + Tables.tbltags.Tag_Id+","
                + Tables.tbltags.Tag_Title+","
                + Tables.tbltags.Tag_remark_1 +","
                + Tables.tbltags.Tag_remark_2 +","
                + Tables.tbltags.Tag_remark_3 +","
                + Tables.tbltags.Tag_Empl_Id +","
                + Tables.tbltags.Tag_Creat_On +","
                + Tables.tbltags.Is_Server_Send
                + " FROM " + Tables.tbltags.name +" WHERE "
                + Tables.tbltags.Is_Server_Send + " = '1'";
        Cursor cursor = sqLiteDB.rawQuery(selectEvents, null);
//        Cursor cursor = sqLiteDB.rawQuery("SELECT * FROM "+ Tables.tbltags.name, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Tables.tbltags.Tag_Id, cursor.getString(0));
                map.put(Tables.tbltags.Tag_Title, cursor.getString(1));
                map.put(Tables.tbltags.Tag_remark_1, cursor.getString(2));
                map.put(Tables.tbltags.Tag_remark_2, cursor.getString(3));
                map.put(Tables.tbltags.Tag_remark_3, cursor.getString(4));
                map.put(Tables.tbltags.Tag_Empl_Id, cursor.getString(5));
                map.put(Tables.tbltags.Tag_Creat_On, cursor.getString(6));
                map.put(Tables.tbltags.Is_Server_Send, cursor.getString(7));
                data.add(map);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return data ;
    }


    //update offline
    public void TAG_UPDATE(String _tagName, String sendtoserver){
        sqLiteDB.execSQL("UPDATE " + Tables.tbltags.name + " SET "
                +  Tables.tbltags.Is_Server_Send + "='" + sendtoserver+ "'"
                + " WHERE " + Tables.tbltags.Tag_Title + " = '" + _tagName+ "'");
    }


    //update offline
    public void TAG_DELETE(String _uniqueid){
        sqLiteDB.execSQL("DELETE FROM " + Tables.tbltags.name
                + " WHERE " + Tables.tbltags.Tag_Id + "=" + _uniqueid);
    }


    //############################################################################################################################
    class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(Procedures.tbl_driver_info.CREATE);
            db.execSQL(Procedures.tblnotification.CREATE);
            db.execSQL(Procedures.tbltag.CREATE);
            db.execSQL(Procedures.tblofflinetask.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
            switch (oldVersion)
            {
                case 1://upgrade from version 1 to 2

                  break;
                case 2://upgrade from version 2 to 3

                    break;

            }





        }
    }

    public void close(){
        try{
        //openHelper.close();
        //sqLiteDB.close();
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
