package com.example.chens.PlaymySong.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.client.DefaultSocketClient;


/**
 * Activity for user feedback.
 */
public class FeedbackActivity extends Activity {
    private EditText title_text;
    private EditText content_text;
    private EditText email_text;
    private String title;
    private String content;
    private String email;
    private Toast toast;
    private String host = "localhost";

    @Override
    public void onCreate(Bundle savedInstanceActivity) {
        super.onCreate(savedInstanceActivity);
        setContentView(R.layout.settings_feedbackactivity);
        ExitActivity.getInstance().addActivity(this);
        Button send = (Button) findViewById(R.id.fbsend);
        Button cancel = (Button) findViewById(R.id.fbcancel);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_text = (EditText) findViewById(R.id.fbtitle_edit);
                content_text = (EditText) findViewById(R.id.fbcontent_edit);
                email_text = (EditText) findViewById(R.id.fbemail_edit);
                title = title_text.getText().toString();
                content = content_text.getText().toString();
                email = email_text.getText().toString();
                String message = checkMessage();
                if (message.equals("true")) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            long time = System.currentTimeMillis();
                            DefaultSocketClient client = new DefaultSocketClient(host, 3000);
                            String key = email + " - " + Long.toString(time);
                            String contents = "title " + title + " content" + content;
                            client.setTaskFeedback(key, contents);
                            String result = client.runRequest();
                        }
                    });
                    thread.start();
                    new AlertDialog.Builder(FeedbackActivity.this)
                            .setTitle("Your request has been sent!")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                                            startActivity(intent);
                                        }
                                    }).create().show();
                } else {
                    sendToast(message);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedbackActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private String checkMessage() {
        if (title == null || title.length() == 0) {
            return "Invalid title. Please try again.";
        }
        if (content == null || content.length() == 0) {
            return "Invalid content. Please try again.";
        }
        if (email == null || email.length() == 0 || !email.contains("@")) {
            return "Invalid email. Please try again.";
        }
        return "true";
    }

    private void sendToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
            toast.setText(s);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
            toast.setText(s);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
