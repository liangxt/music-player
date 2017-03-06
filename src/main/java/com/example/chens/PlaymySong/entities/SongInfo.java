package com.example.chens.PlaymySong.entities;

import java.io.Serializable;


public abstract class SongInfo implements Serializable {
    public static final String LYRIC_NOT_AVAILABLE = "LYRIC_NOT_AVAILABLE";
    private String title;
    private String artist;
    private String album;
    private String lyric;
    private Comments comments;
    private int rawSourceID;
    private String link;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public int getRawSourceID() {
        return rawSourceID;
    }

    public void setRawSourceID(int rawSourceID) {
        this.rawSourceID = rawSourceID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
