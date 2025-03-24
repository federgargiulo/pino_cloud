package it.pliot.equipment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import java.util.Date;

@Entity
public class CronLock {

    @Id
    private String task_name;

    private Boolean locked;

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public long getVersion() {
        return version;
    }

    @Version
    private long version = 0;

    private Date lastRun;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }




    public void setVersion(long version) {
        this.version = version;
    }

    public Date getLastRun() {
        return lastRun;
    }

    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }
}
