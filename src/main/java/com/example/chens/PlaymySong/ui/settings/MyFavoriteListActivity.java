package com.example.chens.PlaymySong.ui.settings;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.chens.PlaymySong.R;


/**
 * Activity for my favorite list.
 */
public class MyFavoriteListActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_myfavoritelistactivity);
        ExitActivity.getInstance().addActivity(this);

        FavoriteListFragment favoriteListFragment=(FavoriteListFragment)getSupportFragmentManager().findFragmentByTag("favoritelistfragment");
        if (favoriteListFragment==null){
            favoriteListFragment=new FavoriteListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, favoriteListFragment, "favoritelistfragment");
            transaction.commit();
        }
    }
}
