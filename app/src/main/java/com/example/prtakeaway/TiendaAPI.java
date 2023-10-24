package com.example.prtakeaway;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface TiendaAPI {
    @POST("/login")
        Call<RespuestaUsuario> login(@Body Usuario usuario);

    @GET("/getProductos")
        Call<List<Productos.Producto>> getProductos();
}
