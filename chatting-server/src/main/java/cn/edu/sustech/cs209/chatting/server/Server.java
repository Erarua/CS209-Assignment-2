package cn.edu.sustech.cs209.chatting.server;

import cn.edu.sustech.cs209.chatting.common.Message;
import cn.edu.sustech.cs209.chatting.common.MessageType;
import cn.edu.sustech.cs209.chatting.common.UserList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private volatile Map<String, ClientService> clients;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
//        UserList.createList();
        this.serverSocket = serverSocket;
        this.clients = new HashMap<>();
//        UserList.addUser("123");
    }

    public void keepListen() {
        System.out.println("The Server is listening for client...");
        while (true) {
            try {

                Socket socket = this.serverSocket.accept();
                ClientService clientService = new ClientService(socket);
                clientService.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    private class ClientService extends Thread {
        private String username;
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;

        public ClientService(Socket socket) {
            try {
                this.socket = socket;
                this.inputStream = new ObjectInputStream(socket.getInputStream());
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message clientMsg = (Message) inputStream.readObject();
//                    System.out.println(
//                            clientMsg.getSentBy() + " " + clientMsg.getSendTo() + " " + clientMsg.getData());
                    if(clientMsg.getMessageType() == MessageType.CONNECTED){
                        this.username = clientMsg.getSentBy();
                        System.out.println(
                                clientMsg.getSentBy() + " " + clientMsg.getSendTo() + " " + clientMsg.getData());
                        UserList.addUser(this.username);
                        System.out.println(UserList.getUserList());
                        System.out.println(UserList.listString());
                        clients.put(this.username, this);
                        clients.forEach((s, clientService) -> {
                            clientService.sendUserList();
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Client No Connection!");
                    break;
                }
            }
        }

        public synchronized void sendFrom(String username) {

        }

        public void sendUserList(){
            Message message = new Message(MessageType.NOTIFICATION,
                    "server", this.username, UserList.listString());
            try {
                outputStream.writeObject(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }


}
