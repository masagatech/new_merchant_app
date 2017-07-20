package com.goyo.traveltracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goyo.traveltracker.R;
import com.goyo.traveltracker.model.model_rider_list;

import java.util.ArrayList;

/**
 * Created by fajar on 07-Jun-17.
 */

public class RiderListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    Integer rdrid;
    public ArrayList<model_rider_list> data;

    public RiderListAdapter(Context context, ArrayList<model_rider_list> item, Integer _rdrid) {

        this.inflater = LayoutInflater.from(context);
        this.rdrid = _rdrid;
        this.context = context;
        this.data = item;


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.csutem_spinner, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
       final model_rider_list Rider = data.get(position);

        mViewHolder.uRidername.setText(" "+Rider.nm);
        mViewHolder.uPhone.setText(Rider.mb);
        mViewHolder.uKm.setText(Rider.km+" KM");
        mViewHolder.uBtry.setText(Rider.btry+"%");
        mViewHolder.uLastSeen.setText(Rider.up_on+" Min");
        mViewHolder.hldr.setBackgroundColor(Color.parseColor("#ffffffff"));
        if(Rider.rdrid == this.rdrid){
            mViewHolder.hldr.setBackgroundColor(Color.parseColor("#cccccccc"));
        }
        mViewHolder.uPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialContactPhone(Rider.mb);
            }
        });

        //showing red icon if rider is offline
        if(Rider.onoff){
            mViewHolder.uRidername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.rider_on, 0, 0, 0);
        }else {
            mViewHolder.uRidername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.rider_off, 0, 0, 0);
        }

        //showing red timer if rider last seen more than 15 min
        if(Integer.parseInt(Rider.up_on)>=15){
            mViewHolder.uLastSeen.setCompoundDrawablesWithIntrinsicBounds( R.drawable.time_up_rider, 0, 0, 0);
        }else {
            mViewHolder.uLastSeen.setCompoundDrawablesWithIntrinsicBounds( R.drawable.times, 0, 0, 0);
        }

        //showing different icon for different batery level in riders list
        if(Rider.btry<30.0){
            mViewHolder.uBtry.setCompoundDrawablesWithIntrinsicBounds( R.drawable.btry_low, 0, 0, 0);
        }else if (Rider.btry>=30.0&&Rider.btry<=80.0){
            mViewHolder.uBtry.setCompoundDrawablesWithIntrinsicBounds( R.drawable.btry_midd, 0, 0, 0);
        }else if (Rider.btry>80.0){
            mViewHolder.uBtry.setCompoundDrawablesWithIntrinsicBounds( R.drawable.btry_ful, 0, 0, 0);
        }


        return convertView;

    }




    private void dialContactPhone(final String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    private class MyViewHolder {

        TextView uRidername;
        TextView uPhone;
        TextView uKm;
        TextView uBtry;
        TextView uLastSeen;
        LinearLayout hldr;


        public MyViewHolder(View item) {
            hldr =(LinearLayout)item;
            uRidername = (TextView) item.findViewById(R.id.name);
            uLastSeen= (TextView) item.findViewById(R.id.rider_lastseen);
            uPhone = (TextView) item.findViewById(R.id.rider_numbr);
            uKm = (TextView) item.findViewById(R.id.km);
            uBtry = (TextView) item.findViewById(R.id.rider_btry);
        }
    }
}