package com.orishkevich.accelerometerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orishkevich.accelerometerapp.R;


public class AccelViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;

    public AccelViewHolder(final View itemView) {
        super(itemView);

        tvName = (TextView) itemView.findViewById(R.id.tvName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccelAdapter.listener != null) {
                    AccelAdapter.listener.onItemClick(itemView, getLayoutPosition());
                }
            }
        });
    }
}
