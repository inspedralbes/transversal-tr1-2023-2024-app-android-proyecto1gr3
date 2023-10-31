package com.example.prtakeaway;

public class Order {
    private int idClient;
    private Double total;
    private String comentario;

    public Order() {
    }

    public int getIDCliente() {
        return idClient;
    }

    public void setIDCliente(int IDCliente) {
        this.idClient = IDCliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
