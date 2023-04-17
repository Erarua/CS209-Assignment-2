package cn.edu.sustech.cs209.chatting.common;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static volatile List<String> userList = new ArrayList<>();


    public static synchronized List<String> getUserList() {
        return UserList.userList;
    }
    public static synchronized void addUser(String username){
        UserList.userList.add(username);
    }
}
