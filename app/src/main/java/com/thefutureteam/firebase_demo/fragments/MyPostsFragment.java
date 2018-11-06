package com.thefutureteam.firebase_demo.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment
        extends PostListFragment
{
    @Override
    public Query getQuery(final DatabaseReference databaseReference)
    {
        // All my posts
        return databaseReference.child("user-posts")
                                .child(getUid());
    }
}
