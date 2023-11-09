package com.example.prtakeaway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstadoActivity extends AppCompatActivity implements CustomAdapter.OnPedidoItemClickListener{

    List<Pedidos.Pedido> pedidos = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    List<ProductosComanda.ProductoComanda> productoComandas = new ArrayList<>();

    HashMap<Integer, String> ProductosxComanda = new HashMap<Integer, String>();
    private String ProdxCom = "";
    private  double Total =0;
    private int idP = 0;

    private Socket mSocket;
    private String URLSocket = "http://dam.inspedralbes.cat:3333";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        int idParaEstado = sharedPreferences.getInt("id",0);
        Log.d("pruebaEstado", ""+idParaEstado);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CustomAdapter(pedidos, idParaEstado, this); // data es tu conjunto de datos
        //adapter.filtrarPedidosPorUsuario();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damtr1g3.dam.inspedralbes.cat:3333/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido(idParaEstado);
        call.enqueue(new Callback<List<Pedidos.Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedidos.Pedido>> call, Response<List<Pedidos.Pedido>> response) {
                if (response.isSuccessful()) {
                    pedidos = response.body();
                    adapter.actualizarPedidos(pedidos);
                    for (Pedidos.Pedido pedido : pedidos) {
                        int prueba = pedido.getIDPedido();
                        String comentario = pedido.getComentario();
                        String estado = pedido.getEstado();
                    }
                }else{
                    Log.d("FALLO","fallo");
                }
            }

            @Override
            public void onFailure(Call<List<Pedidos.Pedido>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("FALLO2", ""+t);
            }

        });


        try{
            mSocket = IO.socket(URLSocket);
            Log.d("connect","inicio el socket");

        }catch (URISyntaxException e){
            Log.d("error",e.toString());
        }
        mSocket.connect(); //nos conectamos al socket
        Log.d("connect","el connect ha salido bien"); //mensaje para ver que nos hemos conectado bien

        mSocket.on("Acceptada", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos(idParaEstado);
            }
        });
        mSocket.on("Rebutjada", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos(idParaEstado);
            }
        });
        mSocket.on("Llesta", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos(idParaEstado);
            }
        });
        mSocket.on("Entregada", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos(idParaEstado);
            }
        });
        mSocket.on("comandaNova", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos(idParaEstado);
            }
        });

    }

    @Override
    public void onPedidoItemClick(Pedidos.Pedido productoComanda) {
        Log.d("Funciona", "Si");
        mostrarDialogoDetallesPedido(productoComanda);
    }
    public void mostrarDialogoDetallesPedido(Pedidos.Pedido pedido) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles del Pedido");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_detalles_pedido, null);
        builder.setView(dialogView);

        // Encuentra las TextViews por sus IDs
        TextView textViewNumeroPedido = dialogView.findViewById(R.id.textViewNumeroPedido);
        TextView textViewFechaPedido = dialogView.findViewById(R.id.textViewFechaPedido);
        TextView textViewComida = dialogView.findViewById(R.id.textViewContenido);

        // Establece el texto en las TextViews con la información del pedido
        textViewNumeroPedido.setText("Número de Pedido: " + String.valueOf(pedido.getIDPedido()));
        textViewFechaPedido.setText("Fecha: "+pedido.getFechaPedido().replace("T","  ").replace("Z","").substring(0,17)+"\n");
        textViewComida.setText(ProductosxComanda.get(pedido.getIDPedido()));

        // Agrega más TextViews y establece su texto según los detalles del pedido

        builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void actualizarPedidos(int idParaEstado){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damtr1g3.dam.inspedralbes.cat:3333/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido(idParaEstado);
        call.enqueue(new Callback<List<Pedidos.Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedidos.Pedido>> call, Response<List<Pedidos.Pedido>> response) {
                if (response.isSuccessful()) {
                    pedidos = response.body();
                    adapter.actualizarPedidos(pedidos);
                    for (Pedidos.Pedido pedido : pedidos) {
                        int prueba = pedido.getIDPedido();
                        String comentario = pedido.getComentario();
                        String estado = pedido.getEstado();

                    }
                }else{
                    Log.d("FALLO","fallo");
                }
            }

            @Override
            public void onFailure(Call<List<Pedidos.Pedido>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
                Log.d("FALLO2", ""+t);
            }

        });
    }
}