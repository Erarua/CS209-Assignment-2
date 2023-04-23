package cn.edu.sustech.cs209.chatting.client;

import cn.edu.sustech.cs209.chatting.common.Message;
import cn.edu.sustech.cs209.chatting.common.MessageType;
import cn.edu.sustech.cs209.chatting.common.UserList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class Connector implements Runnable {
    public String username;
    private Socket socket;
    private static ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Controller controller;

    @Override
    public void run() {
        try{
            connect();
        } catch (IOException e){
            this.controller.onServerClose();
            return;
        }
        try{
            while(socket.isConnected()) {
                Message message = (Message) inputStream.readObject();
                if(message.getMessageType() == MessageType.NOTIFICATION){
                    UserList.setUserList(Arrays.asList(message.getData().split(", ")));
                    this.controller.setCurrentOnlineCnt();
                    System.out.println(UserList.getUserList());
                }
                else if(message.getMessageType() == MessageType.PRIVATE){
                    this.controller.handleReceive(message);
                }
                else if(message.getMessageType() == MessageType.GROUP){
                    this.controller.handleReceive(message);
                }
            }
        } catch (ClassNotFoundException | IOException e){
            this.controller.onServerClose();
            return;
        }
    }

    public Connector(String username, Controller controller){
        this.username = username;
        this.controller = controller;
        try{
            this.socket = new Socket("localhost", 8090);
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e){
            this.controller.onServerClose();
            return;
        }
    }

    public void connect() throws IOException {
        Message msg = new Message(MessageType.CONNECTED, this.username, "server", "hello");
        outputStream.writeObject(msg);
    }

    public static void send(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
