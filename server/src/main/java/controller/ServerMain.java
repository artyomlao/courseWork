package controller;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while(true){
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
