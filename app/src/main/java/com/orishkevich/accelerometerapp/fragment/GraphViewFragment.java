package com.orishkevich.accelerometerapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orishkevich.accelerometerapp.R;


public class GraphViewFragment extends Fragment {


    public GraphViewFragment() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_view, container, false);


        return rootView;
    }

}
