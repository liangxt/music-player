package com.example.chens.PlaymySong.entities;

import java.util.ArrayList;


public class Comments {
    private String title;
    private String artist;
    private ArrayList<SingleComment> singleComment;

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

    public ArrayList<SingleComment> getSingleComment() {
        return singleComment;
    }

    public void addSingleComment(SingleComment singleComment) {
        this.singleComment.add(singleComment);
    }
}
