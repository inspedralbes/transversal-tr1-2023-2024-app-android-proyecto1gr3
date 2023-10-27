package com.example.prtakeaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    //variables para los botones del menu
    Button btnProductos, btnEstado, btnMiPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //vinculamos los botones
        btnProductos = findViewById(R.id.btnVerProductos);
        btnEstado = findViewById(R.id.btnEstado);
        btnMiPerfil = findViewById(R.id.btnMiPerfil);

        //agregamos a cada boton el intent para ir a cada pantalla
        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProductesActivity.class);
                startActivity(intent);
            }
        });

        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EstadoActivity.class);
                startActivity(intent);
            }
        });
        btnMiPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }



}