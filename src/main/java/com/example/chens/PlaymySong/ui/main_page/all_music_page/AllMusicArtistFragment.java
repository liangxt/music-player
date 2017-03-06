package com.example.chens.PlaymySong.ui.main_page.all_music_page;

import android.app.Fragment;

import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.main_page.SortedFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SortedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMusicArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMusicArtistFragment extends SortedAllMusicFragment {

    protected ArrayList<String> allSongsName = new ArrayList<String>();

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
