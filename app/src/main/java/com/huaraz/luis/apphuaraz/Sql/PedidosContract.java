package com.huaraz.luis.apphuaraz.Sql;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class PedidosContract {

    public static abstract class PedidoEntry implements BaseColumns {
        public static final String TABLE_NAME ="pedido";

        public static final String ID = "id";
        public static final  String foto_01="foto_01";
        public static final  String foto_02="foto_02";
        public static final  String foto_03="foto_03";
        public static final  String provincia="provincia";
        public static final  String distrito="distrito";
        public static final  String id_usuario="id_usuario";
        public static final  String id_tecnico="id_tecnico";
        public static final  String fecha="fecha";
        public static final String estado="estado";
        public static final String altitud="altitud";
        public static final  String latitud="latitud";
        public static final  String temperatura="temperatura";

    }
}
