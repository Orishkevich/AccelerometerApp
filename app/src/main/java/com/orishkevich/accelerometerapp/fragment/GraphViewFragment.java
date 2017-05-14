package com.orishkevich.accelerometerapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.Session;

import java.util.ArrayList;


public class GraphViewFragment extends Fragment {
    private Button btnRefresh;
    private SharedPreferences sharedPrefs;
    private String myPrefs = "myPrefs";
    private static final String valuesAccelArraysList = "valuesAccelArraysList";
    private final String LOG_TAG = "GraphViewFragment";
    private ArrayList<AccelModel> valuesAccelArrays = new ArrayList<AccelModel>();
    private Session session = null;
    private GraphView graphX;

    public GraphViewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        // valuesAccelArrays=downSharedPref();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_view, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        graphX = (GraphView) getActivity().findViewById(R.id.graph);
        // graphX.removeAllSeries();
        createdGraphs();
        btnRefresh = (Button) getActivity().findViewById(R.id.button_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                graphX.removeAllSeries();
                createdGraphs();
                Toast.makeText(getActivity(), "График обновился", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<AccelModel> downSharedPref() {

        sharedPrefs = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if (sharedPrefs.contains(valuesAccelArraysList)) {
            Log.d(LOG_TAG, "downSharedPref()");
            String sP = sharedPrefs.getString(valuesAccelArraysList, "");
            session = new Gson().fromJson(sP, Session.class);
            return valuesAccelArrays = session.getUserAccelModel();
        } else {
            Log.d(LOG_TAG, "DOESNT downSharedPref()");
            return valuesAccelArrays = new ArrayList<>();
        }
    }

    public DataPoint[] creatGraphX(ArrayList<AccelModel> valuesAccelArray) {
        int sizeArr = valuesAccelArray.size();
        DataPoint[] dp = new DataPoint[sizeArr];
        for (int x = 0; x < sizeArr; x++) {
            Log.d(LOG_TAG, "Xo:(" + x + "," + valuesAccelArray.get(x).getX() + ")");
            dp[x] = new DataPoint(x, valuesAccelArray.get(x).getX());
        }
        return dp;
    }

    public DataPoint[] creatGraphY(ArrayList<AccelModel> valuesAccelArray) {
        int sizeArr = valuesAccelArray.size();
        DataPoint[] dp = new DataPoint[sizeArr];
        for (int y = 0; y < sizeArr; y++) {
            Log.d(LOG_TAG, "Yo:(" + y + "," + valuesAccelArray.get(y).getY() + ")");
            dp[y] = new DataPoint(y, valuesAccelArray.get(y).getY());
        }
        return dp;
    }

    public DataPoint[] creatGraphZ(ArrayList<AccelModel> valuesAccelArray) {
        int sizeArr = valuesAccelArray.size();
        DataPoint[] dp = new DataPoint[sizeArr];
        for (int z = 0; z < sizeArr; z++) {
            Log.d(LOG_TAG, "Zo:(" + z + "," + valuesAccelArray.get(z).getZ() + ")");
            dp[z] = new DataPoint(z, valuesAccelArray.get(z).getZ());
        }
        return dp;
    }

    public void createdGraphs() {
        Log.d(LOG_TAG, "createdGraphs()");
        valuesAccelArrays = downSharedPref();
        LineGraphSeries<DataPoint> x = new LineGraphSeries<DataPoint>(creatGraphX(valuesAccelArrays));
        LineGraphSeries<DataPoint> y = new LineGraphSeries<DataPoint>(creatGraphY(valuesAccelArrays));
        LineGraphSeries<DataPoint> z = new LineGraphSeries<DataPoint>(creatGraphZ(valuesAccelArrays));

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

        //graphX.getScaleX();

        //   graphX.getViewport().setScalableY(true);

        graphX.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {

                    return super.formatLabel(value, isValueX) + " s";
                } else {

                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graphX.getLegendRenderer().setVisible(true);
        graphX.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphX.addSeries(z);
        graphX.addSeries(x);
        graphX.addSeries(y);
    }
}
