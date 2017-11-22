package com.goyo.marchant.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.marchant.R;
import com.goyo.marchant.forms.Orientation;
import com.goyo.marchant.forms.PendingOrdersView;
import com.goyo.marchant.model.modal_data;

import java.util.List;

/**
 * Created by mis on 08-Nov-17.
 */

public class TimeTableAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;


    public TimeTableAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;

    }
    @Override
    public int getItemViewType(int position) {
        return PendingOrdersView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public pending_order_viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        view = mLayoutInflater.inflate(R.layout.item_timetable, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


//        if(!(timeLineModel.examdt==null)) {
//
//        }
        holder.mOrder.setText("To " + timeLineModel.totm + "");
        holder.mMarchant.setText(timeLineModel.subname + "");
        holder.Custmer_name.setText(timeLineModel.frmtm + "");

        if(timeLineModel.rsttyp.equals("subject")){
            holder.CardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.orange_light));
        }else {
            holder.CardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.red_light));
        }

        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {

//                sem_fragment sem_fragment = new sem_fragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("SemName", timeLineModel.smstrname);
//                bundle.putString("SemID", timeLineModel.smstrid);
//                bundle.putString("ID", ID);
//                sem_fragment.setArguments(bundle);
//                android.support.v4.app.FragmentTransaction transaction = mfragment.getChildFragmentManager().beginTransaction();
//                transaction.replace(R.id.Frames, sem_fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();

//                    Intent intent = new Intent(mContext, Semester.class);
//                    intent.putExtra("SemName", timeLineModel.smstrname);
//                    intent.putExtra("SemID", timeLineModel.smstrid);
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


