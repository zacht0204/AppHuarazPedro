
package com.huaraz.luis.apphuaraz.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pedidos {

    @SerializedName("id_pedido")
    @Expose
    private Integer id_demo;

    @SerializedName("foto_01")
    @Expose
    private String id_foto1;

    @SerializedName("foto_02")
    @Expose
    private String id_foto2;

    @SerializedName("foto_03")
    @Expose
    private String id_foto3;

    @SerializedName("provincia")
    @Expose
    private String provincia;

    @SerializedName("distrito")
    @Expose
    private String distrito;

    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;

    @SerializedName("id_tecnico")
    @Expose
    private int id_tecnico;

    @SerializedName("fecha")
    @Expose
    private Date fecha;

    @SerializedName("estado")
    @Expose
    private int estado;

    @SerializedName("altitud")
    @Expose
    private String altitud;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("temperatura")
    @Expose
    private String temperatura;

    public Integer getId_demo() {
        return id_demo;
    }

    public void setId_demo(Integer id_demo) {
        this.id_demo = id_demo;
    }

    public String getId_foto1() {
        return id_foto1;
    }

    public void setId_foto1(String id_foto1) {
        this.id_foto1 = id_foto1;
    }

    public String getId_foto2() {
        return id_foto2;
    }

    public void setId_foto2(String id_foto2) {
        this.id_foto2 = id_foto2;
    }

    public String getId_foto3() {
        return id_foto3;
    }

    public void setId_foto3(String id_foto3) {
        this.id_foto3 = id_foto3;
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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}


