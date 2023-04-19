package cn.edu.sustech.cs209.chatting.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chat {
    private ChatType chatType;
    private String chatName;
    private List<String> members;
    private List<Message> messageList;

    public Chat(ChatType type, String chatName){
        this.chatType = type;
        this.chatName = chatName;
        this.messageList = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String memberString(){
        String ret = Arrays.toString(members.toArray());
        ret = ret.replace("[", "");
        ret = ret.replace("]", "");
        return ret;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void addMessage(Message message){
        messageList.add(message);
    }

    public void addMember(String member){
        members.add(member);
    }

    public String getThree() {
        StringBuilder res = new StringBuilder(members.get(0));
        for(int i=1;i<3;i++){
            res.append(",").append(members.get(i));
        }
        return res.toString();
    }
}
