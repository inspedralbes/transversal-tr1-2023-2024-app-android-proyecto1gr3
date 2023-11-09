package com.example.prtakeaway;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductosComanda {
    private List<ProductosComanda.ProductoComanda> productos;

    public List<ProductosComanda.ProductoComanda>getProductosC(){
        return productos;
    }

    public static  class ProductoComanda {
        @SerializedName("IDPedido")
        private int IDPedido;
        @SerializedName("IDCliente")
        private int IDCliente;
        @SerializedName("NombreProducto")
        private String NombreProducto;
        @SerializedName("Comentario")
        private String Comentario;
        @SerializedName("PrecioUnitario")
        private double PrecioUnitario;
        @SerializedName("Cantidad")
        private int Cantidad;

        // Getters
        public int getIDPedido() {
            return IDPedido;
        }

        public int getIDCliente() {
            return IDCliente;
        }

        public String getNombreProducto() {
            return NombreProducto;
        }

        public String getComentario() {
            return Comentario;
        }

        public double getPrecioUnitario() {
            return PrecioUnitario;
        }

        public int getCantidad() {
            return Cantidad;
        }
    }}
