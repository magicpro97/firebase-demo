package com.thefutureteam.firebase_demo.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post
{
    private String uid;
    private String author;
    private String title;
    private String body;
    private int starCount = 0;
    private Map<String, Boolean> stars = new HashMap<>();

    public Post()
    {
    }

    public Post(final String uid, final String author, final String title, final String body)
    {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(final String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(final String body)
    {
        this.body = body;
    }

    public int getStarCount()
    {
        return starCount;
    }

    public void setStarCount(final int starCount)
    {
        this.starCount = starCount;
    }

    public Map<String, Boolean> getStars()
    {
        return stars;
    }

    public void setStars(final Map<String, Boolean> stars)
    {
        this.stars = stars;
    }
}
