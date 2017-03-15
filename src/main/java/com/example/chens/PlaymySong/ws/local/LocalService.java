package com.example.chens.PlaymySong.ws.local;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ws.ListName;

import java.util.ArrayList;

public class LocalService extends Service {
    private MyLocalDB myLocalDB = new MyLocalDB(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();
    }

    class ServiceWorker implements Runnable {
        public void run() {
            // do background processing here...
            // stop the service when done...
            // BackgroundService.this.stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // do something like playing music

        return null;
    }

    /**
     * add several songs to the specified list
     * @param listName list's name
     * @param titleSingerAlbum an array of songs
     */
    public void addList(String listName, ArrayList<Song> titleSingerAlbum) {
        switch (listName) {
            case ListName.FAVORITELIST:
//                for (Song song : titleSingerAlbum) {
//                    myLocalDB.addToFavoriteList(song);
//                }
                break;
            case ListName.PLAYLIST:
                for (Song song : titleSingerAlbum) {
                    myLocalDB.addToPlayList(song);
                }
                break;
            case ListName.RECENTPLAYLIST:
                for (Song song : titleSingerAlbum) {
                    myLocalDB.addToRecentPlayList(song);
                }
                break;
            case ListName.WISHLIST:
                for (Song song : titleSingerAlbum) {
                    myLocalDB.addToWishList(song);
                }
                break;
        }
    }

    /**
     * delete one song from the specified list
     * @param listName list's name
     * @param song the song deleted by the user
     */
    public void deleteFromList(String listName, Song song) {
        switch (listName) {
            case ListName.FAVORITELIST:
//                myLocalDB.deleteFromFavoriteList(song.getTitle(), song.getArtist());
                break;
            case ListName.PLAYLIST:
                myLocalDB.deleteFromPlayList(song.getTitle(), song.getArtist());
                break;
            case ListName.RECENTPLAYLIST:
                myLocalDB.deleteFromRecentPlayList(song.getTitle(), song.getArtist());
                break;
            case ListName.WISHLIST:
                myLocalDB.deleteFromWishList(song.getTitle(), song.getArtist());
                break;
        }
    }

}
