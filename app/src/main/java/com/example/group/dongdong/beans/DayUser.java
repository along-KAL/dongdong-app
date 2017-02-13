package com.example.group.dongdong.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2017/1/3.
 * 每天行走的路程，时间
 */

@Entity
public class DayUser {
    @Id
    private Long id;
    @Property(nameInDb = "current")
    private String current;//当前时间
    @Property(nameInDb = "duration")
    private float duration;//时长
    @Property(nameInDb = "distance")
    private float distance;//行走的步数
    @NotNull
    private Long totalUserTag;

    @Generated(hash = 889176876)
    public DayUser() {
    }

    @Generated(hash = 2052024761)
    public DayUser(Long id, String current, float duration, float distance,
            @NotNull Long totalUserTag) {
        this.id = id;
        this.current = current;
        this.duration = duration;
        this.distance = distance;
        this.totalUserTag = totalUserTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public long getTotalUserTag() {
        return this.totalUserTag;
    }

    public void setTotalUserTag(long totalUserTag) {
        this.totalUserTag = totalUserTag;
    }

    public void setTotalUserTag(Long totalUserTag) {
        this.totalUserTag = totalUserTag;
    }

}
