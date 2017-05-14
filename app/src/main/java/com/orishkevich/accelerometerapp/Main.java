package com.orishkevich.accelerometerapp;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.support.v4.app.FragmentTransaction;

import com.orishkevich.accelerometerapp.fragment.AccelerometerFragment;


public class Main extends AppCompatActivity {

    private AccelerometerFragment aF;
    private FragmentTransaction fT;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("Sessions");
        aF = new AccelerometerFragment ();
        fT = getSupportFragmentManager().beginTransaction();
        fT.replace(R.id.frame, aF, "Sessions");
        fT.addToBackStack(null);
        fT.commit();
    }
}