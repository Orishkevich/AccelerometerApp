package com.orishkevich.accelerometerapp.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.adapter.AccelAdapter;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.Session;
import com.orishkevich.accelerometerapp.service.ServiceAccel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AccelerometerFragment extends Fragment {

    private Button btnStart;
    private Button btnStop;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
    private static SimpleDateFormat sdfSmall = new SimpleDateFormat("MMM dd, yyyy");
    private ArrayList<AccelModel> valuesAccelArrays = new ArrayList<AccelModel>();
    private Session session;
    private RecyclerView rvMain;
    private AccelAdapter accelAdapter;
    private LinearLayoutManager layoutManager;
    private Button mSendButton;
    private Spinner spinner;
    private SharedPreferences sharedPrefs;
    private String myPrefs = "myPrefs";
    private static final String valuesAccelArraysList = "valuesAccelArraysList";
    private String sP;
    private int time;
    private final String LOG_TAG = "AccelerometerFragment";
    public final static String BROADCAST_ACTION = "com.orishkevich.accelerometerapp";
    public final static String PARAM_JSON = "JSON";
    private boolean bound = false;
    private ServiceConnection sConn;
    private Intent intent;
    private ServiceAccel myService;
    private BroadcastReceiver br;
    private EditText editTextTimes;
    private int period;
    private GraphViewFragment gF;
    private DatabaseReference mSimpleFirechatDatabaseReference;
    private FragmentTransaction fT;

    public AccelerometerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleFirechatDatabaseReference = FirebaseDatabase.getInstance().getReference();

        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        intent = new Intent(getActivity(), ServiceAccel.class);

        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                Log.d(LOG_TAG, "onReceive:  " + intent.getStringExtra(PARAM_JSON));
                adapterSet(downSharedPref());

                Toast.makeText(getActivity(), "Данные отправленны", Toast.LENGTH_SHORT).show();
                mSimpleFirechatDatabaseReference.child("AccelerometeraAPP")
                        .child(sdfSmall.format(session.getUserAccelModel().get(0).getMil()))
                        .child(session.getUser()).push().setValue(session);

            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);

        getActivity().registerReceiver(br, intFilt);


        /**  sConn = new ServiceConnection() {
         public void onServiceConnected(ComponentName name, IBinder binder) {
         Log.d(LOG_TAG, "onServiceConnected");
         bound = true;

         }

         public void onServiceDisconnected(ComponentName name) {

         Log.d(LOG_TAG, "onServiceDisconnected");
         bound = false;

         }
         };*/


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onCreate(savedInstanceState);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (Button) getActivity().findViewById(R.id.button_start);
        btnStop = (Button) getActivity().findViewById(R.id.button_stop);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        editTextTimes = (EditText) getActivity().findViewById(R.id.times);
        adapterSet(downSharedPref());//создание списка

        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (bound == false) {
                    bound = true;
                    time = Integer.parseInt(spinner.getSelectedItem().toString());
                    period = Integer.parseInt(editTextTimes.getText().toString());
                    Intent intent = new Intent(getActivity(), ServiceAccel.class);
                    intent.putExtra("time", time * 1000);
                    intent.putExtra("period", period * 1000);
                    getActivity().startService(intent);

                    Log.d("AccelerometerFragment", "Start: " + sdf.format(System.currentTimeMillis()));
                } else
                    Toast.makeText(getActivity(), "Служба уже запущенна", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                bound = false;
                getActivity().stopService(new Intent(getActivity(), ServiceAccel.class));


            }

        });


        mSendButton = (Button) getActivity().findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Данные отправленны", Toast.LENGTH_SHORT).show();
                mSimpleFirechatDatabaseReference.child("AccelerometeraAPP")
                        .child(sdfSmall.format(session.getUserAccelModel().get(0).getMil()))
                        .push().setValue(session);
            }
        });
    }


    public void adapterSet(ArrayList<AccelModel> valuesAccelArrays) {
        //downSharedPref();
        rvMain = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        accelAdapter = new AccelAdapter(valuesAccelArrays);
        rvMain.setAdapter(accelAdapter);

        accelAdapter.setOnItemClickListener(new AccelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                gF = new GraphViewFragment();
                fT = getActivity().getSupportFragmentManager().beginTransaction();
                fT.replace(R.id.frame, gF, "Graphs");
                fT.addToBackStack(null);
                fT.commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(br);
        //clearSharedPref();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //clearSharedPref();
    }

    public void clearSharedPref() {
        Log.d(LOG_TAG, "clearSharedPref()");
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(valuesAccelArraysList);
        editor.clear();
        editor.apply();
    }

    public ArrayList<AccelModel> downSharedPref() {

        sharedPrefs = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if (sharedPrefs.contains(valuesAccelArraysList)) {

            sP = sharedPrefs.getString(valuesAccelArraysList, "");
            Log.d(LOG_TAG, "downSharedPref()=" + sP);
            session = new Gson().fromJson(sP, Session.class);
            return valuesAccelArrays = session.getUserAccelModel();
        } else {
            Log.d(LOG_TAG, "DOESNT downSharedPref()");
            return valuesAccelArrays = new ArrayList<>();
        }


    }


}
