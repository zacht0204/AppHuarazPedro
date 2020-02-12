package com.huaraz.luis.apphuaraz.Sql;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Usuario {

    private String id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String contrasena;
    private String correo;
    private String telefono;
    private int     tipo;

    public Usuario(String id, String nombres, String apellidos, String dni, String contrasena, String correo, String telefono, int tipo) {
        this.id = UUID.randomUUID().toString();
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public Usuario(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.ID));
        nombres = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.nombres));
        apellidos = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.apellidos));
        dni = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.dni));
        contrasena = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.contrasena));
        correo = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.correo));
        telefono = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.telefono));
        tipo = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.tipo));

    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UsuarioContract.UsuarioEntry.ID, id);
        values.put(UsuarioContract.UsuarioEntry.nombres, nombres);
        values.put(UsuarioContract.UsuarioEntry.apellidos, apellidos);
        values.put(UsuarioContract.UsuarioEntry.dni, dni);
        values.put(UsuarioContract.UsuarioEntry.contrasena, contrasena);
        values.put(UsuarioContract.UsuarioEntry.correo, correo);
        values.put(UsuarioContract.UsuarioEntry.telefono, telefono);
        values.put(UsuarioContract.UsuarioEntry.tipo, tipo);

        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
}
