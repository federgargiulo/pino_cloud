package it.pliot.equipment.io;

import java.util.Date;

public class SyncCheckpointsTO {

    private String id;

    private Date lastTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
