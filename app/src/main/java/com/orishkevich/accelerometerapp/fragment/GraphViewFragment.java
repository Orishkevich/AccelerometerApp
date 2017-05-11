package com.orishkevich.accelerometerapp.fragment;


import android.graphics.Color;
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
        GraphView graphX = (GraphView)getActivity().findViewById(R.id.graph);


        LineGraphSeries<DataPoint> x = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(1, 4),
                new DataPoint(2, 6),
                new DataPoint(3, 8),
                new DataPoint(4, 10)
        });

        LineGraphSeries<DataPoint> y = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 6),
                new DataPoint(2, 9),
                new DataPoint(3, 12),
                new DataPoint(4, 15)
        });

        LineGraphSeries<DataPoint> z = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1.5),
                new DataPoint(1, 3.2),
                new DataPoint(2, 5.7),
                new DataPoint(3, 11),
                new DataPoint(4, 13.6)
        });

        z.setTitle("Z");
        z.setThickness(10);
        z.setColor(Color.RED);
        z.setAnimated(true);
        z.setDrawDataPoints(true);
        z.setDataPointsRadius(15);

        x.setTitle("X");
        x.setThickness(10);
        x.setColor(Color.BLUE);
        x.setAnimated(true);
        x.setDrawDataPoints(true);
        x.setDataPointsRadius(15);

        y.setTitle("Y");
        y.setThickness(10);
        y.setColor(Color.GREEN);
        y.setAnimated(true);
        y.setDrawDataPoints(true);
        y.setDataPointsRadius(15);


        graphX.getViewport().setScalableY(true);
        graphX.addSeries(z);
        graphX.addSeries(x);
        graphX.addSeries(y);
    }
}
