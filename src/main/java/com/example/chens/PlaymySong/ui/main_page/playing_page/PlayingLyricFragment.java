package com.example.chens.PlaymySong.ui.main_page.playing_page;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayingLyricFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayingLyricFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayingLyricFragment extends Fragment {
    // static symbol


    private View view;

    // the lyric
    private String lyric;

    // all buttons
    private ImageButton setting;
    private ImageButton backward;
    private ImageButton forward;
    private ImageButton play;
    private ImageButton favorite;
    private ImageButton addToList;

    // all text views
    private TextView timePlayed;
    private TextView songLength;
    private TextView titleTextView;
    private TextView artistTextView;
    private TextView lyricTextView;


    public PlayingLyricFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lyric the lyric
     * @return A new instance of fragment PlayingLyricFragment.
     */
    public static PlayingLyricFragment newInstance(String lyric) {
        PlayingLyricFragment fragment = new PlayingLyricFragment();
        Bundle args = new Bundle();
        args.putString(CustomNames.LYRIC, lyric);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            new Thread(changeLyric).start();
        }
    }


    public Runnable changeLyric = new Runnable() {
        @Override
        public void run() {
            Song song = PlayingResource.allSongs.get(PlayingResource.currentSongPos);
            lyric = song.getLyric();
            final String title = song.getTitle();
            final String artist = song.getArtist();
            if (lyric == null) {

                searchLyricOnline(title, artist);
                // TODO: 2016/4/20 save to db
                song.setLyric(lyric);
                MyLocalDB db = new MyLocalDB(getContext());
                db.addToPlayList(song);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lyricTextView.setText(lyric);
                    titleTextView.setText(title);
                    artistTextView.setText(artist);
                }
            });

        }
    };


    private void searchLyricOnline(String title, String artist) {
        lyric = Song.LYRIC_NOT_AVAILABLE;
        String LYRICS__URL_SUFFIX =
                title.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase()
                        + "-lyrics-"
                        + artist.replaceAll("[^a-zA-Z0-9]", "-").toLowerCase()
                        + ".html";
        String URL_ADDR = CustomNames.LYRICS_URL_PREFIX + LYRICS__URL_SUFFIX;
        URL url = null;
        try {
            url = new URL(URL_ADDR);
            InputStream is = url.openStream();
            BufferedReader bis = new BufferedReader(new InputStreamReader(is)) ;
            String line = null ;

            while((line = bis.readLine()) != null){
                if (line.contains("id=\"lyrics\"")) {

                    int index = line.indexOf("<div id=\"lyrics\" class=\"SCREENONLY\" itemprop=\"description\">")
                                            + "<div id=\"lyrics\" class=\"SCREENONLY\" itemprop=\"description\">".length();
                    StringBuilder lyricBuilder = new StringBuilder();
                    lyricBuilder.append(line.substring(index).replaceAll("<br />", "\n").replaceAll("\\&#8217;", "'"));
                    while (!(line = bis.readLine()).contains("id=\"lyrics\"")) {
                        lyricBuilder.append(line.replaceAll("<br />", "\n").replaceAll("\\&#8217;", "'"));
                    }
                    index = line.indexOf("<br />");
                    lyricBuilder.append(line.substring(0, index));

                    lyric = lyricBuilder.toString();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.playing_page_playinglyricfragment, container, false);

        // initialize buttons
        setting = (ImageButton)view.findViewById(R.id.setting);
        backward = (ImageButton)view.findViewById(R.id.backward);
        forward = (ImageButton)view.findViewById(R.id.forward);
        play = (ImageButton)view.findViewById(R.id.play);
        favorite = (ImageButton)view.findViewById(R.id.favorite);
        addToList = (ImageButton)view.findViewById(R.id.addToList);

        // initialize text views
        timePlayed = (TextView)view.findViewById(R.id.timePlayed);
        songLength = (TextView)view.findViewById(R.id.songLength);
        titleTextView = (TextView)view.findViewById(R.id.allMusicTitleLayout);
        artistTextView = (TextView)view.findViewById(R.id.allMusicArtistLayout);
        lyricTextView = (TextView)view.findViewById(R.id.lyricTextView);

        // set buttons' listeners
        setting.setOnClickListener(settingClick);
        backward.setOnClickListener(backwardClick);
        forward.setOnClickListener(forwardClick);
        play.setOnClickListener(playClick);
        favorite.setOnClickListener(favoriteClick);
        addToList.setOnClickListener(addToListClick);

        // set lyrics
        lyricTextView.setText(lyric);


        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener settingClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(v.getContext(), SettingActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener albumClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener backwardClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener playClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener forwardClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener favoriteClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener addToListClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setMessage("Add to List");

//            builder.setTitle("");

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    new AlertDialog.Builder(view.getContext()).setTitle("List Name").setIcon(

                            android.R.drawable.ic_dialog_info).setView(

                            new EditText(view.getContext())).setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }

            });

            builder.setNegativeButton("Existed List", new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    new AlertDialog.Builder(view.getContext()).setTitle("Lists").setMultiChoiceItems(

                            new String[]{"List1", "List2"}, null, null)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }

            });

            builder.create().show();
        }
    };
}
