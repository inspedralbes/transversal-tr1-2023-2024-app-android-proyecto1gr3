package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstadoActivity extends AppCompatActivity {

    List<Pedidos.Pedido> pedidos = new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    private Socket mSocket;
    private String URLSocket = "http://dam.inspedralbes.cat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        int idParaEstado = sharedPreferences.getInt("id",0);
        Log.d("pruebaEstado", ""+idParaEstado);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CustomAdapter(pedidos, idParaEstado); // data es tu conjunto de datos
        //adapter.filtrarPedidosPorUsuario();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damtr1g3.dam.inspedralbes.cat:3333/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Log.d("muestra","aqui");
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido(idParaEstado);
        call.enqueue(new Callback<List<Pedidos.Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedidos.Pedido>> call, Response<List<Pedidos.Pedido>> response) {
                if (response.isSuccessful()) {
                    Log.d("muestra", "ha funcionado");
                    pedidos = response.body();
                    adapter.actualizarPedidos(pedidos);
                    for (Pedidos.Pedido pedido : pedidos) {
                        int prueba = pedido.getIDPedido();
                        String comentario = pedido.getComentario();
                        String estado = pedido.getEstado();

                        Log.d("muestra", String.valueOf(prueba));
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

    public void actualizarPedidos(int idParaEstado){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://damtr1g3.dam.inspedralbes.cat:3333/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Log.d("muestra","aqui");
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido(idParaEstado);
        call.enqueue(new Callback<List<Pedidos.Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedidos.Pedido>> call, Response<List<Pedidos.Pedido>> response) {
                if (response.isSuccessful()) {
                    Log.d("muestra", "ha funcionado");
                    pedidos = response.body();
                    adapter.actualizarPedidos(pedidos);
                    for (Pedidos.Pedido pedido : pedidos) {
                        int prueba = pedido.getIDPedido();
                        String comentario = pedido.getComentario();
                        String estado = pedido.getEstado();

                        Log.d("muestra", String.valueOf(prueba));
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