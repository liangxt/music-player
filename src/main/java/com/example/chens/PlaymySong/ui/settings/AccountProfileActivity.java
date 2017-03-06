package com.example.chens.PlaymySong.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.client.DefaultSocketClient;
import com.example.chens.PlaymySong.myApp;
import com.example.chens.PlaymySong.ui.cover.LoginActivity;

import java.io.File;


/**
 * Activity for user to modify information including name, password, picture and email.
 */
public class AccountProfileActivity extends Activity {
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private ImageButton imageButton;
    private EditText name_text;
    private String name;
    private EditText password_text;
    private String password;
    private EditText confirm_password_text;
    private String confirm_password;
    private EditText email_text;
    private String email;
    private EditText whatsup_text;
    private String whatsup;
    private Toast toast;
    private String host = "localhost";


    @Override
    public void onCreate(Bundle savedInstanceActivity) {
        super.onCreate(savedInstanceActivity);
        setContentView(R.layout.settings_accountprofileactivity);
        ExitActivity.getInstance().addActivity(this);
        Button ok = (Button) findViewById(R.id.apok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_text = (EditText) findViewById(R.id.apname_edit);
                password_text = (EditText) findViewById(R.id.appassword_edit);
                confirm_password_text = (EditText) findViewById(R.id.apconfirmpassword_edit);
                email_text = (EditText) findViewById(R.id.apemail_edit);
                whatsup_text = (EditText) findViewById(R.id.apwhatsup_edit);
                name = name_text.getText().toString();
                password = password_text.getText().toString();
                confirm_password = confirm_password_text.getText().toString();
                email = email_text.getText().toString();
                whatsup = whatsup_text.getText().toString();
                String message = checkMessage();
                if (message.equals("true")) {
                    final String path = getImageUri().toString();
                    final String id = ((myApp) getApplication()).getMyID();
                    if (id == null || id.length() == 0) {
                        new AlertDialog.Builder(AccountProfileActivity.this)
                                .setTitle("Please log in! ")
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(AccountProfileActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }).create().show();
                    } else {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DefaultSocketClient client = new DefaultSocketClient(host, 3000);
                                client.setTaskUpdateUserInfo(id, password, email, path, whatsup);
                                String result = client.runRequest();
                            }
                        });
                        thread.start();
                        new AlertDialog.Builder(AccountProfileActivity.this)
                                .setTitle("Modify information successfully!")
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(AccountProfileActivity.this, SettingActivity.class);
                                                startActivity(intent);
                                            }
                                        }).create().show();
                    }
                } else {
                    sendToast(message);
                }
            }
        });
        Button cancel = (Button) findViewById(R.id.apcancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton = (ImageButton) findViewById(R.id.apphoto);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(AccountProfileActivity.this)
                        .setTitle("Set user picture").setNegativeButton("Take picture",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        choseHeadImageFromCameraCapture();
                                    }
                                }).setPositiveButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }
                        ).setNeutralButton("Photo album",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        choseHeadImageFromGallery();
                                    }
                                }
                        ).create();
                dialog.show();
            }
        });
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

    private String checkMessage() {
        if (name == null || name.length() == 0) {
            return "Invalid name. Please try again.";
        }
        if (password == null || password.length() == 0) {
            return "Invalid password. Please try again.";
        }
        if (confirm_password == null || confirm_password.length() == 0) {
            return "Invalid password. Please try again.";
        }
        if (!password.equals(confirm_password)) {
            return "Your password do not match. Please try again.";
        }
        if (email == null || email.length() == 0) {
            return "Invalid email. Please try again.";
        }
        return "true";
    }

    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    private void choseHeadImageFromCameraCapture() {
        if (hasSdcard()) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(AccountProfileActivity.this, "SdCard No find! ",
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    cropRawPhoto(intent.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        cropRawPhoto(getImageUri());
                    } else {
                        Toast.makeText(getApplication(), "No SDcard find!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    if (intent != null) {
                        setImageToHeadView(intent);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
            imageButton.setImageDrawable(drawable);
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }
}
