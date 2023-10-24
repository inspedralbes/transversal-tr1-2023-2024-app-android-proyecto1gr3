package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Productos {
    private List<Producto>productos;

    public List<Producto>getProductos(){
        return productos;
    }

public  static class Producto{
    @SerializedName("IDProducto")
    private int idProducto;

    @SerializedName("NombreProducto")
    private String NombreProducto;

    @SerializedName("Descripcion")
    private String Descripcion;

    @SerializedName("PrecioUnitario")
    private float PrecioUnitario;

    @SerializedName("Imatge")
    private String Imatge;

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public float getPrecioUnitario() {
        return PrecioUnitario;
    }

    public String getImatge() {
        return Imatge;
    }
}
}
