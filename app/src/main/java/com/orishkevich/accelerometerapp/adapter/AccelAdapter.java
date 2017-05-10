package com.orishkevich.accelerometerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.model.AccelModel;

import java.util.ArrayList;

/**
 * Created by Инженер-конструктор on 13.03.2017.
 */

public class AccelAdapter extends RecyclerView.Adapter<AccelViewHolder> {

    public static OnItemClickListener listener;
        private ArrayList<AccelModel> accelModels;


    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public AccelAdapter(ArrayList<AccelModel> accelModels){
        this.accelModels = accelModels;
    }


    @Override
    public void onBindViewHolder(AccelViewHolder personViewHolder, int i){

        AccelModel current = accelModels.get(i);
                personViewHolder.tvName.setText(String.valueOf(current.getMil()));
        }

    @Override
    public AccelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);

        return new AccelViewHolder(itemView);
    }

    @Override
    public int getItemCount(){
        return accelModels.size();
    }

//фильтрация
    public void setFilter(ArrayList<AccelModel> voc) {
        accelModels = new ArrayList<>();
        accelModels.addAll(voc);
        notifyDataSetChanged();
    }
}