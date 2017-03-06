package com.example.chens.PlaymySong.ui.main_page.all_music_page;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.settings.SettingActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMusicFragment extends Fragment {

    private AllMusicTitleFragment allMusicTitleFragment = new AllMusicTitleFragment();
    private AllMusicArtistFragment allMusicArtistFragment = new AllMusicArtistFragment();
    private AllMusicAlbumFragment allMusicAlbumFragment = new AllMusicAlbumFragment();
    private ImageView settings;
    private TextView importMySongs;
    private MediaMetadataRetriever mmr;
    private ArrayList<Song> songs = new ArrayList<Song>();
    private View view;
    private FrameLayout allMusicTitleLayout, allMusicArtistLayout, allMusicAlbumLayout;


    public AllMusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AllMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllMusicFragment newInstance(String param1, String param2) {
        AllMusicFragment fragment = new AllMusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.all_music_page_allmusicfragment, container, false);
        allMusicTitleLayout = (FrameLayout) view.findViewById(R.id.allMusicTitleLayout);
        allMusicArtistLayout = (FrameLayout) view.findViewById(R.id.allMusicArtistLayout);
        allMusicAlbumLayout = (FrameLayout) view.findViewById(R.id.allMusicAlbumLayout);
        initFragment();


        importMySongs = (TextView) view.findViewById(R.id.import_my_songs);
        System.out.println(importMySongs);
        importMySongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    songs.clear();
                    Field[] fields = R.raw.class.getFields();
                    MyLocalDB db = new MyLocalDB(getActivity().getApplicationContext());
                    for (Field field : fields) {
                        AssetFileDescriptor afd;
                        int rawSourceID = field.getInt(null);
                        afd = getResources().openRawResourceFd(rawSourceID);
                        mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                        Song song = new Song(title, artist, album, rawSourceID);
                        songs.add(song);
                        db.addToPlayList(song);
                    }
                    allMusicTitleFragment.updateSongs(songs);
                    allMusicArtistFragment.updateSongs(songs);
                    allMusicAlbumFragment.updateSongs(songs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        settings = (ImageView) view.findViewById(R.id.settingsView);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /**
     * Link fragments to sub-content.
     */
    private void initFragment() {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (!allMusicTitleFragment.isAdded()) {

            fragmentTransaction.add(R.id.allMusicSubContent, allMusicTitleFragment);
        }
        if (!allMusicArtistFragment.isAdded()) {
            fragmentTransaction.add(R.id.allMusicSubContent, allMusicArtistFragment);
        }
        if (!allMusicAlbumFragment.isAdded()) {
            fragmentTransaction.add(R.id.allMusicSubContent, allMusicAlbumFragment);
        }
        showThisFragment(allMusicTitleFragment);
        fragmentTransaction.commit();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FrameLayout title = (FrameLayout) getActivity().findViewById(R.id.allMusicTitleLayout);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThisFragment(allMusicTitleFragment);
            }
        });

        FrameLayout singer = (FrameLayout) getActivity().findViewById(R.id.allMusicArtistLayout);
        singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThisFragment(allMusicArtistFragment);
            }
        });

        FrameLayout album = (FrameLayout) getActivity().findViewById(R.id.allMusicAlbumLayout);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThisFragment(allMusicAlbumFragment);
            }
        });
    }

    /**
     * Show the specified fragment's content.
     * @param thisFragment
     */
    private void showThisFragment(Fragment thisFragment) {
        clearSelected();
        changeTabStyle(thisFragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(allMusicTitleFragment);
        fragmentTransaction.hide(allMusicArtistFragment);
        fragmentTransaction.hide(allMusicAlbumFragment);
        fragmentTransaction.show(thisFragment);
        fragmentTransaction.commit();
    }

    /**
     * Set all tab's color to default.
     */
    private void clearSelected() {
        if (allMusicTitleFragment.isVisible()) {
            allMusicTitleLayout.setBackgroundColor(Color.parseColor("#535353"));
        }

        if (allMusicArtistFragment.isVisible()) {
            allMusicArtistLayout.setBackgroundColor(Color.parseColor("#535353"));
        }

        if (allMusicAlbumFragment.isVisible()) {
            allMusicAlbumLayout.setBackgroundColor(Color.parseColor("#535353"));
        }

    }

    /**
     * Change the chosen tab's background color.
     * @param tabFragment
     */
    private void changeTabStyle(Fragment tabFragment) {
        if (tabFragment instanceof AllMusicTitleFragment) {
            allMusicTitleLayout.setBackgroundColor(Color.parseColor("#34819D"));
        } else if (tabFragment instanceof AllMusicArtistFragment) {
            allMusicArtistLayout.setBackgroundColor(Color.parseColor("#34819D"));
        } else if (tabFragment instanceof AllMusicAlbumFragment) {
            allMusicAlbumLayout.setBackgroundColor(Color.parseColor("#34819D"));
        }
    }
}
