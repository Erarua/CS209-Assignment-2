package cn.edu.sustech.cs209.chatting.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserList {
    private static volatile List<String> userList = new ArrayList<>();


    public static synchronized List<String> getUserList() {
        return UserList.userList;
    }

    public static synchronized void setUserList(List<String> userList) {
        UserList.userList = userList;
    }

    public static synchronized void addUser(String username){
        UserList.userList.add(username);
    }

    public static synchronized void removeUser(String username){
        UserList.userList.remove(username);
    }

    public static synchronized String listString(){
        String ret = Arrays.toString(userList.toArray());
        ret = ret.replace("[", "");
        ret = ret.replace("]", "");
        return ret;
    }
}
