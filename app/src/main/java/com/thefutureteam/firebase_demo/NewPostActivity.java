package com.thefutureteam.firebase_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thefutureteam.firebase_demo.models.Post;
import com.thefutureteam.firebase_demo.models.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity
        extends BaseActivity
{
    private static final String TAG = NewPostActivity.class.getSimpleName();
    private static final String REQUIRED = "required";

    private DatabaseReference mDataBase;

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost()
    {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDataBase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);

                if (user == null) {
                    Log.e(TAG, "onDataChange: User " + userId + " is unexpectedly null");
                    Toast.makeText(NewPostActivity.this, "Error: could not fetch user", Toast.LENGTH_SHORT).show();
                } else {
                    writeNewPost(userId, user.getUsername(), title, body);
                }

                setEditingEnabled(true);
                finish();
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError)
            {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
                setEditingEnabled(true);
            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if(enabled) {
            mSubmitButton.show();
        } else {
            mSubmitButton.hide();
        }
    }

    private void writeNewPost(String userId, String username, String title, String body) {
        String key = mDataBase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/"  + key, postValues);

        mDataBase.updateChildren(childUpdates);
    }
}
