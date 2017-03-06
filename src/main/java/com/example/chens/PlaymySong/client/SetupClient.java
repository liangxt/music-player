package com.example.chens.PlaymySong.client;

import java.util.Scanner;


public class SetupClient {
    private static String host = "localhost";
    //private static String host = "192.168.1.152";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\n\nClient starting...\nType help for more "
                + "information");
        while (true) {
            System.out.print("Client>>");
            Scanner scan = new Scanner(System.in);
            String read = scan.nextLine();
            String[] lineSplit = read.trim().split(" ");

            // all commands
            if (lineSplit.length == 2) {
                if (lineSplit[0].toLowerCase().equals("help")) {
                    // help info
                    System.out.println("There are three commands:\n\t1. \n\t2. \n\t3.");
                } else if (lineSplit[0].equals("get_user_info")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskGetUserInfo(lineSplit[1]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("get_comment")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskGetComment(lineSplit[1]);
                    System.out.println(client.runRequest());
//                  client.start();
                }
            } else if (lineSplit.length == 3) {
                System.out.println("Sending " + lineSplit[0] + " request!");
                if (lineSplit[0].equals("login")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskLogin(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("sign_up")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskSignUp(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("delete_favorite_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskDeleteFavoriteList(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("delete_play_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskDeletePlayList(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("delete_wish_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskDeleteWishList(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                }  else if (lineSplit[0].equals("send_feedback")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskFeedback(lineSplit[1], lineSplit[2]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else {
                    // wrong
                    System.out.println("Wrong command!");
                }

            } else if (lineSplit.length == 4) {
                if (lineSplit[0].equals("change_passwd")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskChangePasswd(lineSplit[1], lineSplit[2],
                            lineSplit[3]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("add_favorite_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskAddFavoriteList(lineSplit[1], lineSplit[2],
                            lineSplit[3]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("add_play_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskAddPlayList(lineSplit[1], lineSplit[2],
                            lineSplit[3]);
                    System.out.println(client.runRequest());
//                  client.start();
                }
            } else if (lineSplit.length == 5) {
                if (lineSplit[0].equals("add_wish_list")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskAddWishList(lineSplit[1], lineSplit[2],
                            lineSplit[3], lineSplit[4]);
                    System.out.println(client.runRequest());
//                  client.start();
                }
            } else if (lineSplit.length == 6) {
                if (lineSplit[0].equals("send_comment")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskSendComment(lineSplit[1], lineSplit[2],
                            lineSplit[3], lineSplit[4],
                            lineSplit[5]);
                    System.out.println(client.runRequest());
//                  client.start();
                } else if (lineSplit[0].equals("update_user_info")) {
                    DefaultSocketClient client
                            = new DefaultSocketClient(host, 3000);
                    client.setTaskUpdateUserInfo(lineSplit[1], lineSplit[2],
                            lineSplit[3], lineSplit[4],
                            lineSplit[5]);
                    System.out.println(client.runRequest());
//                  client.start();
                }
            } else {
                // wrong

                System.out.println("Wrong command!");
            }


            //"test.properties"
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }
}
