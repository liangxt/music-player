package com.example.chens.PlaymySong.ui.main_page.top20_page;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.chens.PlaymySong.R;


/**
 * Created by Songze Chen on 2016/4/3.
 */

public class Top20Fragment extends Fragment {
    private static final String APP_PACKAGE_NAME = "com.vibin.billy";
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.top20_page_top20fragment, container, false);
        final Context context=getActivity();
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            new AlertDialog.Builder(context)
                    .setTitle("Do you want to open Billy?")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME));
                                }
                            }).setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            WebView wv = (WebView)view.findViewById(R.id.word_web_view);
                            wv.loadUrl("http://www.billboard.com/charts/hot-100");
                        }
                    }
            ).create().show();
        } else {
            WebView wv = (WebView)view.findViewById(R.id.word_web_view);
            wv.loadUrl("http://www.billboard.com/charts/hot-100");
        }
        return view;
    }
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
