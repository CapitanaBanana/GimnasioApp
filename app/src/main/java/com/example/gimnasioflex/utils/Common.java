package com.example.gimnasioflex.utils;

public class Common {
    //Tabla clientes
    public static final String DATABASE_NAME = "GimnasioFlex";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Clientes";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "Nombre";
    public static final String KEY_APE = "Apellido";
    public static final String KEY_DNI = "DNI";
    public static final String KEY_REGISTRO = "Registro";

    //Tabla asistencia
    public static final String TABLE_NAME_ASISTENCIA = "Asistencia";
    public static final String KEY_FECHA= "Fecha";
    public static final String KEY_CLIENTEFK = "ClienteFK";

    //Tabla cuotas
    public static final String TABLE_NAME_CUOTAS = "Cuotas";
    public static final String KEY_INICIO= "Inicio";
    public static final String KEY_FIN= "Fin";
    public static final String KEY_TIPO= "Tipo";
    public static final String KEY_PRECIO= "Precio";

    public static int precioLibre= 1000;
    public static int precioTresDias= 500;
    public static int precioDosDias= 200;

    public static int precioDeTipo(String tipo){
        if (tipo.equals("Libre")){
            return precioLibre;
        }
        if (tipo.equals("Dos Dias")){
            return precioDosDias;
        }
        if (tipo.equals("Tres Dias")){
            return precioTresDias;
        }
        else return 0;
    }

}
