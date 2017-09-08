package com.goyo.parent.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goyo.parent.R;
import com.goyo.parent.model.model_trip_map;

import java.util.List;

/**
 * Created by mis on 22-Aug-17.
 */

public class Map_Trip_Adapter extends BaseAdapter {
    List<model_trip_map> list;
    LayoutInflater inflater;
    Context context;


    public Map_Trip_Adapter(Context context, List<model_trip_map> lst, Resources rs) {
        this.list  = lst;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.map_trip_content, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }


        mViewHolder.FromDate.setText(list.get(position).strtm);
        mViewHolder.ToDate.setText(list.get(position).endtm);
        mViewHolder.Trip_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return convertView;
    }


    private class MyViewHolder {
        private TextView FromDate,ToDate;
        private ImageView Trip_Btn;


        public MyViewHolder(View item) {
            Trip_Btn = (ImageView) item.findViewById(R.id.Trip_Btn);
            FromDate = (TextView) item.findViewById(R.id.Start_Time);
            ToDate = (TextView) item.findViewById(R.id.End_Time);
        }
    }

}
