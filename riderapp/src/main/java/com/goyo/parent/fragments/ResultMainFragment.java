package com.goyo.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.goyo.parent.R;
import com.goyo.parent.common.Preferences;
import com.goyo.parent.forms.AssesmentFrag;
import com.goyo.parent.forms.ExamFrag;
import com.goyo.parent.gloabls.Global;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.goyo.parent.forms.dashboard.SclId;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultMainFragment extends Fragment {

    private FrameLayout Assesment,Exams,Frame;
    private View view;
    private TextView Count_Exams,Count_Ass;
    private String _exam="",_ass="";
    public ResultMainFragment() {
        // Required empty public constructor
    }

    String _ID = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_result_main, container, false);
        Assesment=(FrameLayout)view.findViewById(R.id.Asses);
        Exams=(FrameLayout)view.findViewById(R.id.Exams);
        Count_Exams=(TextView)view.findViewById(R.id.Exam_Count);
        Count_Ass=(TextView)view.findViewById(R.id.Ass_Count);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            _ID = bundle.getString("ID");
        }

        getActivity().setTitle("Result");

        GetCount();
//        Frame=(FrameLayout)view.findViewById(R.id.FrameResultList);

        Assesment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssesmentFrag assesmentFrag = new AssesmentFrag();
                Bundle bundle = new Bundle();
                bundle.putString("ID", _ID);
                assesmentFrag.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameResultList, assesmentFrag);
                transaction.addToBackStack(null);
                transaction.commit();
                Global.Tabfrg   = getParentFragment().getChildFragmentManager();
            }
        });

        Exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamFrag examFrag = new ExamFrag();
                Bundle bundle = new Bundle();
                bundle.putString("ID", _ID);
                examFrag.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameResultList, examFrag);
                transaction.addToBackStack(null);
                transaction.commit();
                Global.Tabfrg   = getParentFragment().getChildFragmentManager();
            }
        });

        return view;
    }


    private void GetCount() {
        JsonObject json = new JsonObject();
        json.addProperty("uid", Preferences.getValue_String(getActivity(), Preferences.USER_ID));
        json.addProperty("flag", "result");
        json.addProperty("enttid", SclId + "");
        json.addProperty("studid", _ID + "");
        Ion.with(this)
                .load(Global.urls.getParentDetails.value)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        try {
                            //Student_Id = new ArrayList<String>();
                            for (int i = 0; i < result.get("data").getAsJsonArray().size(); i++) {
                                JsonObject o = result.get("data").getAsJsonArray().get(i).getAsJsonObject();
//                                Student_Name.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("studentname").getAsString());
//                                Student_Id.add(result.get("data").getAsJsonArray().get(i).getAsJsonObject().get("autoid").getAsString());

                                if(o.get("resulttyp").getAsString().equals("Exam")){
                                    _exam=o.get("countresult").getAsString();
                                }else if(o.get("resulttyp").getAsString().equals("Assesment")){
                                    _ass=o.get("countresult").getAsString();
                                }

                            }
                            setCount(_exam,_ass);
                            //SetStudent(Student_Name,Student_Id);

                        } catch (Exception ea) {
                            ea.printStackTrace();
                        }


                    }
                });
    }

    private void setCount(String exam,String ass) {
        Count_Exams.setText(exam);
        Count_Ass.setText(ass);
    }

}
