package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    List<ProductoEnCarrito> carrito = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializa el RecyclerView y su adaptador
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Muestra 3 columnas por fila
        adapter = new ProductosAdapter(new ArrayList<>(), carrito);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_carrito) {
            // Iniciar la actividad MainActivity (login)
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void afegirProducte(Productos.Producto producto){
        String nombreProducto = producto.getNombreProducto();
        for(ProductoEnCarrito productoCarrito : carrito){
            if(productoCarrito.getNombre().equals(nombreProducto)){
                productoCarrito.setCantidad(productoCarrito.getCantidad()+1);
                return;
            }
        }
        ProductoEnCarrito productoEnCarrito = new ProductoEnCarrito(producto.getNombreProducto(), producto.getPrecioUnitario(), 1);
        carrito.add(productoEnCarrito);

        for(ProductoEnCarrito productoEnCarrito1 : carrito){
            String prueba = productoEnCarrito1.getNombre() + " "+productoEnCarrito1.getCantidad();
            Log.d("productoEnCarrito", prueba);
        }
    }
}