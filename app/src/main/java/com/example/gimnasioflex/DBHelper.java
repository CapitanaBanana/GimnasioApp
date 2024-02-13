package com.example.gimnasioflex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GimnasioFlex";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Clientes";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Nombre";
    private static final String KEY_APE = "Apellido";
    private static final String KEY_DNI = "DNI";
    private static final String KEY_REGISTRO = "Registro";
    private static final String TABLE_NAME_ASISTENCIA = "Asistencia";
    private static final String KEY_FECHA= "Fecha";
    private static final String KEY_CLIENTEFK = "ClienteFK";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_APE + " TEXT,"
                + KEY_DNI + " TEXT,"
                + KEY_REGISTRO + " DATE)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_ASISTENCIA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FECHA + " DATE,"
                + KEY_CLIENTEFK + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ASISTENCIA);
    }

    public boolean addClient(String name, String ape, String dni){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_APE, ape);
        values.put(KEY_DNI, dni);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put(KEY_REGISTRO, String.valueOf(LocalDate.now()));
        }

        long res= db.insert(TABLE_NAME, null, values);
       if(res==-1)
           return false;
       else
           return true;
    }
    public boolean addAsistencia(String dni){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLIENTEFK, dni);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put(KEY_FECHA, String.valueOf(LocalDate.now()));
        }
        long res= db.insert(TABLE_NAME_ASISTENCIA, null, values);
        if(res==-1)
            return false;
        else
            return true;
    }
    public ArrayList<Persona> fetchClient(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Nombre,Apellido,DNI,Registro FROM "+ TABLE_NAME,null);
        ArrayList<Persona> arrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            Persona p = new Persona(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
            arrayList.add(p);
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<LocalDate> fetchAsistencia(String dni){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Fecha FROM "+ TABLE_NAME_ASISTENCIA + " WHERE ClienteFK = '" + dni + "'",null );
        ArrayList<LocalDate> arrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            LocalDate fecha = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                  fecha = LocalDate.parse(cursor.getString(0));
            }
            arrayList.add(fecha);
        }
        cursor.close();
        return arrayList;
    }
}
