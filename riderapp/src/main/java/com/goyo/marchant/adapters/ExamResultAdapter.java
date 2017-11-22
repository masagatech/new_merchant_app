package com.goyo.marchant.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.marchant.R;
import com.goyo.marchant.forms.ExamResultActivity;
import com.goyo.marchant.forms.Orientation;
import com.goyo.marchant.forms.PendingOrdersView;
import com.goyo.marchant.forms.frag_exam_result;
import com.goyo.marchant.gloabls.Global;
import com.goyo.marchant.model.modal_data;

import java.util.List;

/**
 * Created by mis on 12-Oct-17.
 */

public class ExamResultAdapter extends RecyclerView.Adapter<pending_order_viewHolder>  {

    private List<modal_data> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;
    private ProgressDialog loader;
    private Fragment mfragment;





    public ExamResultAdapter(List<modal_data> feedList, Orientation orientation, boolean withLinePadding,Fragment fragment) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
        mfragment=fragment;

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



        holder.mMarchant.setText(timeLineModel.smstrname + "");
        holder.Custmer_name.setText("0");
        holder.Btn_Call.setVisibility(View.GONE);

        holder.Border.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View m) {

                frag_exam_result frag_exam_result = new frag_exam_result();
                Bundle bundle = new Bundle();
                bundle.putString("SemName", timeLineModel.smstrname);
                bundle.putString("SemID", timeLineModel.smstrid);
                frag_exam_result.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = mfragment.getParentFragment().getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameResultList, frag_exam_result);
                transaction.addToBackStack(null);
                transaction.commit();
                Global.Tabfrg   = mfragment.getParentFragment().getChildFragmentManager();


                Intent intent = new Intent(mContext, ExamResultActivity.class);
//                intent.putExtra("SemName", timeLineModel.smstrname);
//                intent.putExtra("SemID", timeLineModel.smstrid);
//                mContext.startActivity(intent);
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


