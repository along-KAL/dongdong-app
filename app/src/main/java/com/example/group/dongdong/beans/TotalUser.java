package com.example.group.dongdong.beans;

import android.util.Log;

import com.admom.mygreendaotest.DaoSession;
import com.admom.mygreendaotest.DayUserDao;
import com.admom.mygreendaotest.TotalUserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 * 总列表,其余子列表都在其中
 */

@Entity
public  class  TotalUser {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "years")
    private String years;//年份
    @Property(nameInDb = "month")
    private String month;//月份
    @Property(nameInDb = "day")
    private String day;//日期
    @Property(nameInDb = "week")
    private String week;//星期几
    @Property(nameInDb = "pace")
    private float pace;//步伐
    @Property(nameInDb = "eight")
    private float eight;//体重
    @Property(nameInDb = "sex")
    private boolean sex;//性别,true男,false女
    @Property(nameInDb = "age")
    private int age;//年龄
    @Unique
    private Long dayTag;
    @ToMany(joinProperties ={
            @JoinProperty(name = "dayTag",referencedName = "totalUserTag")
    })
    @OrderBy("current ASC")
    private List<DayUser> dayList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1562820308)
    private transient TotalUserDao myDao;


    public void setDayList(List<DayUser> dayList) {
        this.dayList=new ArrayList<>();
        this.dayList.addAll(dayList);
        Log.i("TAG", "----------->totaluset中:" +dayList.size());
    }

    @Generated(hash = 2091489903)
    public TotalUser() {
    }

    @Generated(hash = 1061210540)
    public TotalUser(Long id, String years, String month, String day, String week, float pace, float eight, boolean sex,
            int age, Long dayTag) {
        this.id = id;
        this.years = years;
        this.month = month;
        this.day = day;
        this.week = week;
        this.pace = pace;
        this.eight = eight;
        this.sex = sex;
        this.age = age;
        this.dayTag = dayTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public float getPace() {
        return pace;
    }

    public void setPace(float pace) {
        this.pace = pace;
    }

    public float getEight() {
        return eight;
    }

    public void setEight(float eight) {
        this.eight = eight;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getSex() {
        return this.sex;
    }

    public long getDayTag() {
        return dayTag;
    }

    public void setDayTag(long dayTag) {
        this.dayTag = dayTag;
    }

    public void setDayTag(Long dayTag) {
        this.dayTag = dayTag;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 739499603)
    public List<DayUser> getDayList() {
        if (dayList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DayUserDao targetDao = daoSession.getDayUserDao();
            List<DayUser> dayListNew = targetDao._queryTotalUser_DayList(dayTag);
            synchronized (this) {
                if (dayList == null) {
                    dayList = dayListNew;
                }
            }
        }
        return dayList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1010399236)
    public synchronized void resetDayList() {
        dayList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1895984343)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTotalUserDao() : null;
    }
    
}
