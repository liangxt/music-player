package com.example.chens.PlaymySong.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DefaultSocketClient  extends Thread implements SocketClientConstants {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket sock;
    private String strHost;
    private int iPort;

    private String task;

    // login info
    private String id;
    private String passwd;
    private String oldPasswd;
    private String newPasswd;
    private String titleSinger;
    private String album;
    private String link;
    private String userIdTime;
    private String content;
    private String time;
    private String score;
    private String email;
    private String photo;
    private String whatsUp;


    public DefaultSocketClient(String strHost, int iPort) {
        task = "";
        setPort (iPort);
        setHost (strHost);
    }

    /**
     * start run
     */
    public String runRequest(){
        String result = null;
        if (openConnection()){
            result = handler();
            closeSession();
        }
        return result;
    }

    /**
     * get the connection
     */
    public boolean openConnection(){

        try {
            sock = new Socket(strHost, iPort);
            System.out.println("Connect to localhost");
        }
        catch(IOException socketError){
            System.err.println("Unable to connect to " + strHost);
            return false;
        }
        try {
            writer = new ObjectOutputStream(sock.getOutputStream());
            reader = new ObjectInputStream(sock.getInputStream());
        }
        catch (Exception e){
            System.err.println("Unable to obtain stream to/from " + strHost);
            return false;
        }
        return true;
    }

    /**
     * deal with each command
     */
    public String handler(){
        System.out.println ("Handling session with " + strHost + ":" + iPort);
        if (task.equals("login")) {
            sendOutput(task + "," + id + "," + passwd);
        } else if (task.equals("sign_up")) {
            sendOutput(task + "," + id + "," + passwd);
        } else if (task.equals("change_passwd")) {
            sendOutput(task + "," + id + "," + oldPasswd + "," + newPasswd);
        } else if (task.equals("add_favorite_list")) {
            sendOutput(task + "," + id + "," + titleSinger + "," + album);
        } else if (task.equals("delete_favorite_list")) {
            sendOutput(task + "," + id + "," + titleSinger);
        } else if (task.equals("add_play_list")) {
            sendOutput(task + "," + id + "," + titleSinger + "," + album);
        } else if (task.equals("delete_play_list")) {
            sendOutput(task + "," + id + "," + titleSinger);
        } else if (task.equals("add_wish_list")) {
            sendOutput(task + "," + id + "," + titleSinger + "," + album
                    + "," + link);
        } else if (task.equals("delete_wish_list")) {
            sendOutput(task + "," + id + "," + titleSinger);
        } else if (task.equals("send_feedback")) {
            sendOutput(task + "," + userIdTime + "," + content);
        } else if (task.equals("send_comment")) {
            sendOutput(task + "," + titleSinger + "," + id + "," + time + ","
                    + content + "," + score);
        } else if (task.equals("update_user_info")) {
            sendOutput(task + "," + id + "," + passwd + "," + email + ","
                    + photo + "," + whatsUp);
        } else if (task.equals("get_user_info")) {
            sendOutput(task + "," + id);
        } else if (task.equals("get_comment")) {
            sendOutput(task + "," + titleSinger);
        } else if (task.equals("get_favorite_music")) {
            sendOutput(task + "," + titleSinger);
        } else if (task.equals("get_play_music")) {
            sendOutput(task + "," + titleSinger);
        } else if (task.equals("get_wish_music")) {
            sendOutput(task + "," + titleSinger);
        }

        String result = null;
        try {
            Object temp = reader.readObject();
            result = temp.toString();
            //System.out.println(temp);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return result;
    }

    /**
     * send info out
     * @param obj
     */
    public void sendOutput(Object obj){
        try {
            if (obj != null) {
                writer.writeObject(obj);
            } else {
                System.out.println("Object is null!");
            }
        }
        catch (IOException e){
            System.out.println("Error writing to " + strHost);
        }
    }

    /**
     * get info in
     * @param strInput
     */
    public void handleInput(String strInput){
        System.out.println(strInput);
    }

    /**
     * close all
     */
    public void closeSession(){
        try {
            writer = null;
            reader = null;
            sock.close();
        }
        catch (IOException e){
            System.err.println("Error closing socket to " + strHost);
        }
    }

    /**
     * set host name
     * @param strHost
     */
    public void setHost(String strHost){
        this.strHost = strHost;
    }

    /**
     * set port
     * @param iPort
     */
    public void setPort(int iPort){
        this.iPort = iPort;
    }

    public void setTaskLogin(String id, String passwd){
        task = "login";
        this.id = id;
        this.passwd = passwd;
    }

    public void setTaskSignUp(String id, String passwd){
        task = "sign_up";
        this.id = id;
        this.passwd = passwd;
    }

    public void setTaskChangePasswd(String id, String oldPasswd,
                                    String newPasswd){
        task = "change_passwd";
        this.id = id;
        this.oldPasswd = oldPasswd;
        this.newPasswd = newPasswd;
    }

    public void setTaskAddFavoriteList(String id, String titleSinger,
                                       String album){
        task = "add_favorite_list";
        this.id = id;
        this.titleSinger = titleSinger;
        this.album = album;
    }

    public void setTaskDeleteFavoriteList(String id, String titleSinger){
        task = "delete_favorite_list";
        this.id = id;
        this.titleSinger = titleSinger;
    }

    public void setTaskAddPlayList(String id, String titleSinger,
                                   String album){
        task = "add_play_list";
        this.id = id;
        this.titleSinger = titleSinger;
        this.album = album;
    }

    public void setTaskDeletePlayList(String id, String titleSinger){
        task = "delete_play_list";
        this.id = id;
        this.titleSinger = titleSinger;
    }

    public void setTaskAddWishList(String id, String titleSinger,
                                   String album, String link){
        task = "add_wish_list";
        this.id = id;
        this.titleSinger = titleSinger;
        this.album = album;
        this.link = link;
    }

    public void setTaskDeleteWishList(String id, String titleSinger){
        task = "delete_wish_list";
        this.id = id;
        this.titleSinger = titleSinger;
    }

    public void setTaskFeedback(String userIdTime, String content){
        task = "send_feedback";
        this.userIdTime = userIdTime;
        this.content = content;
    }

    public void setTaskSendComment(String titleSinger, String id, String time,
                                   String content, String score){
        task = "send_comment";
        this.titleSinger = titleSinger;
        this.id = id;
        this.time = time;
        this.content = content;
        this.score = score;
    }

    public void setTaskUpdateUserInfo(String id, String passwd, String email,
                                      String photo, String whatsUp){
        task = "update_user_info";
        this.id = id;
        this.passwd = passwd;
        this.email = email;
        this.photo = photo;
        this.whatsUp = whatsUp;
    }

    public void setTaskGetUserInfo(String id){
        task = "get_user_info";
        this.id = id;
    }

    public void setTaskGetComment(String titleSinger){
        task = "get_comment";
        this.titleSinger = titleSinger;
    }

    public void setTaskGetFavoriteMusic(String titleSinger){
        task = "get_favorite_music";
        this.titleSinger = titleSinger;
    }

    public void setTaskGetPlayMusic(String titleSinger){
        task = "get_play_music";
        this.titleSinger = titleSinger;
    }

    public void setTaskGetWishMusic(String titleSinger){
        task = "get_wish_music";
        this.titleSinger = titleSinger;
    }

}
