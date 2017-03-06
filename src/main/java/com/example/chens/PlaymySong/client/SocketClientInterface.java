package com.example.chens.PlaymySong.client;

public interface SocketClientInterface {

        boolean openConnection();
        void handleSession();
        void closeSession();

}
