package com.goyo.parent.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.goyo.parent.R;
import com.goyo.parent.fragments.ResultMainFragment;
import com.goyo.parent.gloabls.Global;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultList extends Fragment {

    private FrameLayout Assesment,Exams,Frame;
    private View view;

    public ResultList() {
        // Required empty public constructor
    }

    String ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_result_list, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ID = bundle.getString("ID");
        }


        bindMainFragResult();



        return view;
    }

    private void bindMainFragResult(){

        ResultMainFragment assesmentFrag = new ResultMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID", ID);
        assesmentFrag.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameResultList, assesmentFrag);
        transaction.addToBackStack(null);
        transaction.commit();
        Global.Tabfrg   = getChildFragmentManager();
    }

}
