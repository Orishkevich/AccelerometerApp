package com.orishkevich.accelerometerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.model.AccelModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AccelAdapter extends RecyclerView.Adapter<AccelViewHolder> {

    public static OnItemClickListener listener;
    private ArrayList<AccelModel> accelModels;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss:SS a");

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AccelAdapter(ArrayList<AccelModel> accelModels) {
        this.accelModels = accelModels;
    }


    @Override
    public void onBindViewHolder(AccelViewHolder personViewHolder, int i) {

        AccelModel current = accelModels.get(i);
        personViewHolder.tvName.setText(String.valueOf(sdf.format(current.getMil())
                + "\n" + "Accelerometer:"
                + "\n" + "X:" + current.getX()
                + "\n" + "Y:" + current.getY()
                + "\n" + "Z:" + current.getZ()));
    }

    @Override
    public AccelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view, viewGroup, false);

        return new AccelViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return accelModels.size();
    }


}