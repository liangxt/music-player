package com.example.chens.PlaymySong.ui.cover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.ui.settings.ExitActivity;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover_loginactivity);
        ExitActivity.getInstance().addActivity(this);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
