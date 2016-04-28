package de.fhdw.bfws114a.Communication;

import de.fhdw.bfws114a.dataInterface.JsonParser;

/**
 * Created by Carsten on 27.04.2016.
 */
public class CommunicationObject {

    JsonParser mJsonParser;

    public CommunicationObject(){
        mJsonParser = new JsonParser();
    }


    public void sendMessage(String text) {
        mJsonParser.test();
    }
}
