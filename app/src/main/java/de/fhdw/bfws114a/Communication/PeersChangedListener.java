package de.fhdw.bfws114a.Communication;

public interface PeersChangedListener {

    // returns list of mac addresses of all reachable peers

    public MacAddressList onPeersChanged();

}
