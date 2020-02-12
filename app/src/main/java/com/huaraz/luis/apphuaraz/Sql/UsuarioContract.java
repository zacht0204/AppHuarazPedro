package com.huaraz.luis.apphuaraz.Sql;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class UsuarioContract {

    public static abstract class UsuarioEntry implements BaseColumns {
        public static final String TABLE_NAME ="usuario";

        public static final String ID = "id";
        public static final  String nombres="nombres";
        public static final  String apellidos="apellidos";
        public static final  String dni="dni";
        public static final  String contrasena="contrasena";
        public static final  String correo="correo";
        public static final  String telefono="telefono";
        public static final  String tipo="tipo";


    }
}
