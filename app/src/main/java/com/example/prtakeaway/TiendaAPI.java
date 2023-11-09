package com.example.prtakeaway;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TiendaAPI {
    @POST("/login")
        Call<RespuestaUsuario> login(@Body Usuario usuario);

    @GET("/getProducts")
        Call<List<Productos.Producto>> getProductos();

    @GET("/getOrdersClient/{id}")
    Call<List<Pedidos.Pedido>> getPedido(@Path("id")int id);
    @GET("/detailOrder/{id}")
    Call<List<ProductosComanda.ProductoComanda>> getProductComanda(@Path("id")int id);

    @POST("/createOrder")
    Call<Void> enviarPedido(@Body PedidoContainer pedidoContainer);}
