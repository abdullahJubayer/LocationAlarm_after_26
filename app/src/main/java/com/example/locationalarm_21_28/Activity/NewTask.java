package com.example.locationalarm_21_28.Activity;

import android.Manifest;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.locationalarm_21_28.Database.MyDatabase;
import com.example.locationalarm_21_28.ModelClass.Model;
import com.example.locationalarm_21_28.R;
import com.example.locationalarm_21_28.Service.LocationService;

import java.util.Calendar;
import java.util.Random;

public class NewTask extends AppCompatActivity implements View.OnClickListener {
    ImageButton imageButton;
    static EditText location;
    EditText title,note;
    TextView time;
    Button save,cancel;
    MyDatabase myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        getSupportActionBar().setTitle("New Task");

        myDB=new MyDatabase(this);

        imageButton=findViewById(R.id.location_button_id);
        location=findViewById(R.id.location_text_id);
        save=findViewById(R.id.location_save_id);
        time=findViewById(R.id.location_time_id);
        title=findViewById(R.id.location_title_id);
        note=findViewById(R.id.location_note_id);
        cancel=findViewById(R.id.location_cancel_id);

        imageButton.setOnClickListener(this);
        time.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v==imageButton){
            startActivity(new Intent(NewTask.this,MapsActivity.class));
        }
        if (v==save){

            if (validate()){
                String pointLocation=location.getText().toString();
                String pLocation[]=pointLocation.split(",");
                Random random=new Random();
                int id=random.nextInt(100);

                if (ContextCompat.checkSelfPermission(NewTask.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    saveData(title.getText().toString(),time.getText().toString(),pLocation[0],pLocation[1],note.getText().toString(),String.valueOf(id));

                    ComponentName componentName=new ComponentName(NewTask.this, LocationService.class);
                    JobInfo jobInfo=new JobInfo.Builder(123,componentName)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setRequiresBatteryNotLow(true)
                            .build();
                    JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    int result=scheduler.schedule(jobInfo);

                    if (result == JobScheduler.RESULT_SUCCESS){
                        scheduler.cancel(123);
                        Log.e("Job Result","Success");
                    }
                    else if (result == JobScheduler.RESULT_FAILURE){
                        Log.e("Job Result","Failed");
                        scheduler.schedule(jobInfo);
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(NewTask.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }
            }

        }
        if (v==time){

            Calendar calendar=Calendar.getInstance();
            int hour=calendar.get(Calendar.HOUR_OF_DAY);
            int minute=calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog= new TimePickerDialog(NewTask.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time.setText(hourOfDay+" : "+minute);
                }
            },hour,minute,false);
              timePickerDialog.setTitle("Select Time");
              timePickerDialog.show();
        }
    }

    private void saveData(String title, String time, String latitude, String longitude, String note, String id) {
        Model modelDB =new Model("0",title,time,latitude,longitude,note,id);
        long result= myDB.insertData(modelDB);
        if (result==-1){
            Toast.makeText(NewTask.this,"Data Inserted Failed",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(NewTask.this,"Data Inserted Success",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate() {
        boolean val = true;

        String Title = title.getText().toString();
        String Time = time.getText().toString();
        String Location = location.getText().toString();
        String Note = note.getText().toString();
        if (Title.isEmpty()) {
            val = false;
            title.setError("Title is Empty...!");
        }
        if (Time.isEmpty()) {
            val = false;
            title.setError("Time is Empty...!");
        }
        if (Location.isEmpty()) {
            val = false;
            title.setError("Location is Empty...!");
        } else {

            String[] string = Location.split(",");
            if (string.length != 2) {
                val = false;
                title.setError("Location Not Valid...!");
            }
        }

        return val;
    }

}
