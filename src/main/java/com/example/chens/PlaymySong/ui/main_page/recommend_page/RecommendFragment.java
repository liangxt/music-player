package com.example.chens.PlaymySong.ui.main_page.recommend_page;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.entities.Song;

import com.example.chens.PlaymySong.ui.main_page.CustomNames;
import com.example.chens.PlaymySong.ui.settings.SettingActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecommendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendFragment extends Fragment {

    private RecommendTitleFragment recommendTitleFragment = new RecommendTitleFragment();
    private RecommendArtistFragment recommendArtistFragment = new RecommendArtistFragment();
    private ArrayList<Song> recommendSongs = new ArrayList<Song>();
    private ArrayList<Song> localSongs = new ArrayList<Song>();
    private FrameLayout recommendTitleLayout, recommendArtistLayout;
    private ImageView settings;
    private View view;
    private Button recommendationButton;
    /**
     * Recommend songs according to the all title of local songs.
     */
    private Runnable doRecommendation = new Runnable() {
        @Override
        public void run() {
            MyLocalDB db = new MyLocalDB(getActivity().getApplicationContext());
            localSongs = db.getPlayListAll();
            recommendSongs.clear();
            for (Song localSong : localSongs) {
                addRecommendSongs(localSong, 2);
            }
        }
    };

    /**
     * add recommend songs to recommendSongs list
     * @param localSong
     * @param n recommend n songs per local song
     */
    private void addRecommendSongs(Song localSong, int n) {
        String title = localSong.getTitle().replaceAll(" ", "%20").toLowerCase();
        String URL_ADDR = CustomNames.RECOMMEMD_URL_PREFIX + title;
        URL url = null;
        try {
            url = new URL(URL_ADDR);
            InputStream is = url.openStream();
            BufferedReader bis = new BufferedReader(new InputStreamReader(is)) ;
            String line = null ;
            int added = 0;
            while((line = bis.readLine()) != null && added < n){
                if (line.contains("class=\"title\"")) {
                    line = bis.readLine();
                    String recommendSongTitle = line.split("\\&quot;")[1];
                    String recommendSongLink = line.split("\"")[1];
                    bis.readLine();
                    bis.readLine();
                    line = bis.readLine();
                    String temp = line.split("<*>")[1];
                    String recommendSongArtist = temp.substring(0, temp.length() - 3);
                    Song recommendSong = new Song(recommendSongTitle, recommendSongArtist);
                    recommendSong.setLink(recommendSongLink);
                    recommendSongs.add(recommendSong);
                    added++;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recommendTitleFragment.updateSongs(recommendSongs);
                            recommendArtistFragment.updateSongs(recommendSongs);
                        }
                    });

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendFragment.
     */
    public static RecommendFragment newInstance(String param1, String param2) {
        RecommendFragment fragment = new RecommendFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.recommend_page_recommendfragment, container, false);
        recommendTitleLayout = (FrameLayout) view.findViewById(R.id.recommendTitleLayout);
        recommendArtistLayout = (FrameLayout) view.findViewById(R.id.recommendArtistLayout);
        recommendationButton = (Button) view.findViewById(R.id.recommendButton);
        recommendationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(doRecommendation).start();
            }
        });
        initFragment();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FrameLayout title = (FrameLayout) getActivity().findViewById(R.id.recommendTitleLayout);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showThisFragment(recommendTitleFragment);
                //recommendTitleFragment.updateSongs(recommendSongs);
            }
        });

        FrameLayout artist = (FrameLayout) getActivity().findViewById(R.id.recommendArtistLayout);
        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showThisFragment(recommendArtistFragment);
                //recommendArtistFragment.updateSongs(recommendSongs);
            }
        });

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

    /**
     * Link fragments to sub-content.
     */
    private void initFragment() {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (!recommendTitleFragment.isAdded()) {

            fragmentTransaction.add(R.id.recommendSubContent, recommendTitleFragment);
        }
        if (!recommendArtistFragment.isAdded()) {
            fragmentTransaction.add(R.id.recommendSubContent, recommendArtistFragment);
        }

        showThisFragment(recommendTitleFragment);
        fragmentTransaction.commit();
    }

    /**
     * Show the specified fragment's content.
     * @param thisFragment
     */
    private void showThisFragment(Fragment thisFragment) {
        clearSelected();
        changeTabStyle(thisFragment);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(recommendTitleFragment);
        fragmentTransaction.hide(recommendArtistFragment);
        fragmentTransaction.show(thisFragment);
        fragmentTransaction.commit();
    }

    /**
     * Set all tab's color to default.
     */
    private void clearSelected() {
        if (recommendTitleFragment.isVisible()) {
            recommendTitleLayout.setBackgroundColor(Color.parseColor("#535353"));
        }

        if (recommendArtistFragment.isVisible()) {
            recommendArtistLayout.setBackgroundColor(Color.parseColor("#535353"));
        }

    }

    /**
     * Change the chosen tab's background color.
     * @param tabFragment
     */
    private void changeTabStyle(Fragment tabFragment) {
        if (tabFragment instanceof RecommendTitleFragment) {
            recommendTitleLayout.setBackgroundColor(Color.parseColor("#34819D"));
        } else if (tabFragment instanceof RecommendArtistFragment) {
            recommendArtistLayout.setBackgroundColor(Color.parseColor("#34819D"));
        }
    }
}
