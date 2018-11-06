package com.thefutureteam.firebase_demo.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User
{
    private String username;
    private String email;

    public User()
    {
    }

    public User(final String username, final String email)
    {
        this.username = username;
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(final String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(final String email)
    {
        this.email = email;
    }
}
