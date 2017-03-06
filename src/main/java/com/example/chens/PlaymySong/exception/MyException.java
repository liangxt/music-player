package com.example.chens.PlaymySong.exception;

import android.app.AlertDialog;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyException {
    public MyException(String s, File dir){
        Log.e(null, s);// log the exception in System.out of terminal
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            long time = System.currentTimeMillis();
            String logName = "log-" + time + ".txt";
            File log = new File(dir, logName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(log));
            writer.write("Error time: " + sdf.format(cal.getTime()));
            writer.write(s);
            writer.close();
        } catch (Exception e1){
            e1.printStackTrace();
        }
    }
}
