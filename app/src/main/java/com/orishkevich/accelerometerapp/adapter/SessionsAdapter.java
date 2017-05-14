package com.orishkevich.accelerometerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.DaysModel;
import com.orishkevich.accelerometerapp.model.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Инженер-конструктор on 13.03.2017.
 */

public class SessionsAdapter extends RecyclerView.Adapter<SessionsViewHolder> {

    public static OnItemClickListener listener;
    private DaysModel dayMod;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss:SS a");

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public SessionsAdapter(DaysModel accelModels){
        this.dayMod = accelModels;
    }


    @Override
    public void onBindViewHolder(SessionsViewHolder personViewHolder, int i){

        String  current=dayMod.getDateName();
                personViewHolder.sessionsName.setText(current);
        }

    @Override
    public SessionsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);

        return new SessionsViewHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return dayMod.getSessions().size();
    }


}