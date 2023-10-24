package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductesActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private String URL = "http://192.168.19.242:3000/"; //url para pedir los productos
    private RecyclerView recyclerView;
    private ProductosAdapter adapter;

    List<Productos.Producto> productos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productes);


        // Inicializa el RecyclerView y su adaptador
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Muestra 3 columnas por fila
        adapter = new ProductosAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);

        Call<List<Productos.Producto>> call = tiendaAPI.getProductos();

        call.enqueue(new Callback<List<Productos.Producto>>() {
            @Override
            public void onResponse(Call<List<Productos.Producto>> call, Response<List<Productos.Producto>> response) {
                Log.d("call","ha funcionado");
                productos = response.body();
                adapter.actualizarProductos(productos);
                for(Productos.Producto producto : productos){
                    String prueba = producto.getNombreProducto();
                    Log.d("muestra",prueba);
                }

            }

            @Override
            public void onFailure(Call<List<Productos.Producto>> call, Throwable t) {
                Log.d("error getProductos",t.getMessage());
            }
        });

    }
}