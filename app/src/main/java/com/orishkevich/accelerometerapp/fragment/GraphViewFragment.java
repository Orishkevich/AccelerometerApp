package com.orishkevich.accelerometerapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.orishkevich.accelerometerapp.R;


public class GraphViewFragment extends Fragment {


    public GraphViewFragment() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_view, container, false);



        return rootView;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphView graphX = (GraphView)getActivity().findViewById(R.id.graphx);
        GraphView graphY = (GraphView)getActivity().findViewById(R.id.graphy);
        GraphView graphZ = (GraphView)getActivity().findViewById(R.id.graphz);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graphX.addSeries(series);
        graphY.addSeries(series);
        graphZ.addSeries(series);
    }
}
