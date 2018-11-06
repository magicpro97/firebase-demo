package com.thefutureteam.firebase_demo.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Comment
{
    private String uid;
    private String author;
    private String text;

    public Comment()
    {
    }

    public Comment(final String uid, final String author, final String text)
    {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(final String uid)
    {
        this.uid = uid;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(final String author)
    {
        this.author = author;
    }

    public String getText()
    {
        return text;
    }

    public void setText(final String text)
    {
        this.text = text;
    }
}
