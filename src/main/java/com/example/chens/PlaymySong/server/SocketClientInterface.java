package com.example.chens.PlaymySong.server;


public interface SocketClientInterface {
    boolean openConnection();
    void handleSession();
    void closeSession();
}
