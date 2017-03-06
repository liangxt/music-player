package com.example.chens.PlaymySong.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chens.PlaymySong.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Activity for help document.
 */
public class HelpActivity extends Activity {
    protected GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_helpactivity);
        ExitActivity.getInstance().addActivity(this);
        Button button = (Button) findViewById(R.id.helpbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGestureDetector = new GestureDetector(HelpActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getRawX() - e1.getRawX() > 200) {
                    Intent intent = new Intent(HelpActivity.this, SettingActivity.class);
                    startActivity(intent);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        TextView help = (TextView) findViewById(R.id.help_document);
        String help_string = getDocument();
        if (help_string != null) {
            help.setText(help_string);
        }
    }

    private String getDocument() {
        InputStream inputStream = getResources().openRawResource(R.raw.help_document);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        if (inputStreamReader == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
