package com.thefutureteam.firebase_demo.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopPostsFragment
        extends PostListFragment
{
    @Override
    public Query getQuery(final DatabaseReference databaseReference)
    {
        // [START my_top_posts_query]
        // My top posts by number of stars
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)
                                                 .orderByChild("starCount");
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}
