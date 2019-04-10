package com.example.locationalarm_21_28.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.locationalarm_21_28.Adapter.RecyclerAdapter;
import com.example.locationalarm_21_28.Database.MyDatabase;
import com.example.locationalarm_21_28.ModelClass.Model;
import com.example.locationalarm_21_28.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<Model> myList;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=new MyDatabase(this);
        recyclerView=findViewById(R.id.recycler_view_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myList=database.getData();
        recyclerAdapter=new RecyclerAdapter(this,myList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.new_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.add_new_task)
        {
            startActivity(new Intent(MainActivity.this,NewTask.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
