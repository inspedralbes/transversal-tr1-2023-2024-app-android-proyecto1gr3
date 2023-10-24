package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductesActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String URL = ""; //url para pedir los productos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productes);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);

        Call<List<Productos.Producto>> call = tiendaAPI.getProductos();

        call.enqueue(new Callback<List<Productos.Producto>>() {
            @Override
            public void onResponse(Call<List<Productos.Producto>> call, Response<List<Productos.Producto>> response) {

            }

            @Override
            public void onFailure(Call<List<Productos.Producto>> call, Throwable t) {
                Log.d("error getProductos",t.getMessage());
            }
        });

    }
}