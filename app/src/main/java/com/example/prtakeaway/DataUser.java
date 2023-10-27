package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("IDCliente")
    private int idUsuario;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Apellido")
    private String apellido;

    @SerializedName("Contrasena")
    private String contrasena;

    @SerializedName("CorreoElectronico")
    private String correo;

    @SerializedName("Telefono")
    private String telefono;

    @SerializedName("Direccion")
    private  String direccion;

    public DataUser(int idUsuario, String nombre, String apellido, String contrasena, String correo, String telefono, String direccion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
