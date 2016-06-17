package de.fhdw.bfws114a.data;

import java.io.Serializable;

/**
 * Created by Carsten on 19.05.2016.
 */
public class ChatMessage implements Serializable {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}
