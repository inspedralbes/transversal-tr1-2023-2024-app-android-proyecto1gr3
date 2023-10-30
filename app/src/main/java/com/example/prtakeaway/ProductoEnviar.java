package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

public class ProductoEnviar {
    @SerializedName("idProducto")
    private int idProducto;

    @SerializedName("cantidad")
    private int cantidad;

    public ProductoEnviar(int idProducto, int cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
}
