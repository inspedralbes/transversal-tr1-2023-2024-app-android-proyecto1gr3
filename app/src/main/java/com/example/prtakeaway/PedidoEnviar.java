package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PedidoEnviar {
    @SerializedName("productosEnCarrito")
    private List<ProductoEnviar> productosEnCarrito;

    public List<ProductoEnviar> getProductosEnCarrito() {
        return productosEnCarrito;
    }

    public void setProductosEnCarrito(List<ProductoEnviar> productosEnCarrito) {
        this.productosEnCarrito = productosEnCarrito;
    }
}
