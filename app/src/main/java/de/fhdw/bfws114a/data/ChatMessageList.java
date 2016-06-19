package de.fhdw.bfws114a.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Carsten Schlender.
 */

public class ChatMessageList implements Iterable<ChatMessage>,Serializable{
    private ArrayList<ChatMessage> mMessageList;

    public ChatMessageList() {
        this.mMessageList = new ArrayList<>();
    }

    public ChatMessageList(ArrayList<ChatMessage> messageList) {
        this.mMessageList = messageList;
    }



    public void add(ChatMessage message){
        mMessageList.add(message);
    }

    public ArrayList<ChatMessage> getMessageList() {
        return mMessageList;
    }

    public void setMessageList(ArrayList<ChatMessage> messageList) {
        this.mMessageList = messageList;
    }

    @Override
    public Iterator<ChatMessage> iterator(){
        return mMessageList.iterator();
    }

}
