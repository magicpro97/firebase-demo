package com.thefutureteam.firebase_demo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.thefutureteam.firebase_demo.fragments.MyPostsFragment;
import com.thefutureteam.firebase_demo.fragments.MyTopPostsFragment;
import com.thefutureteam.firebase_demo.fragments.RecentPostsFragment;

public class MainActivity
        extends BaseActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            private final Fragment[] mFragments = new Fragment[] {
                    new RecentPostsFragment(),
                    new MyPostsFragment(),
                    new MyTopPostsFragment(),
                    };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
                    getString(R.string.heading_my_posts),
                    getString(R.string.heading_my_top_posts)
            };

            @Override
            public Fragment getItem(final int position)
            {
                return mFragments[position];
            }

            @Override
            public int getCount()
            {
                return mFragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(final int position)
            {
                return mFragmentNames[position];
            }
        };

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        findViewById(R.id.fabNewPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.log_out: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LogInActivity.class));
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
