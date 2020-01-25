
package com.huaraz.luis.apphuaraz.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

@SerializedName("id_usuario")
@Expose
private int id_usuario;

@SerializedName("nombres")
@Expose
private String nombres;

@SerializedName("apellidos")
@Expose
private String apellidos;

@SerializedName("dni")
@Expose
private String dni;

@SerializedName("contrasena")
@Expose
private String contrasena;

@SerializedName("correo")
@Expose
private String correo;

@SerializedName("telefono")
@Expose
private String telefono;


@SerializedName("tipo")
@Expose
private int tipo;


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }



}
