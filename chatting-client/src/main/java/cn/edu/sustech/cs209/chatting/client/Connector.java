package cn.edu.sustech.cs209.chatting.client;

import cn.edu.sustech.cs209.chatting.common.Message;
import cn.edu.sustech.cs209.chatting.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connector implements Runnable {
    public String username;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Controller controller;

    @Override
    public void run() {
        try{
            connect();
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            while(socket.isConnected()) {
                Message message = (Message) inputStream.readObject();
            }
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

    public Connector(String username, Controller controller){
        this.username = username;
        this.controller = controller;
        try{
            this.socket = new Socket("localhost", 8090);
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        Message msg = new Message(MessageType.CONNECTED, this.username, "server", "hello");
        outputStream.writeObject(msg);
    }
}
