package com.example.group.dongdong.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * currentDate 获取的是当前具体的年月日
 * nearDate     获取最近日期,正数后n天，负数前n天
 */

public class NearlyDateUtils {
    public static List<Map<String,String>> getDate(String currentDate, int nearDate){
        List<Map<String,String>> dateList=new ArrayList<>();
        Map<String,String> map=new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String mDate = currentDate;
        Calendar cal = Calendar.getInstance();
        Date date = null;

        try {
            date = sdf.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal.setTime(date);

        for (int i = 0; i < nearDate; i++) {
            cal.add(Calendar.DAY_OF_MONTH, i);
            Date fDate = cal.getTime();
            String format = sdf.format(fDate);
            String years = format.substring(0, 4);
            String months = format.substring(5, 7);
            String days = format.substring(8, 10);
            map.put("year",years);
            map.put("month",months);
            map.put("day",days);
            dateList.add(map);
        }
        return dateList;
    }
}
