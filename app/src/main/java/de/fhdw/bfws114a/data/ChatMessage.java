package de.fhdw.bfws114a.data;

import java.io.Serializable;

/**
 * Created by Carsten Schlender.
 */

public class ChatMessage implements Serializable {
    private boolean left;
    private String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
