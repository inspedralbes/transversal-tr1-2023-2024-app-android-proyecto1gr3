package com.example.prtakeaway;


import com.google.gson.annotations.SerializedName;

public class RespuestaUsuario {

    @SerializedName("userData")
    public DataUser userData;

    @SerializedName("cookie")
    public Cookie cookie;

    public RespuestaUsuario(DataUser userData, Cookie cookie) {
        this.userData = userData;
        this.cookie = cookie;
    }

    public DataUser getUserData() {
        return userData;
    }

    public void setUserData(DataUser userData) {
        this.userData = userData;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}

