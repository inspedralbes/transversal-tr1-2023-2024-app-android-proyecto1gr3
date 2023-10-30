package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductesActivity extends AppCompatActivity {

    private Retrofit retrofit; //variable para el retrofit
    private String URL = "http://192.168.18.251:3000/"; //url para pedir los productos
    private RecyclerView recyclerView;
    private ProductosAdapter adapter;
    Button btnTodo, btnMenos2, btnMenos5;

    List<Productos.Producto> productos = new ArrayList<>(); //array donde estarán todos los productos
    List<ProductoEnCarrito> carrito = new ArrayList<>(); //array para los productos seleccionados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productes);
        //activamos poder ir atrás con el menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTodo = findViewById(R.id.btnTodo);
        btnMenos2 = findViewById(R.id.btnMenor2);
        btnMenos5 = findViewById(R.id.btnMenor5);


        // Inicializa el RecyclerView y su adaptador
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Muestra 3 columnas por fila
        adapter = new ProductosAdapter(new ArrayList<>(), carrito);
        recyclerView.setAdapter(adapter);

        //iniciamos el retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //hacemos el get de los productos
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);

        Call<List<Productos.Producto>> call = tiendaAPI.getProductos();

        call.enqueue(new Callback<List<Productos.Producto>>() {
            @Override
            public void onResponse(Call<List<Productos.Producto>> call, Response<List<Productos.Producto>> response) {

                productos = response.body();
                adapter.actualizarProductos(productos);
                for(Productos.Producto producto : productos){
                    String prueba = producto.getNombreProducto();
                    //Log.d("muestra",prueba);
                }

            }

            @Override
            public void onFailure(Call<List<Productos.Producto>> call, Throwable t) {
                Log.d("error getProductos",t.getMessage());
            }
        });


        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.actualizarProductos(productos);
            }
        });
        btnMenos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filtrarProductosPorPrecio(2.0);

            }
        });
        btnMenos5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.filtrarProductosPorPrecio(5.0);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //funcion para que al darle al boton del carrito guardemos la array en los sharedprefrencces
    // y el intent para ir a la activity del carrito
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_carrito) {
            SharedPreferences sharedPreferences = getSharedPreferences("MiCarrito", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();
            String CarritoJson = gson.toJson(carrito);
            Log.d("pruebaJSON", CarritoJson);

            editor.putString("productoEnCarrito", CarritoJson);
            editor.apply();

            // Iniciar la actividad MainActivity (login)
            Intent intent = new Intent(this, CarritoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    en esta funcion, cuando queremos agregar un producto, primero comprobamos si ya existe para
    agregar uno a la cantidad de ese producto, y sino lo creamos y agregamos al carrito
     */
    public void afegirProducte(Productos.Producto producto){
        String nombreProducto = producto.getNombreProducto();
        for(ProductoEnCarrito productoCarrito : carrito){
            if(productoCarrito.getNombre().equals(nombreProducto)){
                productoCarrito.setCantidad(productoCarrito.getCantidad()+1);
                Toast.makeText(this, nombreProducto+" añadido al carrito!", Toast.LENGTH_SHORT).show();

                return;
            }
        }
        ProductoEnCarrito productoEnCarrito = new ProductoEnCarrito(producto.getNombreProducto(), producto.getPrecioUnitario(), 1, producto.getIdProducto(), producto.getDescripcion());
        carrito.add(productoEnCarrito);

        Toast.makeText(this, nombreProducto+" añadido al carrito!", Toast.LENGTH_SHORT).show();

        for(ProductoEnCarrito productoEnCarrito1 : carrito){
            String prueba = productoEnCarrito1.getNombre() + " "+productoEnCarrito1.getCantidad();
            Log.d("productoEnCarrito", prueba);
        }


    }
}