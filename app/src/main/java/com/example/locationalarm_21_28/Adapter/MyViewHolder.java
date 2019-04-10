package com.example.locationalarm_21_28.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.locationalarm_21_28.R;

public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title,location,time;
    MyRecyclerViewItemCliclListner itemCliclListner;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        title=itemView.findViewById(R.id.title_id);
        location=itemView.findViewById(R.id.location_id);
        time=itemView.findViewById(R.id.time_id);
        //itemView.setOnClickListener(this);
    }



    public interface MyRecyclerViewItemCliclListner{
        void onItemClick(int position);
    }
}
