package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    String URL = "http://damtr1g3.dam.inspedralbes.cat:3333/"; //variable con la url a la que nos conectamos
    public Retrofit retrofit; //variable para el retrofit
Button btnLogin;
EditText etUser, etPass;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        etPass = findViewById(R.id.etPass);
        etUser = findViewById(R.id.etUser);

        //usuario :  clientest@gmail.com
        //pass: 1234

        //definimos lo que hace el boton de login
        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cogemos los valores de los campos que ha rellenado el usuario
                String user = etUser.getText().toString();
                String pass = etPass.getText().toString();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(!user.isEmpty()){
                    if(pattern.matcher(user).matches()){
                        if(!pass.isEmpty()){
                            login(user,pass);
                        }else{
                            Toast.makeText(LoginActivity.this, "Has d'introduir una contrasenya", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Introdueix un format vàlid per al correu electrònic", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Has d'introduir un correu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //funcion que comprobara si el usuario puede entrar
    public void login(String user, String pass){
        Log.d("onresponseLogin","entra al onResponse bien");

        //llamamos a retrofit
        retrofit = new Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Usuario usuario = new Usuario(user, pass); //creamos un objeto usuario

        TiendaAPI tiendaAPI = retrofit.create(TiendaAPI.class);

        Call<RespuestaUsuario> call = tiendaAPI.login(usuario); //hacemos una llamada al retrofit

        call.enqueue(new Callback<RespuestaUsuario>() {
            @Override
            public void onResponse(Call<RespuestaUsuario> call, Response<RespuestaUsuario> response) {
                if (response.isSuccessful()){
                    RespuestaUsuario respuesta = response.body();
                    Log.d("onresponse","sadasd");


                    //usamos sharedPreferences para que sean variables globales

                    editor = sharedPreferences.edit();

                    editor.putInt("id", respuesta.userData.getIdUsuario());
                    editor.putString("nombre", respuesta.userData.getNombre());
                    editor.putString("apellido",respuesta.userData.getApellido());
                    editor.putInt("idCliente",respuesta.userData.getIdUsuario());
                    editor.putString("contraseña",respuesta.userData.getContrasena());
                    editor.putString("correo", respuesta.userData.getCorreo());
                    editor.putString("direccion", respuesta.userData.getDireccion());
                    editor.putString("telefono", respuesta.userData.getTelefono());

                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);

                }else{
                   Log.d("response","response mal "+response.body());
                    Toast.makeText(LoginActivity.this, "Usuario incorrecto :c", Toast.LENGTH_SHORT).show();                }
            }

            @Override
            public void onFailure(Call<RespuestaUsuario> call, Throwable t) {
                Log.d("error onFailure", "error onFailure "+t.getMessage()+" "+t+" "+ call);
            }
        });
    }


}