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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView; //recycler view para los productos
    private CarritoAdapter carritoAdapter;
    TextView tvPrecio;
    Button btnConfirmar;
    EditText etComentario;
    String BASEURL = "http://damtr1g3.dam.inspedralbes.cat:3333/";

    List<ProductoEnCarrito> carrito;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        etComentario = findViewById(R.id.etComentario);
        carrito = obtenerProductosEnCarrito();
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        id = sharedPreferences.getInt("id",-1);

        //iniciamos y editamos el recycler view con el adaptador
        recyclerView = findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //obtenemos los productos que están en el carrito

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

            double total = carritoAdapter.calcularPrecioTotal();

            //aqui haremos el post y tal
            Order pedido = new Order();

            //pedido.setIDCliente(sharedPreferences.getInt("id",-1));
            pedido.setIDCliente(id);
            pedido.setComentario(etComentario.getText().toString());
            pedido.setTotal(total);

            enviarPedido(pedido);


        });

        Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(view -> {
            dialog.dismiss(); // Cierra el cuadro de diálogo
        });

        dialog.show();
    }

    private void enviarPedido(Order pedido) {
        // Usar Retrofit para realizar la solicitud POST con el objeto Pedido
        // Debes configurar tu interfaz de servicio para manejar esta solicitud
        // Aquí está un ejemplo simplificado de cómo podrías hacerlo:

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);

        Call<Void> call = tiendaAPI.enviarPedido(pedido, carrito); // Debes crear esta función en tu interfaz

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CarritoActivity.this, "Pedido realizado con exito!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CarritoActivity.this, "Hubo un error en tu pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("error onFailure",t.getMessage());
            }
        });

        /*
        List<ProductoEnviar> productosEnviar = new ArrayList<>();
        for (ProductoEnCarrito producto : carrito){
            int idProducto = producto.getIDproducto();
            int cantidad = producto.getCantidad();
            productosEnviar.add(new ProductoEnviar(idProducto, cantidad));
        }

        PedidoEnviar pedido1  = new PedidoEnviar();
        pedido1.setProductosEnCarrito(productosEnviar);
*/
    }
}