package com.example.chens.PlaymySong.ws.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.entities.Song;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Songze Chen on 2016/4/14.
 */
public class RemoteService extends Service {
    private final String urlString = "remote server url";
    private Toast toast;
    private static final String TAG = "MyRemoteService";
    private MyLocalDB myLocalDB = new MyLocalDB(this);
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate() called");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy() called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // do something
        return null;
    }

    /**
     * add songs to the server database
     * @param userID user ID
     * @param listName the list's name
     * @param titleSingerAlbum songs added by the user
     */
    public void addToList(String userID, String listName, ArrayList<Song> titleSingerAlbum) {

        try {
            for (Song song : titleSingerAlbum) {
                StringBuilder builder = new StringBuilder(urlString);
                builder.append("/add?");
                builder.append("userID=" + userID);
                builder.append("&listName=" + listName);
                builder.append("&title=" + song.getTitle());
                builder.append("&singer=" + song.getArtist());
                builder.append("&album=" + song.getAlbum());

                URL url = new URL(builder.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                if (responseCode == 200) {
                    sendToast("success");
                } else {
                    sendToast("fail");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete songs from server database
     * @param userID user ID
     * @param listName the list's name
     * @param titleSingerAlbum the song deleted by the user
     */
    public void deleteFromList(String userID, String listName, ArrayList<Song> titleSingerAlbum) {

        try {
            for (Song song : titleSingerAlbum) {
                StringBuilder builder = new StringBuilder(urlString);
                builder.append("/delete?");
                builder.append("userID=" + userID);
                builder.append("&listName=" + listName);
                builder.append("&title=" + song.getTitle());
                builder.append("&singer=" + song.getArtist());

                URL url = new URL(builder.toString());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();

                if (responseCode == 200) {
                    sendToast("success");
                } else {
                    sendToast("fail");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * toast
     * @param s information
     */
    private void sendToast(String s) {

        if (toast == null){
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        }

        toast.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}
