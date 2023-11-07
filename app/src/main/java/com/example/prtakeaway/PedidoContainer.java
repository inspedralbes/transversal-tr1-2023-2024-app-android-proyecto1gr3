package com.example.prtakeaway;

import java.util.List;

public class PedidoContainer {
    private Order pedido;
    private List<ProductoEnCarrito> productos;

    public PedidoContainer(Order pedido, List<ProductoEnCarrito> productos) {
        this.pedido = pedido;
        this.productos = productos;
    }
}
