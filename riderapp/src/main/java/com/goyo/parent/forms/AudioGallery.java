package com.goyo.parent.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goyo.parent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioGallery extends Fragment {

    private View view;

    public AudioGallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_audio_gallery, container, false);
        return view;
    }

}
