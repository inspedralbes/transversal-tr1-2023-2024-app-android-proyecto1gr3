package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private String URLSocket = "ws://socket.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CustomAdapter(pedidos); // data es tu conjunto de datos
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.16.131:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Log.d("muestra","aqui");
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido();
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

                actualizarPedidos();
            }
        });
        mSocket.on("Rebutjada", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos();
            }
        });
        mSocket.on("Completada", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                actualizarPedidos();
            }
        });

    }

    public void actualizarPedidos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.16.131:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);
        Log.d("muestra","aqui");
        Call<List<Pedidos.Pedido>> call = tiendaAPI.getPedido();
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