package com.huaraz.luis.apphuaraz.Sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.huaraz.luis.apphuaraz.Sql.PedidosContract.PedidoEntry;

/**
 * Manejador de la base de datos
 */
public class PedidosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pedido.db";

    public PedidosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PedidoEntry.TABLE_NAME + " ("
                + PedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PedidoEntry.ID + " TEXT NOT NULL,"
                + PedidoEntry.foto_01 + " BLOB NOT NULL,"
                + PedidoEntry.foto_02 + " BLOB NOT NULL,"
                + PedidoEntry.foto_03 + " BLOB NOT NULL,"
                + PedidoEntry.provincia + " TEXT NOT NULL,"
                + PedidoEntry.distrito + " TEXT NOT NULL,"
                + PedidoEntry.id_usuario + " INTEGER ,"
                + PedidoEntry.id_tecnico + " INTEGER ,"
                + PedidoEntry.fecha + " TEXT,"
                + PedidoEntry.estado + " TEXT ,"
                + PedidoEntry.altitud + " TEXT ,"
                + PedidoEntry.latitud + " TEXT,"
                + PedidoEntry.	temperatura + " TEXT,"
                + "UNIQUE (" + PedidoEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
       // mockData(db);

    }



    public long mockPedido(SQLiteDatabase db, Pedido pedido) {
        return db.insert(
                PedidoEntry.TABLE_NAME,
                null,
                pedido.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveLawyer(Pedido pedido) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                PedidoEntry.TABLE_NAME,
                null,
                pedido.toContentValues());

    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        PedidoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getPedidosos() {
        Cursor c = getReadableDatabase().query(
                PedidoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        return c;
    }

    public Cursor getLawyerById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                PedidoEntry.TABLE_NAME,
                null,
                PedidoEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public int deletePedidos() {
        return getWritableDatabase().delete(
                PedidoEntry.TABLE_NAME,
                null,
                null);
    }

    public int updateLawyer(Pedido pedido, String lawyerId) {
        return getWritableDatabase().update(
                PedidoEntry.TABLE_NAME,
                pedido.toContentValues(),
                PedidoEntry.ID + " LIKE ?",
                new String[]{lawyerId}
        );
    }
}
