package com.huaraz.luis.apphuaraz.Sql;

import android.content.ContentValues;
import android.database.Cursor;

import com.huaraz.luis.apphuaraz.Sql.PedidosContract.PedidoEntry;

import java.util.UUID;

/**
 * Entidad "abogado"
 */
public class Pedido {
    private String id;
    private String foto_01;
    private String foto_02;
    private String foto_03;
    private String provincia;
    private String 	distrito;
    private String 	id_usuario;
    private String 	id_tecnico;
    private String 	fecha;
    private String 	estado;
    private String 	latitud;
    private String 	altitud;
    private String temperatura;


    //UID.randomUUID().toString(


    public Pedido(String id, String foto_01, String foto_02, String foto_03, String provincia, String distrito, String id_usuario, String id_tecnico, String fecha, String estado, String latitud, String altitud, String temperatura) {
        this.id = UUID.randomUUID().toString();
        this.foto_01 = foto_01;
        this.foto_02 = foto_02;
        this.foto_03 = foto_03;
        this.provincia = provincia;
        this.distrito = distrito;
        this.id_usuario = id_usuario;
        this.id_tecnico = id_tecnico;
        this.fecha = fecha;
        this.estado = estado;
        this.latitud = latitud;
        this.altitud = altitud;
        this.temperatura = temperatura;

    }

    public Pedido(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(PedidoEntry.ID));
        foto_01 = cursor.getString(cursor.getColumnIndex(PedidoEntry.foto_01));
        foto_02 = cursor.getString(cursor.getColumnIndex(PedidoEntry.foto_02));
        foto_03 = cursor.getString(cursor.getColumnIndex(PedidoEntry.foto_03));
        distrito = cursor.getString(cursor.getColumnIndex(PedidoEntry.distrito));
        provincia = cursor.getString(cursor.getColumnIndex(PedidoEntry.provincia));
        id_usuario = cursor.getString(cursor.getColumnIndex(PedidoEntry.id_usuario));
        id_tecnico = cursor.getString(cursor.getColumnIndex(PedidoEntry.id_tecnico));
        fecha = cursor.getString(cursor.getColumnIndex(PedidoEntry.fecha));
        estado = cursor.getString(cursor.getColumnIndex(PedidoEntry.estado));
        altitud = cursor.getString(cursor.getColumnIndex(PedidoEntry.altitud));
        latitud = cursor.getString(cursor.getColumnIndex(PedidoEntry.latitud));
        temperatura = cursor.getString(cursor.getColumnIndex(PedidoEntry.temperatura));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PedidoEntry.ID, id);
        values.put(PedidoEntry.foto_01, foto_01);
        values.put(PedidoEntry.foto_02, foto_02);
        values.put(PedidoEntry.foto_03, foto_03);
        values.put(PedidoEntry.provincia, provincia);
        values.put(PedidoEntry.distrito, distrito);
        values.put(PedidoEntry.id_usuario, id_usuario);
        values.put(PedidoEntry.id_tecnico, id_tecnico);
        values.put(PedidoEntry.fecha, fecha);
        values.put(PedidoEntry.estado, estado);
        values.put(PedidoEntry.altitud, altitud);
        values.put(PedidoEntry.latitud, latitud);
        values.put(PedidoEntry.temperatura, temperatura);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto_01() {
        return foto_01;
    }

    public void setFoto_01(String foto_01) {
        this.foto_01 = foto_01;
    }

    public String getFoto_02() {
        return foto_02;
    }

    public void setFoto_02(String foto_02) {
        this.foto_02 = foto_02;
    }

    public String getFoto_03() {
        return foto_03;
    }

    public void setFoto_03(String foto_03) {
        this.foto_03 = foto_03;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(String id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}
