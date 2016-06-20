package de.fhdw.bfws114a.data;

import java.io.Serializable;

/**
 * Created by Carsten Schlender.
 */

public class Profile implements Serializable {
    //attributes: id, name, status, image
    private String mMac;
    private String mName;
    private String mStatus;
    private byte[] mImage;


    public Profile(String mMac, String mName, String mStatus, byte[] mImage) {
        this.mMac = mMac;
        this.mImage = mImage;
        this.mName = mName;
        this.mStatus = mStatus;
    }

    public String getMac() {
        return mMac;
    }

    public void setMac(String mMac) {
        this.mMac = mMac;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}

