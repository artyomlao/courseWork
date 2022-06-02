package controller;

import util.HibernateUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            new HibernateUtil();
            while(true){
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
                System.out.println("Пользователь присоединился, Server IP: " + socket.getInetAddress().getHostName());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

