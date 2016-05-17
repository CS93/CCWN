package de.fhdw.bfws114a.Communication;

public class MacAddress {

    private String mMacAddress;

    public MacAddress(String address) {
        checkMacAddress(address);
        mMacAddress = address;
    }

    public String getMacAddress(){
        return mMacAddress;
    }

    public void setMacAddress(String macAddress){
        checkMacAddress(macAddress);
        mMacAddress = macAddress;
    }

    @Override
    public String toString(){
        return mMacAddress;
    }

    private void checkMacAddress(String macAddress) {
        // TO DO: check format of mac address
        if (macAddress == null) throw new IllegalArgumentException("mac address cannot be empty");
    }

}
