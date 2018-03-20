package com.example.meg2tron.digitalentrypass;

/**
 * Created by meg2tron on 15/3/18.
 */

class User {
    public String username="abc",email="a@gmail",photouri="@@@",uid="12234";

    public User()
    {
        username="abc";
        email="a@gmail";
        photouri="@@@";
        uid="12234";
    }

    public User(String username,String email,String photouri,String uid)
    {
        this.username =username;
        this.email = email;
        this.photouri = photouri;
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotouri() {
        return photouri;
    }

    public void setPhotouri(String photouri) {
        this.photouri = photouri;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
