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

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.ui.settings.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayingCommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayingCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayingCommentFragment extends Fragment {

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
    private TextView title;
    private TextView singer;


    private OnFragmentInteractionListener mListener;

    public PlayingCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayingCommentFragment.
     */
    public static PlayingCommentFragment newInstance() {
        PlayingCommentFragment fragment = new PlayingCommentFragment();
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
        view = inflater.inflate(R.layout.playing_page_playingcommentfragment, container, false);

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
        title = (TextView)view.findViewById(R.id.allMusicTitleLayout);
        singer = (TextView)view.findViewById(R.id.allMusicTitleLayout);

        // set buttons' listeners
        setting.setOnClickListener(settingClick);
        backward.setOnClickListener(backwardClick);
        forward.setOnClickListener(forwardClick);
        play.setOnClickListener(playClick);
        favorite.setOnClickListener(favoriteClick);
        addToList.setOnClickListener(addToListClick);


        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

                            new String[] { "List1", "List2" }, null, null)

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
