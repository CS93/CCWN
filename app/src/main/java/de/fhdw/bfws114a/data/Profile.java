package de.fhdw.bfws114a.data;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Editable;

import java.io.Serializable;

/**
 * Created by Carsten on 28.04.2016.
 */
public class Profile implements Serializable {
    //attributes: name, status, image
    private String mName;
    private String mStatus;
    private Drawable mImage;


    public Profile(Drawable mImage, String mName, String mStatus) {
        this.mImage = mImage;
        this.mName = mName;
        this.mStatus = mStatus;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable mImage) {
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

