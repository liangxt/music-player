package com.example.chens.PlaymySong.ui.main_page;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.main_page.all_music_page.AllMusicArtistFragment;
import com.example.chens.PlaymySong.ui.main_page.playing_page.PlayingActivity;
import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.ui.settings.ExitActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TitleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TitleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class SortedFragment extends Fragment {

    protected ListView listview;
    private View view;
    protected ArrayList<String> allSongsName = new ArrayList<String>();


    public SortedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SortedFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static SortedFragment newInstance(String param1, String param2) {
//        SortedFragment fragment = new SortedFragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_title, container, false);

        listview = (ListView) view.findViewById(R.id.titleListView);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Update list view accoring to the ArrayList of songs.
     * @param songs
     */
    public void updateSongs(ArrayList<Song> songs) {
        sortSongs(songs);
        updateSongsName(songs);
        setListViewAdapter();

        setListViewOnClickListener(songs);
    }

    /**
     * To be override, default by title.
     * @param songs
     */
    protected void sortSongs(ArrayList<Song> songs) {
        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getTitle().compareToIgnoreCase(song2.getTitle());
            }
        });
    }

    /**
     * Link ListView and ArrayList of song names.
     */
    private void setListViewAdapter() {
        listview.setAdapter(new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_list_item_1,
                (String[]) allSongsName.toArray(new String[allSongsName.size()])) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });
    }

    /**
     * By default will be derived to play page.
     */
    protected void setListViewOnClickListener(ArrayList<Song> songs) {

        final ArrayList<Song> temp = new ArrayList<Song>();
        for (Song song : songs) {
            temp.add(song);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), PlayingActivity.class);
                intent.putExtra(CustomNames.CURR_POSITION, position);
                intent.putExtra(CustomNames.SONG_LIST, temp);

                startActivity(intent);
            }
        });
    }

    /**
     * Create a new song name list according to the ArrayList of songs.
     * Could be override when album's information is lacked.
     * @param songs
     */
    protected void updateSongsName(ArrayList<Song> songs) {
        allSongsName = new ArrayList<>();
        for (Song song : songs) {
            allSongsName.add(song.getTitle() + " - " + song.getArtist() + " - " + song.getAlbum());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
