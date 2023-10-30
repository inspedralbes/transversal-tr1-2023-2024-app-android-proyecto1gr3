package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("email")
    private String mail;
    @SerializedName("password")
    private String password;
    public Usuario(String email, String contrasena){
        this.mail = email;
        this.password = contrasena;
    }

}
