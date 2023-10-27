package com.example.prtakeaway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView; //recycler view para los productos
    private CarritoAdapter carritoAdapter;
    TextView tvPrecio;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        //iniciamos y editamos el recycler view con el adaptador
        recyclerView = findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //obtenemos los productos que están en el carrito
        List<ProductoEnCarrito> carrito = obtenerProductosEnCarrito();

        carritoAdapter = new CarritoAdapter(carrito);
        recyclerView.setAdapter(carritoAdapter);

        tvPrecio = findViewById(R.id.tvPrecio);

        //añadimos en un textView el total de los productos que hay en el carrito
        tvPrecio.setText("Total: " + String.format("%.2f€", carritoAdapter.calcularPrecioTotal()));

        //boton para que salga la confirmacion de compra
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });


        }

        //funcion que obtiene del sharedprefreferences el carrito
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

    //funcion para eliminar un producto del carrito
    public void eliminarProducto(int position) {
        carritoAdapter.eliminarProducto(position);
        actualizarPrecio();  // Actualiza el precio después de eliminar un producto
    }
    //funcion para que se actualice el precio
    private void actualizarPrecio() {
        double precioTotal = carritoAdapter.calcularPrecioTotal();
        tvPrecio.setText("Precio Total: " + String.format("%.2f€", precioTotal));    }

    //funcion para mostrar el cuadro de confirmacion
    private void showConfirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm, null);

        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();

        TextView message = dialogView.findViewById(R.id.dialog_message);
        message.setText("¿Está seguro de que desea confirmar la compra?");

        Button confirmButton = dialogView.findViewById(R.id.dialog_confirm_button);
        confirmButton.setOnClickListener(view -> {
            // Realiza la lógica de confirmación de la compra aquí
            dialog.dismiss(); // Cierra el cuadro de diálogo

            //aqui haremos el post y tal
        });

        Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(view -> {
            dialog.dismiss(); // Cierra el cuadro de diálogo
        });

        dialog.show();
    }



}