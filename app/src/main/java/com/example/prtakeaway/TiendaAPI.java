package com.example.prtakeaway;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface TiendaAPI {
    @POST("/login")
        Call<RespuestaUsuario> login(@Body Usuario usuario);

    @GET("/getProducts")
        Call<List<Productos.Producto>> getProductos();

    @GET("/getOrders")
    Call<List<Pedidos.Pedido>> getPedido();

    @POST("/enviarPedido")
    Call<Void> enviarPedido(@Body Pedidos.Pedido pedido);
}
