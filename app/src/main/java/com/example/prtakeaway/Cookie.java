package com.example.prtakeaway;

import com.google.gson.annotations.SerializedName;

public class Cookie {
    @SerializedName("originalMaxAge")
    public int originalMaxAge;
    @SerializedName("expires")
    public String expires;
    @SerializedName("secure")
    public Boolean secure;
    @SerializedName("httpOnly")
    public Boolean httpOnly;
    @SerializedName("path")
    public String path;
    @SerializedName("user")
    public String user;

    public Cookie(int originalMaxAge, String expires, Boolean secure, Boolean httpOnly, String path, String user) {
        this.originalMaxAge = originalMaxAge;
        this.expires = expires;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.path = path;
        this.user = user;
    }

    public int getOriginalMaxAge() {
        return originalMaxAge;
    }

    public void setOriginalMaxAge(int originalMaxAge) {
        this.originalMaxAge = originalMaxAge;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public Boolean getHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
