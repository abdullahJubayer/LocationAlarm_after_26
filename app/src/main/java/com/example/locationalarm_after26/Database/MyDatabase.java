package com.example.locationalarm_after26.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.locationalarm_after26.ModelClass.Model;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    Context context;
    private static  int VERSION=1;
    private static  String TableName="LocationData";
    private static  String ID="ID";
    private static  String Title="Title";
    private static  String Time="Time";
    private static  String Latitude="Latitude";
    private static  String Longitude="Longitude";
    private static  String Note="Note";
    private static  String ServiceID="ServiceID";

    private static  String DropTable="Drop Table If Exists "+TableName;
    private static  String CreateTable="Create Table if not Exists "+TableName+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Title+" Varchar,"+Time+
            " Varchar,"+Latitude+ " Varchar,"+Longitude+" Varchar,"+Note+" Text,"+ServiceID+" Varchar);";



    public MyDatabase(Context context) {
        super(context, "MyDatabase", null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CreateTable);
            Toast.makeText(context,"Table Created Success ",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,"Table Created Failed Cause: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL(DropTable);
            Toast.makeText(context,"Table Deleted Success ",Toast.LENGTH_LONG).show();
            onCreate(db);
        }
        catch (Exception e){
            Toast.makeText(context,"Table Deleted Failed Cause: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public long insertData(Model modelDB){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Title, modelDB.getTitle());
        contentValues.put(Time, modelDB.getTime());
        contentValues.put(Latitude, modelDB.getLatitude());
        contentValues.put(Longitude, modelDB.getLongitude());
        contentValues.put(Note, modelDB.getNote());
        contentValues.put(ServiceID, modelDB.getServiceID());
        long row=sqLiteDatabase.insert(TableName,null,contentValues);
        sqLiteDatabase.close();
        return row;
    }

    public ArrayList<Model> getData(){
        ArrayList<Model> list=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.query(TableName,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Model modelDB =new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            list.add(modelDB);
        }
        db.close();

        return list;

    }

    public long deleteService(Integer id) {
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TableName,"ID = ?",new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public static String getTableName() {
        return TableName;
    }

    public static String getID() {
        return ID;
    }

    public static String getTitle() {
        return Title;
    }

    public static String getTime() {
        return Time;
    }

    public static String getLatitude() {
        return Latitude;
    }

    public static String getLongitude() {
        return Longitude;
    }

    public static String getNote() {
        return Note;
    }

    public  static String getServiceID() {
        return ServiceID;
    }
}
