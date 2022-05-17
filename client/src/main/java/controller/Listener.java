package controller;

import java.io.*;
import java.net.Socket;

public class Listener{
    private static Socket socket;
    private static BufferedReader is;
    private static BufferedWriter os;

    public Listener() {
        try {
            socket = new Socket("127.0.0.1", 8000);
            System.out.println("законнектились");
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String listen(){
        String message = null;
        try {
            message = is.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        return message;
    }
//    @Override
//    public void run() {
//        try {
//            while(socket.isConnected()){
//                String message = null;
//                message = is.readLine();
//                System.out.println(message);
//                ResponseChecker responseChecker = new ResponseChecker();
//                responseChecker.checkResponseType(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void send(String json) {
        try {
            os.write(json + "\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
