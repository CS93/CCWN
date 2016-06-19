package de.fhdw.bfws114a.data;
/**
 * Created by Carsten Schlender.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class MacAddressList implements Iterable<MacAddress>, Serializable {

    private ArrayList<MacAddress> mMacAddressList;

    public MacAddressList() {
        mMacAddressList = new ArrayList<MacAddress>();
    }

    public int size() {
        return mMacAddressList.size();
    }

    public boolean isEmpty() { return size() == 0; }

    public MacAddress getMacAddressByIndex(int index) {
        return mMacAddressList.get(index);
    }

    public void add(MacAddress macAdress) {
        mMacAddressList.add(macAdress);
    }

    public ArrayList<MacAddress> getMacAddressList() { return mMacAddressList; }

    @Override
    public Iterator<MacAddress> iterator(){
        return mMacAddressList.iterator();
    }

    public String[] getStringArray(){
        String[] result = new String[mMacAddressList.size()];
        for(int i=0;i<mMacAddressList.size();i++){
            result[i] = mMacAddressList.get(i).getMacAddress();
        }
        return result;
    }

}