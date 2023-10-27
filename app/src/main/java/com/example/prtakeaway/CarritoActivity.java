package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    TextView tvPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerView = findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        List<ProductoEnCarrito> carrito = obtenerProductosEnCarrito();

        carritoAdapter = new CarritoAdapter(carrito);
        recyclerView.setAdapter(carritoAdapter);

        for(ProductoEnCarrito producto : carrito){
            Log.d("carrito en activity: ",producto.getNombre()+" "+producto.getCantidad());
        }

        tvPrecio = findViewById(R.id.tvPrecio);

        tvPrecio.setText("Total: " + String.format("%.2f€", carritoAdapter.calcularPrecioTotal()));

        }

    private List<ProductoEnCarrito> obtenerProductosEnCarrito() {
        SharedPreferences sharedPreferences = getSharedPreferences("MiCarrito", MODE_PRIVATE);

// Recupera la lista de productos en el carrito como una cadena JSON
        String productosEnCarritoJson = sharedPreferences.getString("productoEnCarrito", null);
        List<ProductoEnCarrito> carrito = new ArrayList<>();

        if (productosEnCarritoJson != null) {
            // Convierte la cadena JSON de vuelta a la lista de productos en el carrito
            Gson gson = new Gson();
            Type type = new TypeToken<List<ProductoEnCarrito>>() {}.getType();
            carrito = gson.fromJson(productosEnCarritoJson, type);
        }

        return carrito;
    }

    public void eliminarProducto(int position) {
        carritoAdapter.eliminarProducto(position);
        actualizarPrecio();  // Actualiza el precio después de eliminar un producto
    }
    private void actualizarPrecio() {
        double precioTotal = carritoAdapter.calcularPrecioTotal();
        tvPrecio.setText("Precio Total: " + String.format("%.2f€", precioTotal));    }


}