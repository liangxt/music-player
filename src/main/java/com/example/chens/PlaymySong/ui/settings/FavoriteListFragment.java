package com.example.chens.PlaymySong.ui.settings;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.client.DefaultSocketClient;
import com.example.chens.PlaymySong.myApp;

public class FavoriteListFragment extends ListFragment {
    private String host = "localhost";
    private String[] online_songs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.settings_myfavoritelistactivity, container, false);
        String[] datasource = getOnlineSongs();
        if (datasource == null || datasource.length == 0) {
            datasource = new String[1];
            datasource[0] = " ";
            new AlertDialog.Builder(getActivity())
                    .setMessage("No Favorite List information. Try to explore music world! ")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                                    startActivity(intent);
                                }
                            }).create().show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.settings_favoritelistfragment, R.id.mflsong, datasource);
        setListAdapter(adapter);
        setRetainInstance(true);

        return rootView;
    }

    private String[] getOnlineSongs() {
        final String id = ((myApp) getActivity().getApplication()).getMyID();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultSocketClient client = new DefaultSocketClient(host, 3000);
                client.setTaskGetFavoriteMusic(id);
                String result = client.runRequest();
                if (result != null) {
                    online_songs = result.split("\\*;\\*");
                }
            }
        });
        thread.start();
        return online_songs;
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        ViewGroup viewGroup = (ViewGroup) view;
        TextView txt = (TextView) viewGroup.findViewById(R.id.txtitem);
        final String infor = txt.getText().toString();
        new AlertDialog.Builder(getActivity())
                .setTitle("Do you want to delete " + infor + " ?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String id = ((myApp) getActivity().getApplication()).getMyID();
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DefaultSocketClient client = new DefaultSocketClient("", 3000);
                                        client.setTaskDeleteFavoriteList(id, infor);
                                        String result = client.runRequest();
                                    }
                                });
                                thread.start();
                                Intent intent = new Intent(getActivity(), SettingActivity.class);
                                    startActivity(intent);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
}
