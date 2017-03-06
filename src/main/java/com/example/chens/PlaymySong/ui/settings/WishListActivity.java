package com.example.chens.PlaymySong.ui.settings;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.chens.PlaymySong.R;


/**
 * Activity for wish list.
 */
public class WishListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_wishlistactivity);
        ExitActivity.getInstance().addActivity(this);


        ListView listView = (ListView) findViewById(android.R.id.list);
//        View headerView = getLayoutInflater().inflate(R.layout.header_layout, listView, false);
//        listView.addHeaderView(headerView);
//        ImageButton button = (ImageButton) findViewById(R.id.backbutton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        WishListFragment wishListFragment = (WishListFragment) getSupportFragmentManager().findFragmentByTag("wishlistFragment");
        if (wishListFragment == null) {
            wishListFragment = new WishListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, wishListFragment, "wishlistFragment");
            transaction.commit();
        }
    }
}
