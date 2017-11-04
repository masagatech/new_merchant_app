package com.goyo.parent.forms;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.goyo.parent.R;
import com.goyo.parent.adapters.sem_frag_adapter;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.model.modal_data;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.List;

import static com.goyo.parent.forms.dashboard.SclId;
import static com.goyo.parent.gloabls.Global.urls.getExamDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class sem_fragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private com.goyo.parent.adapters.sem_frag_adapter mTimeLineAdapter;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private ProgressDialog loader;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    String ID;
    String SemIDs,SemName;

    public sem_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_sem_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ID = bundle.getString("ID");
            SemName = bundle.getString("SemName");
            SemIDs = bundle.getString("SemID");
        }

       getActivity().setTitle("Exam - "+SemName);


        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = true;


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.Refresh);

        //refresh data at first time
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //api call
                DataFromServer(ID);
            }
        });

        //swipe to refresh data
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items and get data from server
                DataFromServer(ID);
            }
        });



        return view;

    }

    private void DataFromServer(String ID) {
        JsonObject json = new JsonObject();
        json.addProperty("flag", "byparents");
        json.addProperty("studid", ID+"");
        json.addProperty("enttid", SclId+"");
        json.addProperty("smstrid", SemIDs+"");
        json.addProperty("uid", Preferences.getValue_String(getActivity(), Preferences.USER_ID));
        Ion.with(this)
                .load(getExamDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            if (result != null) Log.v("result", result.toString());
                            // JSONObject jsnobject = new JSONObject(jsond);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<modal_data>>() {
                            }.getType();
                            List<modal_data> events = (List<modal_data>) gson.fromJson(result.get("data"), listType);
                            bindCurrentTrips(events);
                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

//                    }
                });
    }
    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }
    private void bindCurrentTrips(List<modal_data> lst) {
        if (lst.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.txtNodata).setVisibility(View.GONE);
            mTimeLineAdapter = new sem_frag_adapter(lst, mOrientation, mWithLinePadding);
            mRecyclerView.setAdapter(mTimeLineAdapter);
            mTimeLineAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            view.findViewById(R.id.txtNodata).setVisibility(View.VISIBLE);
        }
    }

}
