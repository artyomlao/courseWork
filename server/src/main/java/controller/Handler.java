package controller;

import model.Request;
import model.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Handler extends Thread{
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private Socket socket;

    Handler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            while(socket.isConnected()){
                Request request = (Request) is.readObject();
                switch(request.getRequestType()){
                    case REGISTRATION:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
