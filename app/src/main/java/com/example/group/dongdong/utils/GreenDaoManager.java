package com.example.group.dongdong.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.admom.mygreendaotest.DaoMaster;
import com.admom.mygreendaotest.DaoSession;

/**
 * Created by Administrator on 2016/12/30.
 */

public class GreenDaoManager {
    //是否加密
    public static final boolean ENCRYPTED=true;
    private static final String DB_NAME="totaluser_db";
    private DaoMaster.DevOpenHelper devOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance;//单例
    private Context mContext;

    private GreenDaoManager(Context context) {
        this.mContext=context;
        devOpenHelper= new DaoMaster.DevOpenHelper(mContext,DB_NAME);
        getDaoMaster();
        getDaoSession();
    }

    public static GreenDaoManager getInstance(Context context){
        if(mInstance==null){
            synchronized (GreenDaoManager.class){
                if(mInstance==null){
                    mInstance=new GreenDaoManager(context);
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getDaoMaster() {
        if(mDaoMaster==null){
            synchronized (GreenDaoManager.class){
                if(mDaoMaster==null){
                    mDaoMaster=new DaoMaster(getWriteDatabase(mContext));
                }
            }
        }
        return mDaoMaster;
    }

    //获取可写数据库
    private SQLiteDatabase getWriteDatabase(Context context) {
        if(devOpenHelper==null){
            getInstance(context);
        }
        return devOpenHelper.getWritableDatabase();
    }

    //获取可读数据库
    private SQLiteDatabase getReadableDatabase(Context context) {
        if(devOpenHelper==null){
            getInstance(context);
        }
        return devOpenHelper.getReadableDatabase();
    }

    public DaoSession getDaoSession() {
        if(mDaoSession==null){
            mDaoSession=getDaoMaster().newSession();
        }
        return mDaoSession;
    }
}
