package cn.edu.sustech.cs209.chatting.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        ServerSocket serverSocket = new ServerSocket(8090);
        Server server = new Server(serverSocket);
        server.keepListen();
    }
}
