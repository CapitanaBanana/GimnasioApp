package com.example.gimnasioflex.activities;

import static com.example.gimnasioflex.utils.Common.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.gimnasioflex.models.Cuota;
import com.example.gimnasioflex.models.Persona;

import java.time.LocalDate;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {



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

        //crea tabla cuotas
        db.execSQL("CREATE TABLE " + TABLE_NAME_CUOTAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TIPO + " TEXT,"
                + KEY_CLIENTEFK + " TEXT,"
                + KEY_INICIO + " DATE,"
                + KEY_FIN + " DATE,"
                + KEY_PRECIO + " INTEGER)");

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

    public boolean addCuota(String dni, LocalDate inicio, LocalDate fin, String tipo, int precio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENTEFK, dni);
        values.put(KEY_INICIO, String.valueOf(inicio));
        values.put(KEY_FIN, String.valueOf(fin));
        values.put(KEY_TIPO, tipo);
        values.put(KEY_PRECIO, precio);
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Cuota> fetchCuotas(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ClienteFK,Inicio,Fin,Tipo,Precio FROM "+ TABLE_NAME_CUOTAS,null);
        ArrayList<Cuota> arrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            Cuota c;
            c = new Cuota(cursor.getString(0), LocalDate.parse(cursor.getString(1)), LocalDate.parse(cursor.getString(2)), cursor.getString(3),cursor.getInt(4));
            arrayList.add(c);
        }
        cursor.close();
        return arrayList;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Persona> fetchUltimaCuota(){
        SQLiteDatabase db = this.getReadableDatabase();
        LocalDate currentDate = LocalDate.now();
        String query = "SELECT C.ClienteFK, C.Inicio, C.Fin, C.Tipo, C.Precio, P.Nombre, P.Apellido, P.DNI, P.Registro " +
                "FROM " + TABLE_NAME_CUOTAS + " C " +
                "INNER JOIN " + TABLE_NAME + " P ON C.ClienteFK = P.DNI " +
                "WHERE (C.ClienteFK, C.Fin) IN (SELECT ClienteFK, MAX(Fin) FROM " + TABLE_NAME_CUOTAS +
                " WHERE Fin <= '" + currentDate + "' GROUP BY ClienteFK)";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Persona> arrayList = new ArrayList<>();
        while(cursor.moveToNext()){
            Cuota c;
            Persona p;
            c = new Cuota(cursor.getString(0), LocalDate.parse(cursor.getString(1)), LocalDate.parse(cursor.getString(2)), cursor.getString(3),cursor.getInt(4));
            p= new Persona(cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
            p.agregarCuota(c);
            arrayList.add(p);
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
    public Boolean updateClientData(String name, String ape, String dni){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_APE, ape);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DNI = ?", new String [] {dni});
        if(cursor.getCount()>0){
            long res= db.update(TABLE_NAME,values,"DNI=?", new String[] {dni});
            if(res==-1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }
    public Boolean deleteClient(String dni){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE DNI = ?", new String [] {dni});
        if(cursor.getCount()>0){
            long res= db.delete(TABLE_NAME,"DNI=?", new String[] {dni});
            if(res==-1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }
}
