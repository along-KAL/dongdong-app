package com.example.group.dongdong.utils;

import android.content.Context;
import android.util.Log;

import com.admom.mygreendaotest.DaoSession;
import com.admom.mygreendaotest.DayUserDao;
import com.admom.mygreendaotest.TotalUserDao;
import com.example.group.dongdong.beans.DayUser;
import com.example.group.dongdong.beans.TotalUser;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 封装的GreenDao数据库帮助类
 * Created by Administrator on 2016/12/30.
 */
public class GreenDaoUtils {
    private static Context mContext;
    private static String years;
    private static String month;
    private static String week;
    private static String day;
    private static String hour;
    private static Calendar c;
    private static long i=0;
    private static int lastHour=0;

    public GreenDaoUtils(Context context) {
        this.mContext = context;
    }

    /**
     * 插入数据库，没有插入的数据使用null代替,
     * ????????数据库只插入一次
     * @param age       年龄
     * @param pace      步伐
     * @param eight     体重
     * @param duration  走的时间
     * @param distance  走的距离
     * @param sex       性别
     */
    public static void insertData(int age,float pace, float eight
                        , float duration, float distance, boolean sex){
        initDate();//创建日期

            //创建总列表
            Long dayUserTag=Long.parseLong(years+month+day);
        Log.i("TAG", "----------->插入的标签是:" +dayUserTag);
            TotalUser total=new TotalUser(null,years,month,day,week,
                    pace,eight,sex,age,dayUserTag);

            //创建当天列表信息
            DaoSession daoSession = GreenDaoManager.getInstance(mContext).getDaoMaster().newSession();
            TotalUserDao totalUserDao = daoSession.getTotalUserDao();

            List<DayUser> dayList=new ArrayList<>();

            if(Integer.parseInt(hour)>lastHour||Integer.parseInt(hour)==0){
                Long totalTag=Long.parseLong(years+month+day+hour);
                DayUser dayUser=new DayUser(null,hour,duration,distance,totalTag);
                saveDayUser(dayUser);
                dayList.add(dayUser);
                lastHour=Integer.parseInt(hour);
            }
            total.setDayList(dayList);
            totalUserDao.insertOrReplace(total);
    }

    public static void saveDayUser(DayUser dayUser){
        GreenDaoManager.getInstance(mContext).getDaoMaster().newSession()
                .getDayUserDao().insertOrReplace(dayUser);
    }

    /**
     * 更新数据，对于不需要更新的数据可以使用null代替,
     * boolean类型的参数使用false代替
     * @param age       年龄
     * @param pace      步伐
     * @param eight     体重
     * @param duration  走的时间
     * @param distance  行走的距离
     * @param current   更新当前时间(注意:只更新当前的小时数)
     * @param sex       性别
     */
    public static void upData(int age,float pace,float eight
            ,float duration,float distance,String current,boolean sex,long dayTag){

        initDate();//更新数据
        Long dayUserTag=Long.parseLong(years+month+day);

        List<TotalUser> users = getUserDao().loadAll();
        TotalUser total = new TotalUser(users.get(0).getId(),years,month,day,
                week,pace,eight,sex,age,dayTag);

        List<DayUser> dayList = total.getDayList();
        for (int j = 0; j < dayList.size(); j++) {
            DayUser dayUser=dayList.get(j);

            getDayDao().update(dayUser);
        }

        getUserDao().update(total);
        total.refresh();
    }

    /**
     * 删除用户所有信息
     */
    public static void deleteAllData(){
        getUserDao().deleteAll();
        getDayDao().deleteAll();
    }

    /**
     * 根据用户类删除用户信息,两个如果不删除可以设置为null
     * @param total
     */
    public static void deleteData(TotalUser total,DayUser dayUser){
        getUserDao().delete(total);
        getDayDao().delete(dayUser);
        total.refresh();
    }

    /**
     * 根据传入的Tag删除响应的实体类
     * @param totalTag
     */
    public static void deleteTag(Long totalTag){
        getUserDao().deleteByKey(totalTag);
    }


    /**
     * 查询数据库，这里返回的是一个泛型集合，
     * 具体需要什么可以通过循环来获取需要的相应的值
     * @return
     */
    public static List<TotalUser> queryData(){
        List<TotalUser> users = getUserDao().loadAll();
        return users;
    }

    public static List<TotalUser> getSelectTotalUser(Long tag,String currDay){
        return getUserDao().queryBuilder().where(TotalUserDao.Properties
                .DayTag.eq(tag)).limit(Integer.parseInt(currDay)).list();
    }


    /**
     * 通过标签查询dayuser
     * @param tag
     * @return
     */
    public static List<DayUser> getDayusers(long tag){
        List<DayUser> dayList = getUserDao().load(tag).getDayList();
        return dayList;
    }

    /**
     * 查询所有的dayuser
     * @return
     */
    public static List<DayUser> getAllDayuser(){
        return getDayDao().loadAll();
    }

    /**
     * 获取具体的某一时刻数据
     * @param tag 当天日期具体标签
     * @param current 某一时刻小时（hour）
     * @return
     */
    public static List<DayUser> getSelectDayUser(Long tag,String current){
        return getDayDao().queryBuilder().where(DayUserDao.Properties
                .TotalUserTag.eq(tag)).limit(Integer.parseInt(current)).list();
    }


    /**
     * 根据id查询TotalUser数据
     * @param id
     * @return
     */
    public static TotalUser queryTotalId(Long id){
        TotalUser totalUser = getUserDao().loadByRowId(id);
        return totalUser;
    }

    /**
     * 根据id查询DayUser
     * @param id
     * @return
     */
    public static DayUser queryDayId(Long id){
        DayUser dayUser = getDayDao().loadByRowId(id);
        return dayUser;
    }

    /**
     * 通过查询条件获取List<TotalUser>
     * 查询条件通过 TotalUserDao.Properties。。。获取
     * @param dayUserTag
     * @return
     */
    public static List<TotalUser> queryTotalUser(Property dayUserTag){

        Query<TotalUser> build = getUserDao().queryBuilder().orderAsc(dayUserTag).build();
        List<TotalUser> list = build.list();
        return list;
    }

    /**
     * 通过查询条件获取List<DayUser>
     * 查询条件通过 DayUserDao.Properties...获取
     * @param property
     * @return
     */
    public static List<DayUser> queryDayUser(Property property){
        Query<DayUser> dayBuild = getDayDao().queryBuilder().orderAsc(property).build();
        List<DayUser> dayUserList = dayBuild.list();
        return dayUserList;
    }


    private static TotalUserDao getUserDao(){
        return GreenDaoManager.getInstance(mContext).getDaoSession().getTotalUserDao();
    }

    private static DayUserDao getDayDao(){
        return GreenDaoManager.getInstance(mContext).getDaoSession().getDayUserDao();
    }

    private static void initDate() {
        c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        years=String.valueOf(c.get(Calendar.YEAR));//获取当前年份
        month=String.valueOf(c.get(Calendar.MONTH)+1);//获取当前月份
        week=String.valueOf(c.get(Calendar.DAY_OF_WEEK));//获取当前周
        day=String.valueOf(c.get(Calendar.DAY_OF_MONTH));//获取当前日期
        hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));//获取当前时间小时

        if("1".equals(week)){
            week="星期日";
        }else if("2".equals(week)){
            week="星期一";
        }else if("3".equals(week)){
            week="星期二";
        }else if("4".equals(week)){
            week="星期三";
        }else if("5".equals(week)){
            week="星期四";
        }else if("6".equals(week)){
            week="星期五";
        }else if("7".equals(week)){
            week="星期六";
        }
    }
}
