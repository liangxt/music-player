package com.example.chens.PlaymySong.ui.settings;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.chens.PlaymySong.R;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_settingactivity);
        ExitActivity.getInstance().addActivity(this);
        SettingListFragment lstFragment = (SettingListFragment) getSupportFragmentManager().findFragmentByTag("listFragment");
        if (lstFragment == null) {
            lstFragment = new SettingListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, lstFragment, "listFragment");
            transaction.commit();
        }
    }
}
