package com.orishkevich.accelerometerapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Инженер-конструктор on 14.05.2017.
 */

public class DetailsActivity  extends Activity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "DetailsActivity", Toast.LENGTH_SHORT).show();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.

            // create fragment
            DetailsFragment details = new DetailsFragment();

            // get and set the position input by user (i.e., "index")
            // which is the construction arguments for this fragment
            details.setArguments(getIntent().getExtras());

            //
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, details).commit();
        }

    }

public int getShownIndex() {
        return getArguments().getInt("index", 0);
        }

// The system calls this when it's time for the fragment to draw its
// user interface for the first time. To draw a UI for your fragment,
// you must return a View from this method that is the root of your
// fragment's layout. You can return null if the fragment does not
// provide a UI.

// We create the UI with a scrollview and text and return a reference to
// the scoller which is then drawn to the screen

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {



        // programmatically create a scrollview and textview for the text in
        // the container/fragment layout. Set up the properties and add the view

        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int) TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 4, getActivity()
        .getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        text.setText(Shakespeare.DIALOGUE[getShownIndex()]);
        return scroller;
        }*/
        }