package com.example.chens.PlaymySong.server;
import java.net.*;

import java.io.*;


public class DefaultSocketClient  extends Thread
        implements SocketClientInterface, SocketClientConstants{
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket sock;
    private String strHost;
    private int iPort;

    private DatabaseControl dbCtr;

    public DefaultSocketClient() {

    }

    public DefaultSocketClient(String strHost, int iPort) {
        setPort (iPort);
        setHost (strHost);
        dbCtr = new DatabaseControl();
        dbCtr.createDatabase();
    }

    /**
     * start
     */
    public void run(){
        if (openConnection()){
            handleSession();
            closeSession();
        }
    }

    public void init() {
        dbCtr = new DatabaseControl();
        dbCtr.createDatabase();
    }

    /**
     * connection
     */
    public boolean openConnection(){
        try {
            reader = new ObjectInputStream(sock.getInputStream());
            writer = new ObjectOutputStream(sock.getOutputStream());
        }
        catch (Exception e){
            System.err.println("Unable to obtain stream to/from " + strHost);
            return false;
        }
        return true;
    }


    /**
     * used to deal with connection
     */
    public void handleSession(){
        dbCtr = new DatabaseControl();
        System.out.println ("Handling session with " + strHost + ":" + iPort);
        Object command = null;
        try {
            command = reader.readObject();
        } catch (ClassNotFoundException | IOException e1) {
            System.out.println(e1);
        }
        if (command instanceof String) {
            String[] params = command.toString().split(",");
            if (params[0].equals("login")) {
                System.out.println("Login request, id: " + params[1]);
                int result = dbCtr.login(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("sign_up")) {
                System.out.println("Sign up request, id: " + params[1]);
                int result = dbCtr.signUp(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("change_passwd")) {
                System.out.println("Change passwd request, id: " + params[1]);
                int result = dbCtr.changePasswd(params[1], params[2], params[3]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("add_favorite_list")) {
                System.out.println("Add favorite list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.addFavoriteList(params[1], params[2], params[3]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("delete_favorite_list")) {
                System.out.println("Delete favorite list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.deleteFavoriteList(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("add_play_list")) {
                System.out.println("Add play list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.addPlayList(params[1], params[2], params[3]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("delete_play_list")) {
                System.out.println("Delete play list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.deletePlayList(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("add_wish_list")) {
                System.out.println("Add wish list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.addWishList(params[1], params[2], params[3],
                        params[4]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("delete_wish_list")) {
                System.out.println("Delete wish list request, id: " + params[1]
                        + " title - singer: " + params[2]);
                int result = dbCtr.deleteWishList(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("send_feedback")) {
                System.out.println("Send feedback request, id-time: "
                        + params[1]);
                int result = dbCtr.sendFeedback(params[1], params[2]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("send_comment")) {
                System.out.println("Send comment request, title-singer: "
                        + params[1]);
                int result = dbCtr.sendComment(params[1], params[2], params[3],
                        params[4], params[5]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("update_user_info")) {
                System.out.println("Send update user info request, id: "
                        + params[1]);
                int result = dbCtr.updateUserInfo(params[1], params[2], params[3],
                        params[4], params[5]);
                sendOutput(String.valueOf(result));
            } else if (params[0].equals("get_user_info")) {
                System.out.println("Send get user info request, id: "
                        + params[1]);
                String result = dbCtr.getUserInfo(params[1]);
                sendOutput(result);
            } else if (params[0].equals("get_comment")) {
                System.out.println("Send get comment request, title - Singer: "
                        + params[1]);
                String result = dbCtr.getComment(params[1]);
                sendOutput(result);
            } else if (params[0].equals("get_favorite_music")) {
                System.out.println("Send get favorite music request, "
                        + "title - Singer: " + params[1]);
                String result = dbCtr.getFavoriteMusic(params[1]);
                sendOutput(result);
            } else if (params[0].equals("get_play_music")) {
                System.out.println("Send get play music request, "
                        + "title - Singer: " + params[1]);
                String result = dbCtr.getPlayMusic(params[1]);
                sendOutput(result);
            } else if (params[0].equals("get_wish_music")) {
                System.out.println("Send get wish music request, "
                        + "title - Singer: " + params[1]);
                String result = dbCtr.getWishMusic(params[1]);
                sendOutput(result);
            }

        }

    }

    public void sendOutput(String strOutput){
        try {
            writer.writeObject(strOutput);
        }
        catch (Exception e){
            System.out.println("Error writing to " + strHost);
        }
    }


    /**
     * get input
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
     * set host
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

    /**
     * set socket
     * @param sock
     */
    public void setSocket(Socket sock) {
        this.sock = sock;
    }

    /**
     * get socket
     * @return
     */
    public Socket getSocket() {
        return this.sock;
    }

}
