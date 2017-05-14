package com.orishkevich.accelerometerapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.adapter.AccelAdapter;
import com.orishkevich.accelerometerapp.adapter.SessionsAdapter;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.DaysModel;
import com.orishkevich.accelerometerapp.model.Session;
import com.orishkevich.accelerometerapp.service.ServiceAccel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;





public class DaysListSessions extends Fragment {


    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
    private static SimpleDateFormat sdfSmall = new SimpleDateFormat("MMM dd, yyyy");

    private DaysModel session;
    private RecyclerView rvMain;

    private LinearLayoutManager layoutManager;

    private SharedPreferences sharedPrefs;
    private String myPrefs = "myPrefs";
    private static final String daySessionSP = "daySessionSP";
    private String sP;

    private final String LOG_TAG = "DayList";
    private  SessionsAdapter sessionAdapter;





    public DaysListSessions() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        downSharedPref();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_days_list_sessions, container, false);
        super.onCreate(savedInstanceState);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterSet(downSharedPref());
    }


    public void adapterSet(DaysModel dayMod) {

        rvMain = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        sessionAdapter = new SessionsAdapter(dayMod);
        rvMain.setAdapter (sessionAdapter);
        sessionAdapter.setOnItemClickListener(new SessionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }



    public DaysModel downSharedPref() {

        sharedPrefs = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if (sharedPrefs.contains(daySessionSP)) {

            sP = sharedPrefs.getString(daySessionSP, "");
            Log.d(LOG_TAG, "downSharedPref()=" + sP);
            session = new Gson().fromJson(sP, DaysModel.class);
            return session;
        } else {
            Log.d(LOG_TAG, "DOESNT downSharedPref()");
            return session= new DaysModel();
        }


    }


}
