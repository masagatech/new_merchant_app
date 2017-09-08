package com.goyo.parent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goyo.parent.R;
import com.goyo.parent.model.model_order_details;

import java.util.ArrayList;

/**
 * Created by fajar on 08-Jun-17.
 */

public class OrderDetailsAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    public ArrayList<model_order_details> data;

    public OrderDetailsAdapter(ArrayList<model_order_details> item,Context context) {

        this.data = item;
        this.inflater = LayoutInflater.from(context);
        this.context = context;

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
            convertView = inflater.inflate(R.layout.all_order_details_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        model_order_details details = data.get(position);


//
//        mViewHolder.uDate.setText(details.dt+"");
//        mViewHolder.uReturn.setText(details.ret+"");
//        mViewHolder.uDelivery.setText(details.del+"");
//        mViewHolder.uRejected.setText("");
//        mViewHolder.uTotal.setText((details.del+details.ret)+"");


        mViewHolder.uDate.setText(details.strtm+"");
        mViewHolder.uStops.setText(details.stops+"");
        mViewHolder.uKM.setText(details.km+"");
        mViewHolder.uCheckIn.setText(details.chkin+"");
        mViewHolder.uCheckOut.setText(details.chkout+"");
        mViewHolder.uTask.setText(details.task+"");



        return convertView;

    }






    private class MyViewHolder {


        TextView uDate;
        TextView uStops;
        TextView uKM;
        TextView uCheckIn;
        TextView uCheckOut;
        TextView uTask;

        public MyViewHolder(View item) {
            uDate = (TextView) item.findViewById(R.id.DateAllOrder);
            uStops = (TextView) item.findViewById(R.id.StopsAllOrder);
            uKM = (TextView) item.findViewById(R.id.KMAllOrder);
            uCheckIn = (TextView) item.findViewById(R.id.CheckInAllOrder);
            uCheckOut = (TextView) item.findViewById(R.id.CheckOutAllOrder);
            uTask = (TextView) item.findViewById(R.id.TaskAllOrder);
        }
    }
}