package com.example.chens.PlaymySong.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.entities.Song;

import java.util.ArrayList;


/**
 * Activity for recent played list.
 */
public class RecentlyPlayedActivity extends AppCompatActivity {
    protected GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_recentlyplayedactivity);
        ExitActivity.getInstance().addActivity(this);
        ImageButton imageButton = (ImageButton) findViewById(R.id.rpbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecentlyPlayedActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        mGestureDetector = new GestureDetector(RecentlyPlayedActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getRawX() - e1.getRawX() > 200) {
                    finish();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        TextView song_information_text = (TextView) findViewById(R.id.rpltxt);
        String song_infromation = getSongs();
        if (song_infromation == null || song_infromation.length() == 0) {
            new AlertDialog.Builder(RecentlyPlayedActivity.this)
                    .setMessage("No Recent Played List information. Try to explore music world! ")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RecentlyPlayedActivity.this, SettingActivity.class);
                                    startActivity(intent);
                                }
                            }).create().show();
        } else {
            song_information_text.setText(song_infromation);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private String getSongs() {
        ArrayList<Song> songlist;
        MyLocalDB database = new MyLocalDB(RecentlyPlayedActivity.this);
        songlist = database.getRecentPlayListAll();
        if (songlist == null || songlist.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < songlist.size(); i++) {
            Song song = songlist.get(i);
            if (song != null) {
                stringBuilder.append(song.getTitle());
                stringBuilder.append("  ");
                stringBuilder.append(song.getArtist());
                stringBuilder.append("  ");
                if (song.getAlbum() == null) {
                    stringBuilder.append("  ");
                } else {
                    stringBuilder.append(song.getAlbum());

                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
