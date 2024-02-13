package com.example.gimnasioflex;

import android.app.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    //Tabla clientes
    private static final String DATABASE_NAME = "GimnasioFlex";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Clientes";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Nombre";
    private static final String KEY_APE = "Apellido";
    private static final String KEY_DNI = "DNI";
    private static final String KEY_REGISTRO = "Registro";

    //Tabla asistencia
    private static final String TABLE_NAME_ASISTENCIA = "Asistencia";
    private static final String KEY_FECHA= "Fecha";
    private static final String KEY_CLIENTEFK = "ClienteFK";

    //Tabla cuotas
    private static final String TABLE_NAME_CUOTAS = "Cuotas";
    private static final String KEY_INICIO= "Inicio";
    private static final String KEY_FIN= "Fin";
    private static final String KEY_TIPO= "Tipo";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //crea tabla clientes
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_APE + " TEXT,"
                + KEY_DNI + " TEXT,"
                + KEY_REGISTRO + " DATE)");

        //crea tabla asistencia
        db.execSQL("CREATE TABLE " + TABLE_NAME_ASISTENCIA + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FECHA + " DATE,"
                + KEY_CLIENTEFK + " TEXT)");

        //crea tabla cuotas
        db.execSQL("CREATE TABLE " + TABLE_NAME_CUOTAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TIPO + " TEXT,"
                + KEY_CLIENTEFK + " TEXT,"
                + KEY_INICIO + " DATE,"
                + KEY_FIN + " DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ASISTENCIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUOTAS);
        onCreate(db);
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
    public boolean addCuota(String dni, LocalDate inicio, LocalDate fin, String tipo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENTEFK, dni);
        values.put(KEY_INICIO, String.valueOf(inicio));
        values.put(KEY_FIN, String.valueOf(fin));
        values.put(KEY_TIPO, tipo);
        long res= db.insert(TABLE_NAME_CUOTAS, null, values);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cuota> fetchCuotas(String dni){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Inicio,Fin,DNI,ClienteFK FROM "+ TABLE_NAME_CUOTAS,null);
        ArrayList<Cuota> arrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            Cuota c;
            if (cursor.getString(0)=="Libre" ){
                c = new CuotaLibre(cursor.getString(1), LocalDate.parse(cursor.getString(2)), LocalDate.parse(cursor.getString(3)));
            }
            else if (cursor.getString(0)=="Dos Dias"){
                c = new CuotaDosDias(cursor.getString(1), LocalDate.parse(cursor.getString(2)), LocalDate.parse(cursor.getString(3)));
            }
            else {
                c = new CuotaTresDias(cursor.getString(1), LocalDate.parse(cursor.getString(2)), LocalDate.parse(cursor.getString(3)));
            }
            arrayList.add(c);
        }
        cursor.close();
        return arrayList;
    }
    public Persona getPersonaPorDNI(String dni){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Nombre,Apellido,DNI,Registro FROM "+ TABLE_NAME + " WHERE DNI = '" + dni + "'",null);
        Persona p=null;
        if (cursor.moveToFirst()) { // Mover el cursor al primer resultado si hay alguno
            p = new Persona(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        return p;
    }
}
