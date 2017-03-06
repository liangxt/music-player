package com.example.chens.PlaymySong.ui.settings;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chens.PlaymySong.ui.cover.LoginActivity;
import com.example.chens.PlaymySong.ui.main_page.MainActivity;
import com.example.chens.PlaymySong.R;


public class SettingListFragment extends ListFragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.settings_settingactivity, container, false);
        String[] datasource = {"Account Profile", "Switch Account/Login", "Recently Played", "Wish List",
                                "My Favorite List", "Help", "Feedback", "Back to Play my Song",  "Exit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.settings_settinglistfragment, R.id.txtitem, datasource);
        setListAdapter(adapter);
        setRetainInstance(true);
        return rootView;
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup) view;
        TextView txt = (TextView) viewGroup.findViewById(R.id.txtitem);
        String choice = txt.getText().toString();
        if (choice.equals("Account Profile")) {
            Intent intent = new Intent(getActivity(), AccountProfileActivity.class);
            startActivity(intent);
        }
        if (choice.equals("Switch Account/Login")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        if (choice.equals("Recently Played")) {
            Intent intent3 = new Intent(getActivity(), RecentlyPlayedActivity.class);
            startActivity(intent3);
        }
        if (choice.equals("Wish List")) {
            Intent intent = new Intent(getActivity(), WishListActivity.class);
            startActivity(intent);
        }
        if (choice.equals("My Favorite List")) {
            Intent intent = new Intent(getActivity(), MyFavoriteListActivity.class);
            startActivity(intent);
        }
        if (choice.equals("Help")) {
            Intent intent = new Intent(getActivity(), HelpActivity.class);
            startActivity(intent);
        }else if (choice.equals("Feedback")) {
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        }else if (choice.equals("Back to Play my Song")) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else if (choice.equals("Exit")) {
            ExitActivity.getInstance().exit();
        }
    }
}
