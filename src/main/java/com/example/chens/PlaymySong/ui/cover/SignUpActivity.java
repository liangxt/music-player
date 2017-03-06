package com.example.chens.PlaymySong.ui.cover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chens.PlaymySong.R;
import com.example.chens.PlaymySong.exception.MyException;
import com.example.chens.PlaymySong.ui.main_page.MainActivity;
import com.example.chens.PlaymySong.ui.settings.ExitActivity;

import java.io.File;


public class SignUpActivity extends AppCompatActivity {
    private Button signUpButton, cancelButton;
    private Toast toast;
    private TextView userIDTextView, passwordTextView, passwordConfirmTextView;
    private final String idAlreadyExist = "This id already exists";
    private final String passwordNotMatch = "password doesn't match";
    private final String signUpSuccess = "successful signed up";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover_singupactivity);
        ExitActivity.getInstance().addActivity(this);
        userIDTextView = (TextView) findViewById(R.id.userIDSignUp);
        passwordTextView = (TextView) findViewById(R.id.passwordSignUp);
        passwordConfirmTextView = (TextView) findViewById(R.id.passwordConfirmSignUp);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String userID = userIDTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                String passwordConfirm = passwordConfirmTextView.getText().toString();
                if (!password.equals(passwordConfirm)) {
                    sendToast(passwordNotMatch);
                } else if (isValid(userID, password)) {
                    sendToast(signUpSuccess);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    File dir = getFilesDir();
                    new MyException("user id already exists", dir);
                    sendToast(idAlreadyExist);
                }
            }
        });
    }

    private boolean isValid(String userID, String password) {
        // to be connected to server to check if it's duplicated
        if (userID != null && !userID.equals("") && password != null && !password.equals("")) {
            return true;
        }
        return false;
    }

    private void sendToast(String s) {

        if (toast == null){
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        }

        toast.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
