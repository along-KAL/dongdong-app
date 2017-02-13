package com.example.group.dongdong.modle;

import com.example.group.dongdong.beans.DayUser;
import com.example.group.dongdong.beans.TotalUser;
import com.example.group.dongdong.utils.GreenDaoUtils;

import org.greenrobot.greendao.Property;

import java.util.List;

/**
 * Created by Administrator on 2016/12/31.
 */

public class MainModle extends BaseModle{

    /**
     * 通过传入Property来在presenter中实现逻辑运算
     * TotalUserDao.Properties。。。获取
     * @param property
     */
    public List<TotalUser> getTotalData(Property property){

        List<TotalUser> totalUsers = GreenDaoUtils.queryTotalUser(property);

        return totalUsers;
    }

    /**
     * 通过传入条件获取DayUser
     * DayUserDao.Properties...查询条件获取方式
     * @param dayUserTag
     * @return
     */
    public List<DayUser> getDayUser(Property dayUserTag){
        List<DayUser> dayUsers = GreenDaoUtils.queryDayUser(dayUserTag);
        return dayUsers;
    }
}
