package com.example.group.dongdong.beans;

import java.util.List;

/**
 * Created by kongalong on 2017/1/10.
 */

public class SportsTrainPlanBean {

    private int mWeek;
    private int mDay;

    private int mSlowRunTime;
    private int mWalkTime;
    private int mRepeatCount;

    private long mTotalTime;


    private List<ListBean> mListBeans;


    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public List<ListBean> getListBeans() {
        return mListBeans;
    }

    public void setListBeans(List<ListBean> listBeans) {
        mListBeans = listBeans;
    }

    public int getRepeatCount() {
        return mRepeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
    }

    public int getSlowRunTime() {
        return mSlowRunTime;
    }

    public void setSlowRunTime(int slowRunTime) {
        mSlowRunTime = slowRunTime;
    }

    public int getWalkTime() {
        return mWalkTime;
    }

    public void setWalkTime(int walkTime) {
        mWalkTime = walkTime;
    }

    public int getWeek() {
        return mWeek;
    }

    public void setWeek(int week) {
        mWeek = week;
    }

    public long getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(long totalTime) {
        mTotalTime = totalTime;
    }

    public static class ListBean{

        private String mSportType;

        private long mSportsTotalTime;


        public long getSportsTotalTime() {
            return mSportsTotalTime;
        }

        public void setSportsTotalTime(long sportsTotalTime) {
            mSportsTotalTime = sportsTotalTime;
        }

        public String getSportType() {
            return mSportType;
        }

        public void setSportType(String sportType) {
            mSportType = sportType;
        }
    }


}
