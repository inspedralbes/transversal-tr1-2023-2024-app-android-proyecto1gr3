package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    //variables para cada tv en los que apareceran los datos
    TextView tvNombre, tvApellido, tvCorreo, tvDireccion, tvTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //vinculamos con las id
        tvNombre = findViewById(R.id.tvNombre);
        tvApellido = findViewById(R.id.tvApellido);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvTelefono = findViewById(R.id.tvTelefono);

        //cogemos de los sharedPreferences los datos del usuario
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        //los agregamos a los tv
        tvNombre.setText(sharedPreferences.getString("nombre",""));
        tvApellido.setText(sharedPreferences.getString("apellido",""));
        tvCorreo.setText(sharedPreferences.getString("correo",""));
        tvDireccion.setText(sharedPreferences.getString("direccion",""));
        tvTelefono.setText(sharedPreferences.getString("telefono",""));


    }
}