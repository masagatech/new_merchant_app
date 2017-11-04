package com.goyo.parent.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;
import com.goyo.parent.forms.Orientation;
import com.goyo.parent.forms.PendingOrdersView;
import com.goyo.parent.forms.sem_fragment;
import com.goyo.parent.model.modal_data;

import java.util.List;

/**
 * Created by mis on 12-Oct-17.
 */

public class SemesterListAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;
    private Fragment mfragment;

    private String ID;



    public SemesterListAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding,Fragment fragment,String Id) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
        mfragment=fragment;
        ID=Id;

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

        view = mLayoutInflater.inflate(R.layout.item_semester, parent, false);

        return new pending_order_viewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final pending_order_viewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
        final modal_data timeLineModel = mFeedList.get(position);


        if(!(timeLineModel.examdt==null)) {
            holder.mOrder.setText("Last Update : " + timeLineModel.examdt + "");
        }
        holder.mMarchant.setText(timeLineModel.smstrname + "");
        holder.Custmer_name.setText(timeLineModel.countexam + "");

            holder.Border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View m) {

                    sem_fragment sem_fragment = new sem_fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("SemName", timeLineModel.smstrname);
                    bundle.putString("SemID", timeLineModel.smstrid);
                    bundle.putString("ID", ID);
                    sem_fragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = mfragment.getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.Frames, sem_fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

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

