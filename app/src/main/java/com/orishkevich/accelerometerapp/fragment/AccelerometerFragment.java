package com.orishkevich.accelerometerapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.adapter.AccelAdapter;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.ArrayAccelModel;
import com.orishkevich.accelerometerapp.service.ServiceAccel;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class AccelerometerFragment extends Fragment {
    Button btnStart;
    Button btnStop;

    private ArrayList<AccelModel> valuesAccelArrays=new ArrayList <AccelModel>();
    private ArrayAccelModel arrayAccelModel;
    public RecyclerView rvMain;
    public AccelAdapter accelAdapter;
    public LinearLayoutManager layoutManager;


    private SharedPreferences sharedPrefs;
    public String myPrefs = "myPrefs";
    public static final String valuesAccelArraysList = "valuesAccelArraysList";

    public AccelerometerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        if (sharedPrefs.contains(valuesAccelArraysList)) {
           String sP=sharedPrefs.getString(valuesAccelArraysList, "");
            Log.d("AccelerometerFragment", "toJsonArrayList="+sP);
            arrayAccelModel=new Gson().fromJson(sP, ArrayAccelModel.class);
        }

        valuesAccelArrays=new ArrayList<>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onCreate(savedInstanceState);


        return rootView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (Button) getActivity().findViewById(R.id.button_start);
        btnStop = (Button) getActivity().findViewById(R.id.button_stop);



       valuesAccelArrays=arrayAccelModel.getAccelModel();
         rvMain = (RecyclerView)getActivity().findViewById(R.id.my_recycler_view);
         //rvMain.setHasFixedSize(true);
         layoutManager = new LinearLayoutManager(getActivity());
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         rvMain.setLayoutManager(layoutManager);
         accelAdapter = new AccelAdapter(valuesAccelArrays);
         rvMain.setAdapter(accelAdapter);

         accelAdapter.setOnItemClickListener(new AccelAdapter.OnItemClickListener(){
             @Override
             public void onItemClick(View v, int position){
                 Log.d("", "");


             }
         });
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                getActivity().startService(
                        new Intent(getActivity(), ServiceAccel.class));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getActivity().stopService(
                        new Intent(getActivity(), ServiceAccel.class));
            }
        });
    }


}
