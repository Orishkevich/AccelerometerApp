package com.orishkevich.accelerometerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.orishkevich.accelerometerapp.R;


public class SessionsViewHolder extends RecyclerView.ViewHolder {

        public TextView sessionsName;

        public SessionsViewHolder(final View itemView){
            super(itemView);

            sessionsName = (TextView)itemView.findViewById(R.id.sessionsName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AccelAdapter.listener!=null){
                        AccelAdapter.listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }
