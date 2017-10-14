package com.goyo.parent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.model.modal_data;

import java.util.List;

import az.plainpie.animation.PieAngleAnimation;

/**
 * Created by mis on 13-Oct-17.
 */

public class AsseFragAdapter  extends RecyclerView.Adapter<ResultFragViewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;
    private String Temp="";





    public AsseFragAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }
    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public ResultFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.item_asse_frag, parent, false);

        return new ResultFragViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ResultFragViewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        if(position==0){
            Temp=timeLineModel.assresdate;
            holder.Header.setText(Temp+"");
            holder.Header.setVisibility(View.VISIBLE);
        }else {
            if(Temp.equals(timeLineModel.assresdate)){
                holder.Header.setVisibility(View.GONE);
            }else {
                Temp=timeLineModel.assresdate;
                holder.Header.setText(Temp+"");
                holder.Header.setVisibility(View.VISIBLE);
            }
        }

        //setting Group name and last update date
        holder.mOrder.setText(timeLineModel.resremark + "");
        holder.mMarchant.setText(timeLineModel.heading + "");
        holder.Status.setText(timeLineModel.subheading + "");

        if(timeLineModel.resremark.equals("")){
            holder.mOrder.setVisibility(View.GONE);
        }
//        holder.mDate.setText("Out of "+timeLineModel.outofmarks );
//        holder.Custmer_name.setText(timeLineModel.countannc + "");


        switch (timeLineModel.gradename) {
            case "A+":
                holder.pieView.setPercentage(98);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ff99cc00"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation2 = new PieAngleAnimation(holder.pieView);
                animation2.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation2);
                break;
            case "A":

                holder.pieView.setPercentage(80);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ff99cc00"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation = new PieAngleAnimation(holder.pieView);
                animation.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation);
                break;
            case "B+":

                holder.pieView.setPercentage(70);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation6 = new PieAngleAnimation(holder.pieView);
                animation6.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation6);
                break;

            case "B":
                holder.pieView.setPercentage(60);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffffbb33"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation3 = new PieAngleAnimation(holder.pieView);
                animation3.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation3);
                break;
            case "C+":

                holder.pieView.setPercentage(50);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffff4444"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation7 = new PieAngleAnimation(holder.pieView);
                animation7.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation7);
                break;
            case "C":

                holder.pieView.setPercentage(40);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffff4444"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation4 = new PieAngleAnimation(holder.pieView);
                animation4.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation4);
                break;
            case "D":

                holder.pieView.setPercentage(20);
                holder.pieView.setPercentageBackgroundColor(Color.parseColor("#ffff4444"));
                holder.pieView.setInnerText(timeLineModel.gradename+"");
                holder.pieView.setInnerTextVisibility(View.VISIBLE);
                PieAngleAnimation animation5 = new PieAngleAnimation(holder.pieView);
                animation5.setDuration(1000); //This is the duration of the animation in millis
                holder.pieView.startAnimation(animation5);
                break;
        }




//        if(timeLineModel.countannc.equals("0")){
//            Toast.makeText(mContext, "There is no Announcement in this group!", Toast.LENGTH_SHORT).show();
//        }else {
        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {
//                    Intent intent = new Intent(mContext, complated_order.class);
//                    intent.putExtra("GrpName", timeLineModel.grpname);
//                    intent.putExtra("GrpID", timeLineModel.grpid);
//                    mContext.startActivity(intent);
            }
        });
    }
//
//        if (timeLineModel.isEnabled) {
//            holder.ClickToHide.setVisibility(View.VISIBLE);
//        } else {
//            holder.ClickToHide.setVisibility(View.GONE);
//        }
//    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}


