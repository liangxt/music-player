package com.example.chens.PlaymySong.ui.main_page.playing_page;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chens.PlaymySong.DBLayout.MyLocalDB;
import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.entities.Song;
import com.example.chens.PlaymySong.ui.main_page.CustomNames;
import com.example.chens.PlaymySong.ui.settings.ExitActivity;

import java.util.ArrayList;

public class PlayingActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private MyLocalDB db = new MyLocalDB(this);
    // used for test, all songs' names

    // used for test, lyric
    private String lyric = "Hello, it's me\n" +
                    "I was wondering if after all these years you'd like to meet\n" +
                    "To go over everything\n" +
                    "They say that time's supposed to heal ya\n" +
                    "But I ain't done much healing\n" +
                    "\n" +
                    "Hello, can you hear me\n" +
                    "I'm in California dreaming about who we used to be\n" +
                    "When we were younger and free\n" +
                    "I've forgotten how it felt before\n the world fell at our feet\n"  +
                    "I've forgotten how it felt before\n the world fell at our feet\n" +
                    "I've forgotten how it felt before\n the world fell at our feet\n\n" +
                    "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";



    private SectionsPagerAdapter mSectionsPagerAdapter;

    // all fragments
    public ArrayList<Fragment> fragments;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playingpage_playingactivity);
        ExitActivity.getInstance().addActivity(this);

        Bundle bundle= getIntent().getExtras();
        PlayingResource.allSongs = (ArrayList<Song>) bundle.getSerializable(CustomNames.SONG_LIST);
        PlayingResource.currentSongPos = (int)bundle.getSerializable(CustomNames.CURR_POSITION);
        System.out.println(PlayingResource.currentSongPos);
        // put all fragments in the list
        fragments = new ArrayList<Fragment>();
        fragments.add(PlayingBottomFragment.newInstance());
        fragments.add(PlayingLyricFragment.newInstance(lyric));
        fragments.add(PlayingCommentFragment.newInstance());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                                                             fragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.playing_page_playingactivity, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("default");
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
