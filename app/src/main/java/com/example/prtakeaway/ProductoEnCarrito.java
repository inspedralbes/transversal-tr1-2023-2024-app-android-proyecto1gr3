package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

public class ProductoEnCarrito {
    private String nombre;
    private float precio;
    @SerializedName("cantidad")
    private int cantidad;
    @SerializedName("IDProducto")
    private int IDproducto;
    private String descripcion;

    public ProductoEnCarrito(String nombre, float precio, int cantidad, int id, String descripcion){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad =cantidad;
        this.IDproducto = id;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIDproducto() {
        return IDproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
