package com.example.chens.PlaymySong.ui.main_page.recommend_page;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.main_page.SortedFragment;

import java.util.ArrayList;


public class SortedRecommendFragment extends SortedFragment {

    protected Toast toast;
    @Override
    protected void updateSongsName(ArrayList<Song> songs) {
        allSongsName = new ArrayList<>();
        for (Song song : songs) {
            allSongsName.add(song.getTitle() + " - " + song.getArtist());
        }
    }


    @Override
    protected void setListViewOnClickListener(final ArrayList<Song> songs) {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView v = (TextView) view;
                String titleAndArtist = v.getText().toString();
                String title = titleAndArtist.split(" - ")[0];
                String artist = titleAndArtist.split(" - ")[1];
                MyLocalDB db = new MyLocalDB(getContext());
                if (db.containsInWishList(title, artist)) {
                    sendToast(titleAndArtist + " is already in the wish list");
                } else {
                    db.addToWishList(new Song(title, artist));
                    sendToast(titleAndArtist + " is successfully added to the wish list");
                }
            }
        });
    }

    private void sendToast(String s) {
        if (toast == null){
            toast = Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
