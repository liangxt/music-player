package com.example.chens.PlaymySong.entities;

import java.io.Serializable;


public class Song extends SongInfo implements updateMySong, Serializable {
    public Song(String title, String artist) {
        setTitle(title);
        setArtist(artist);
    }
    public Song(String title, String artist, String album, int rawSourceID) {
        setTitle(title);
        setArtist(artist);
        setAlbum(album);
        setRawSourceID(rawSourceID);
    }
}
