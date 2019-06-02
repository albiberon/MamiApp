package com.example.mamiapp;


//DataSync class to passvalue to location related constructor. When location updated, data is updated.

public class DataSyncEvent {
    private final String syncStatusMessage;
    public DataSyncEvent(String syncStatusMessage) {
        this.syncStatusMessage = syncStatusMessage;
    }
    public String getSyncStatusMessage() {
        return syncStatusMessage;
    }
}