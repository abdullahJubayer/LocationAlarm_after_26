package com.example.locationalarm_after26.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locationalarm_after26.ModelClass.Model;
import com.example.locationalarm_after26.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<Model> mydata;
    public RecyclerAdapter(Context context,ArrayList<Model> mydata){
        this.context=context;
        this.mydata=mydata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view_item_desigm,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Model modelDB =mydata.get(i);
        myViewHolder.title.setText(modelDB.getTitle());
        myViewHolder.location.setText(String.valueOf(modelDB.getLatitude())+","+String.valueOf(modelDB.getLongitude()));
        myViewHolder.time.setText(modelDB.getTime());
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }
}
