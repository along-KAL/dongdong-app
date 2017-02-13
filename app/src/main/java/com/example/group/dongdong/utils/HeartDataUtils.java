package com.example.group.dongdong.utils;

/**
 * 心率计算工具类
 * 男：（（年龄＊0.2017 ＋ 体重＊0.09036 ＋ 心率＊0.6309）－ 55.0969）＊时间分钟 ／ 4.184
 * 女：（（年龄＊0.074 ＋ 体重＊0.05741 ＋ 心率＊0.4472）－ 20.4022）＊时间分钟 ／ 4.184
 * Created by Administrator on 2017/1/6.
 */

public class HeartDataUtils {

    //大卡计算公式
    public static int initHeart(int age, float eight, float totalPace, float totalDuration, boolean sex) {
       float averageHeart=0;//目标心率
        int car=0;

        //平均步伐
        float averagePace = totalPace / totalDuration;

        if(averagePace>=60&&averagePace<=80){
            averageHeart= (float) ((200-age)*((averagePace-60)/2*0.01+0.6));

        }else if(averagePace<60){
            averageHeart= (float) ((200-age)*(0.6-(60-averagePace)/2*0.01));
        }else if(averagePace>80){
            averageHeart= (float) ((200-age)*((averagePace-80)/2*0.01+0.7));
        }

        if(sex==true){
            car= (int) (((age*0.2017+eight*0.09036+averageHeart*0.6309)-55.0969)*totalDuration/4.184);
        }else {
            car= (int) (((age*0.074+eight*0.05741+averageHeart*0.4472)-20.4022)*totalDuration/4.184);
        }

        return car;
    }
}
