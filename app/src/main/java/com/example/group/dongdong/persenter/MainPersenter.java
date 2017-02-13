package com.example.group.dongdong.persenter;

import com.example.group.dongdong.beans.TotalUser;
import com.example.group.dongdong.utils.GreenDaoUtils;

import org.greenrobot.greendao.Property;

import java.util.List;

import com.example.group.dongdong.modle.MainModle;
import com.example.group.dongdong.ui.view.MainView;

/**
 * Created by Administrator on 2016/12/31.
 */

public class MainPersenter extends BasePersenter{
    private MainModle mModle;
    private MainView mView;

    public MainPersenter(MainView view) {
        mView = view;
        mModle=new MainModle();
    }

    //查询数据获取数据源
    public void showInfo(Property property){
        List<TotalUser> users = mModle.getTotalData(property);
        mView.getUserData(users);
    }

    //增添数据调用的方法
    public void inisertData(int age,float pace,float eight,float duration,
              float distance, boolean sex){
        GreenDaoUtils.insertData(age,pace,eight,duration,distance,sex);
    }

    //更新数据调用的方法
    public void upData(int age,float pace,float eight
            ,float duration,float distance,String current,boolean sex){
//        GreenDaoUtils.upData(age,pace,eight,duration,distance,current,sex);
    }
}
