package com.thefutureteam.firebase_demo.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentPostsFragment
        extends PostListFragment
{
    @Override
    public Query getQuery(final DatabaseReference databaseReference)
    {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                                                  .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
