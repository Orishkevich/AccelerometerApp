package com.orishkevich.accelerometerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import com.orishkevich.accelerometerapp.fragment.AccelerometerFragment;
import com.orishkevich.accelerometerapp.fragment.DaysListSessions;


public class Main extends AppCompatActivity {

    private AccelerometerFragment aF;
    private DaysListSessions dF;
    private FragmentTransaction fT;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("Sessions");
        dF = new DaysListSessions();
        fT = getSupportFragmentManager().beginTransaction();
        fT.replace(R.id.frame, dF, "Sessions");
        fT.addToBackStack(null);
        fT.commit();
    }
}