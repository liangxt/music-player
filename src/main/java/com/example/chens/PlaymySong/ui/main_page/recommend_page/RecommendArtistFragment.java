package com.example.chens.PlaymySong.ui.main_page.recommend_page;

import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.main_page.SortedFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class RecommendArtistFragment extends SortedRecommendFragment {
    @Override
    protected void sortSongs(ArrayList<Song> songs) {
        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getArtist().compareToIgnoreCase(song2.getArtist());
            }
        });
    }
}
