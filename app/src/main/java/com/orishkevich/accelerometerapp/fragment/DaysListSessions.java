package com.orishkevich.accelerometerapp.fragment;

import android.content.Context;
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
import com.orishkevich.accelerometerapp.adapter.SessionsAdapter;
import com.orishkevich.accelerometerapp.model.DaysModel;

import java.text.SimpleDateFormat;


public class DaysListSessions extends Fragment {

    private Button btnTest;

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
    private SessionsAdapter sessionAdapter;


    private AccelerometerFragment aF;

    private FragmentTransaction fT;


    public DaysListSessions() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if (savedInstanceState == null) {

            // downSharedPref();
        }
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
        // adapterSet(downSharedPref());
        btnTest = (Button) getActivity().findViewById(R.id.test);
        btnTest.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                aF = new AccelerometerFragment();
                fT = getActivity().getSupportFragmentManager().beginTransaction();
                fT.replace(R.id.frame, aF, "Sessions");
                fT.addToBackStack(null);
                fT.commit();


            }

        });
    }


    public void adapterSet(DaysModel dayMod) {

        rvMain = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        sessionAdapter = new SessionsAdapter(dayMod);
        rvMain.setAdapter(sessionAdapter);
        sessionAdapter.setOnItemClickListener(new SessionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                aF = new AccelerometerFragment();
                fT = getActivity().getSupportFragmentManager().beginTransaction();
                fT.replace(R.id.frame, aF, "Sessions");
                fT.addToBackStack(null);
                fT.commit();
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
            return session = new DaysModel();
        }


    }


}
