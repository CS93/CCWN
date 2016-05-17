package de.fhdw.bfws114a.Communication;

public interface DataReceiveListener {

    // returns received data and the mac address of the sender

    public void onDataReceive(MacAddress macAddressOfSender, byte[] receivedData);

}
