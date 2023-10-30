package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pedidos {
    private List<Pedido> pedidos;

    public List<Pedido>getPedidos(){
        return pedidos;
    }


    public  static class Pedido {
        @SerializedName("IDPedido")
        private int IDPedido;
        @SerializedName("IDCliente")
        private int IDCliente;
        @SerializedName("FechaPedido")
        private String FechaPedido;
        @SerializedName("Total")
        private double Total;
        @SerializedName("Estado")
        private String Estado;
        @SerializedName("Comentario")
        private String Comentario;

        public int getIDPedido() {
            return IDPedido;
        }

        public int getIDCliente() {
            return IDCliente;
        }

        public String getFechaPedido() {
            return FechaPedido;
        }


        public double getTotal() {
            return Total;
        }


        public String getEstado() {
            return Estado;
        }


        public String getComentario() {
            return Comentario;
        }
    }
    }


