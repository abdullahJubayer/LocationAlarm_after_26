package com.example.locationalarm_after26.Service;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.locationalarm_after26.Database.MyDatabase;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LocationService extends JobService {
    public static final String TAG="MyJobService";
    LocationManager locationManager;
    LocationListener locationListener;
    MyDatabase myDB;
    private boolean isJobFinished=false;


    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.e(TAG,"onStartJob");

        myDB=new MyDatabase(this);
        locationManager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                checker(location,params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        if (ContextCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG,"onStopJob");

        isJobFinished=true;
        return true;
    }

    private void checker(Location location, JobParameters params) {

        Log.e(TAG,"checker");

        if (isJobFinished){

            if (locationManager !=null){
                locationManager.removeUpdates(locationListener);
            }
            return ;
        }


        ArrayList<LatLng> setLocation=new ArrayList<>();
        ArrayList<Integer> id=new ArrayList<>();
        ArrayList<Integer> serviceId=new ArrayList<>();

        SQLiteDatabase db=myDB.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select "+MyDatabase.getID()+","+MyDatabase.getLatitude()+","+MyDatabase.getLongitude()+","+MyDatabase.getServiceID()+" From "+MyDatabase.getTableName()+"",null);
        while (cursor.moveToNext()){

            id.add(cursor.getInt(0));
            LatLng lng=new LatLng(cursor.getDouble(1),cursor.getDouble(2));
            setLocation.add(lng);
            serviceId.add(cursor.getInt(3));
        }
        db.close();

        if (cursor.getCount()==0){
            onStopJob(params);
        }

        if (!setLocation.isEmpty()){
            for (int i=0;i<setLocation.size();i++){
                Double latitude=setLocation.get(i).latitude;
                Double longitude=setLocation.get(i).longitude;
                if (latitude != null && longitude !=null){

                    Log.e(TAG,"Current Location :"+String.valueOf(location.getLatitude())+" "+String.valueOf(location.getLongitude()));
                    Log.e(TAG,"Set Location :"+latitude+" "+longitude);

                    if (location.getLatitude()==latitude && location.getLongitude()==longitude){
                        Log.e("MatchResult :","Match");

//                        MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(), R.raw.alerm);
                          stopSelf(serviceId.get(i));
//                        mediaPlayer.start();
//                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                            @Override
//                            public void onCompletion(MediaPlayer mp) {
//                                mp.release();
//                            }
//                        });
                        long result=myDB.deleteService(id.get(i));
                        Log.e("ID :",id.get(i)+"");
                        if (result==-1){
                            Log.e("Delete Result :","Delete Failed");

                        }else {
                            Log.e("Delete Result :","Delete");

                        }

                    }else {
                        Log.e("MatchResult :","Not Match");
                    }
                }
                else {
                    Log.e("SetData :","null");
                }

            }
        }
    }
}
