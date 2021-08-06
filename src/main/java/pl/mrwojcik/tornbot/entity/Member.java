package pl.mrwojcik.tornbot.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Member implements Serializable {

    private Integer tornId;
    private String username;
    private Integer rpForFaction;
    private Integer daysInFaction;
    private Integer level;
    private Integer yellowFlag;
    private boolean yeet;
    private long lastAction;

    public Integer getTornId() {
        return tornId;
    }

    public void setTornId(Integer tornId) {
        this.tornId = tornId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRpForFaction() {
        return rpForFaction;
    }

    public void setRpForFaction(Integer rpForFaction) {
        this.rpForFaction = rpForFaction;
    }

    public Integer getDaysInFaction() {
        return daysInFaction;
    }

    public void setDaysInFaction(Integer daysInFaction) {
        this.daysInFaction = daysInFaction;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getYellowFlag() {
        return yellowFlag;
    }

    public void setYellowFlag(Integer yellowFlag) {
        this.yellowFlag = yellowFlag;
    }

    public boolean isYeet() {
        return yeet;
    }

    public void setYeet(boolean yeet) {
        this.yeet = yeet;
    }

    public long getLastAction() {
        return lastAction;
    }

    public void setLastAction(Integer timestamp) {
        LocalDateTime lastAction = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
        long daysFromLastActivity = ChronoUnit.DAYS.between(lastAction, LocalDateTime.now());
        this.lastAction = daysFromLastActivity;
    }
}
