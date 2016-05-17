package de.fhdw.bfws114a.Communication;


public class SimpleDataExchange {

    // create SimpleDataExchange object when you initialize your app data

    public SimpleDataExchange() {
        // TO DO
    }


    // call onResume() in activity.onResume to do any work necessary when activity resumes

    public void onResume() {
        // TO DO
    }


    // call onPause() in activity.onPause to any work necessary when activity pauses

    public void onPause() {
        // TO DO
    }


    // send bytes in data to peer identified by macAddress

    public void send(MacAddress macAddress, byte[] data) {
        // TO DO
    }


    // register a dataReceiveListener to get notified when data is received

    public void setOnDataReceiveListener(DataReceiveListener dataReceiveListener) {
        // TO DO
    }


    // register a peersChangedListener to get notified when the list of reachable peers has changed

    public void setOnPeersChangedListener(PeersChangedListener peersChangedListener) {
        // TO DO
    }

}
