package com.example.group.dongdong.module.my.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.admom.mygreendaotest.DayUserDao;
import com.example.group.teamproject2.R;
import com.example.group.dongdong.beans.DayUser;
import com.example.group.dongdong.beans.TotalUser;
import com.example.group.dongdong.utils.GreenDaoUtils;
import com.example.group.dongdong.utils.HeartDataUtils;
import com.example.group.dongdong.utils.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.example.group.dongdong.widget.customs.CustomIndicator;
import com.example.group.dongdong.widget.customs.PageV1;
import com.example.group.dongdong.widget.customs.PageV2;
import com.example.group.dongdong.widget.customs.PageV4;
import com.example.group.dongdong.widget.customs.WidgetView;
import com.example.group.dongdong.activitys.RecordeActivity;

import static com.example.group.dongdong.widget.Current.BACK_NUM;
import static com.example.group.dongdong.widget.Current.HIST_NUM;
import static com.example.group.dongdong.widget.Current.PAGE_NUM;
import static com.example.group.dongdong.widget.Current.SET_NUM;
import static com.example.group.dongdong.widget.Current.TOTAL_NUM;
import static com.example.group.dongdong.widget.Current.WIDGET_NUM;

/**
 * Created by Administrator on 2016/12/31.
 */
public class MyFragAdapter extends RecyclerView.Adapter{
    private FragmentActivity context;
    private List<TotalUser> mUsers;
    private List<View> vData;
    private MyPagerAdapter mMeAdapter;
    private static String years;
    private static String month;
    private static String day;
    private static String hour;
    private float mBmi;
    private float yBmi;
    private static Tencent mTencent;
    private boolean isServerSideLogin=false;
    private Handler mHandler ;
    private UserInfo mInfo;



    public MyFragAdapter(FragmentActivity activity, List<TotalUser> users) {
        context=activity;
        mUsers=users;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TOTAL_NUM;
        }else if(position==1){
            return PAGE_NUM;
        }else if(position==2){
            return WIDGET_NUM;
        }else if(position==3){
            return HIST_NUM;
        }else if(position==4){
            return SET_NUM;
        }else{
            return BACK_NUM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ret=null;
        Log.i("TAG", "----------->onCreateViewHolder:");
        switch (viewType){
            case TOTAL_NUM:
                ret= LayoutInflater.from(context).inflate(R.layout.total_item,parent,false);
                return new MeHolder(ret);

            case PAGE_NUM:
                ret=LayoutInflater.from(context).inflate(R.layout.page_item,parent,false);
                return new PageHolder(ret);

            case WIDGET_NUM:
                ret=LayoutInflater.from(context).inflate(R.layout.widget_item,parent,false);
                return new WightHolder(ret);

            case HIST_NUM:
                ret=LayoutInflater.from(context).inflate(R.layout.hist_item,parent,false);
                return new HistoryHolder(ret);
            case SET_NUM:
                ret=LayoutInflater.from(context).inflate(R.layout.set_item,parent,false);
                return new SetHolder(ret);

            case BACK_NUM:
                ret=LayoutInflater.from(context).inflate(R.layout.back_item,parent,false);
                return new BackHolder(ret);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MeHolder){//头条目
            //这里暂时用假数据

            ((MeHolder) holder).number.setText("动动号:j72984298");

            ((MeHolder) holder).totalLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mTencent = Tencent.createInstance("1105870165", context);
                    if (!mTencent.isSessionValid()) {
                        Animation shake = AnimationUtils.loadAnimation(context,
                                R.anim.shake);
                        onClickLogin();
                        v.startAnimation(shake);


                      mHandler =  new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                if (msg.what == 0) {
                                    JSONObject response = (JSONObject) msg.obj;
                                    if (response.has("nickname")) {
                                        try {
                                            ((MeHolder) holder).name.setText(response.getString("nickname"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if(msg.what == 1){
                                    Bitmap bitmap = (Bitmap)msg.obj;
                                    ((MeHolder) holder).meImg.setImageBitmap(bitmap);
                                }
                            }

                        };

                    }
                }
            });

        }else if(holder instanceof PageHolder){//viewPager条目
            //获取当天数据的信息
            TotalUser totalUser = mUsers.get(0);

            initPage(totalUser);//初始化控件
            ((PageHolder) holder).mCustomIndicator.setCount(vData.size());
            ((PageHolder) holder).mViewPager.setCurrentItem(0);
            mMeAdapter = new MyPagerAdapter(context, vData);
            ((PageHolder) holder).mViewPager.setAdapter(mMeAdapter);
            ((PageHolder) holder).mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    ((PageHolder) holder).mCustomIndicator.setMoveX(position,positionOffset);
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });


        }else if(holder instanceof WightHolder){//我的体重条目

            TotalUser totalUser = mUsers.get(mUsers.size()-1);
            float eight = totalUser.getEight();
            ((WightHolder) holder).widget.setText(String.valueOf(eight));

            //bt的三种监听状态
            widgetClick( ((WightHolder) holder).widgetBt,
                    ((WightHolder) holder).widgetBt2,
                    ((WightHolder) holder).widgetBt3,
                    ((WightHolder) holder).nearDay,
                    ((WightHolder) holder).mWidgetView,
                    ((WightHolder) holder).upData,
                    ((WightHolder) holder).bmi);


        }else if(holder instanceof HistoryHolder){
            ((HistoryHolder) holder).ka.setText("340大卡");
            Calendar c=Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            ((HistoryHolder) holder).time.setText(year+"年"+month+"月"+day+"日"+hour);

        }else if(holder instanceof SetHolder){

            ((SetHolder) holder).setText.setText("账户类型");

        }else if(holder instanceof BackHolder){
            ((BackHolder) holder).backText.setText("数据备份");
        }
    }

    private void widgetClick(final Button bt, final Button bt2, final Button bt3,
                             final TextView nearDay, WidgetView widgetView,
                             final TextView upData, final TextView bmiData) {


        //当前年月日
        final String currentDate=years+"-"+month+"-"+day;

        //先用假数据看一下
        List<Float> bmis=new ArrayList<>();
        List<String> days=new ArrayList<>();
        for (int i = 0; i < 7; i++) {

            days.add(i+"天");
        }
        bmis.add(7.0f);
        bmis.add(7.0f);
        bmis.add(7.0f);
        bmis.add(7.0f);
        bmis.add(9.0f);
        bmis.add(9.0f);
        bmis.add(9.0f);

        widgetView.setBmi(bmis,days);
        final TotalUser totalUser = mUsers.get(mUsers.size()-1);

        getCurrentDay();

        //三种监听状态
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Map<String, String>> date = NearlyDateUtils.getDate(currentDate, -1);
//                String yyear = date.get(1).get("year");
//                String ymonth = date.get(1).get("month");
//                String yday = date.get(1).get("day");
//                String yTag=yyear+ymonth+yday;
//
//                List<TotalUser> yTotalUser = GreenDaoUtils.getSelectTotalUser(Long.parseLong(yTag), yday);
//
//                float contrast = totalUser.getEight() - yTotalUser.get(0).getEight();
//                //当天bmi值
//                mBmi = (float) (totalUser.getEight()/1.73/1.73);
//                //前一天bmi值
//                yBmi = (float) (yTotalUser.get(0).getEight()/1.73/1.73);
//                if(contrast >0){
//                    bt.setBackgroundColor(Color.parseColor("#fa8c05"));
//                }else {
//                    bt.setBackgroundColor(Color.parseColor("#01e737"));
//                }
//
//                bt.setText(String.valueOf(contrast));
//                upData.setText(String.valueOf(mBmi - yBmi));
//                bmiData.setText(String.valueOf(mBmi));
                nearDay.setText("上次录入");

                bt.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.VISIBLE);
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<TotalUser> userList=new ArrayList<>();
//                String current=Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH;
//                List<Map<String, String>> mapDate = NearlyDateUtils.getDate(current, -7);
//                for (int i = 0; i < mapDate.size(); i++) {
//                    Map<String, String> map = mapDate.get(i);
//                    String year = map.get("year");
//                    String month = map.get("month");
//                    String day = map.get("day");
////                    TotalUser totalUserlist = initCurrentUsers(Integer.parseInt(year),
////                            Integer.parseInt(month), Integer.parseInt(day));
////                    userList.add(totalUserlist);
//                }
//
//                int numberUser=0;
//                for (int i = 0; i < userList.size(); i++) {
//                    numberUser+=userList.get(i).getEight();
//                }
//
//                int everageUser = numberUser / userList.size();
//                float contrast=totalUser.getEight()-everageUser;
//
//                mBmi= (float) (totalUser.getEight()/1.73/1.73);//当天bmi值
//                yBmi= (float) (everageUser/1.73/1.73);//前一天bmi值
//                if(contrast >0){
//                    bt2.setBackgroundColor(Color.parseColor("#fa8c05"));
//                }else {
//                    bt2.setBackgroundColor(Color.parseColor("#01e737"));
//                }
//                bt2.setText(String.valueOf(contrast));
//                upData.setText(String.valueOf(mBmi-yBmi));
//                bmiData.setText(String.valueOf(mBmi));
                nearDay.setText("最近7天");

                bt.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.VISIBLE);
            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<TotalUser> userList=new ArrayList<>();
//                String current=Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DAY_OF_MONTH;
//                List<Map<String, String>> mapDate = NearlyDateUtils.getDate(current, -30);
//                for (int i = 0; i < mapDate.size(); i++) {
//                    Map<String, String> map = mapDate.get(i);
//                    String year = map.get("year");
//                    String month = map.get("month");
//                    String day = map.get("day");
////                    TotalUser totalUserlist = initCurrentUsers(Integer.parseInt(year),
////                            Integer.parseInt(month), Integer.parseInt(day));
////                    userList.add(totalUserlist);
//                }
//
//                int numberUser=0;
//                for (int i = 0; i < userList.size(); i++) {
//                    numberUser+=userList.get(i).getEight();
//                }
//
//                int everageUser = numberUser / userList.size();
//                float contrast=totalUser.getEight()-everageUser;
//
//                mBmi= (float) (totalUser.getEight()/1.73/1.73);//当天bmi值
//                yBmi= (float) (everageUser/1.73/1.73);//前一天bmi值
//                if(contrast >0){
//                    bt3.setBackgroundColor(Color.parseColor("#fa8c05"));
//                }else {
//                    bt3.setBackgroundColor(Color.parseColor("#01e737"));
//                }
//                bt3.setText(String.valueOf(contrast));
//                upData.setText(String.valueOf(mBmi-yBmi));
//                bmiData.setText(String.valueOf(mBmi));
                nearDay.setText("最近30天");

                bt.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.INVISIBLE);
            }
        });
    }

    //第二条目ViewPager显示数据内容
    private void initPage(TotalUser totalUser) {
        vData = new ArrayList<>();
        View v1=LayoutInflater.from(context).inflate(R.layout.v1_item,null);
        PageV1 pageV1= (PageV1) v1.findViewById(R.id.page_V1_curtomView);
        TextView time= (TextView) v1.findViewById(R.id.page_v1_time);
        TextView maxPace= (TextView) v1.findViewById(R.id.page_v1_numb);

        String timeStr = totalUser.getYears() + "年" + totalUser.getMonth() + "月" + totalUser.getDay();
        time.setText(timeStr);

        //获取当前日期
        getCurrentDay();

        long dayTag = Long.parseLong(years+month+day+hour);
        List<DayUser> dayList = GreenDaoUtils.getSelectDayUser(dayTag,hour);
        Log.i("TAG", "----------->使用自身获取数据:" +dayList);
        float maxDistance=0;
        for (int i = 0; i < dayList.size(); i++) {
            float distance = dayList.get(i).getDistance();
            if(distance>maxDistance){
                maxDistance=distance;
            }
        }

        //获取一日走的最大值
        String resultPace = getDataPace(String.valueOf(maxDistance));
        maxPace.setText(resultPace);

        List<DayUser> dayUsers = GreenDaoUtils.queryDayUser(DayUserDao.Properties.Distance);
        for (int i = 0; i < dayUsers.size(); i++) {
            float distance = dayUsers.get(i).getDistance();
            if(maxDistance==distance){
                String current = dayUsers.get(i).getCurrent();
                pageV1.setData((int) maxDistance,current);
            }
            Log.i("TAG", "----------->这个结果是:" +resultPace);
        }

        pageV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecordeActivity.class);
                context.startActivity(intent);
            }
        });


        vData.add(v1);

        View v2=LayoutInflater.from(context).inflate(R.layout.v2_item,null);
        PageV2 pageV2= (PageV2) v2.findViewById(R.id.page_V2_curtomView);
        TextView avePeace= (TextView) v2.findViewById(R.id.page_v2_numb);
        List<DayUser> dayList2 = GreenDaoUtils.getSelectDayUser(dayTag,hour);
        Log.i("TAG", "----------->适配器daylist2:" +dayList2);
        int totalDistance=0;
        for (int i = 0; i < dayList2.size(); i++) {
            float distance = dayList2.get(i).getDistance();
            String current = dayList2.get(i).getCurrent();
            pageV2.setData((int) distance,Integer.parseInt(current));
            totalDistance+=distance;
        }
        Log.i("TAG", "----------->总数totalDistance:" +totalDistance);
        int averagePace = totalDistance / 24;
        avePeace.setText(String.valueOf(averagePace));
        vData.add(v2);

        View v3=LayoutInflater.from(context).inflate(R.layout.v3_item,null);
        TextView numb= (TextView) v3.findViewById(R.id.page_v3_number);
        TextView showInfo= (TextView) v3.findViewById(R.id.page_v3_textShowInfo);
        numb.setText("15");
        showInfo.setText("15%的用户");
        vData.add(v3);

        View v4=LayoutInflater.from(context).inflate(R.layout.v4_item,null);
        PageV4 pageV4= (PageV4) v4.findViewById(R.id.v4_custom);
        TextView v4Numb= (TextView) v4.findViewById(R.id.page_v4_numb);
        v4Numb.setText("001");
        pageV4.setCar(500,200);
        vData.add(v4);

        View v5=LayoutInflater.from(context).inflate(R.layout.v5_item,null);
        TextView titleTime= (TextView) v5.findViewById(R.id.page_v5_titleTime);
        TextView v5pace= (TextView) v5.findViewById(R.id.page_v5_pace);
        TextView v5car= (TextView) v5.findViewById(R.id.page_v5_car);
        TextView v5hour= (TextView) v5.findViewById(R.id.page_v5_hour);
        TextView v5kilo= (TextView) v5.findViewById(R.id.page_v5_kilo);

        String oneDay = mUsers.get(0).getDay();//使用首日日期
        String oneMonth = mUsers.get(0).getMonth();//使用首日月份
        String oneYears = mUsers.get(0).getYears();//使用首日年份
        String oneTotalTime=oneYears+"年"+oneMonth+"月"+oneDay+"日";
        titleTime.setText(oneTotalTime);

        //总步数
        float totalPace=0;
        //总时长
        float totalDuration=0;
        //总里程
        float totalKilo=0;
        for (int i = 0; i < mUsers.size(); i++) {
            for (int j = 0; j < mUsers.get(i).getDayList().size(); j++) {
                //行走步数
                float distance = mUsers.get(i).getDayList().get(j).getDistance();
                totalPace+=distance;
                //行走时长
                float duration = mUsers.get(i).getDayList().get(j).getDuration();
                totalDuration+=duration;
            }
        }

        v5pace.setText(String.valueOf(totalPace));
        v5hour.setText(String.valueOf(totalDuration));
        //一步长
        float pace = mUsers.get(0).getPace();
        DecimalFormat df=new DecimalFormat(".0");
        totalKilo=pace*totalPace*totalDuration/100000;
        String format = df.format(totalKilo);
        v5kilo.setText(format);

        int age = mUsers.get(0).getAge();//年龄
        float eight = mUsers.get(0).getEight();//体重
        boolean sex = mUsers.get(0).getSex();//性别

        int car = HeartDataUtils.initHeart(age, eight, totalPace, totalDuration, sex);
        v5car.setText(String.valueOf(car));
        vData.add(v5);
    }

    //获取当前日期
    private static void getCurrentDay(){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        //获取当前年份
        years = String.valueOf(c.get(Calendar.YEAR));
        //获取当前月份
        month = String.valueOf(c.get(Calendar.MONTH)+1);
        //获取当前日期
        day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        //获取当前时间小时
        hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
    }


    //逗号分隔数据
    private String getDataPace(String data) {
        String str = String.valueOf(data);
        String sReverse = new StringBuilder(str).reverse().toString();
        String temp="";
        for (int j = 0; j < sReverse.length(); j++) {
            if(j*3+3>sReverse.length()){
                temp+=sReverse.substring(j*3,sReverse.length());
                break;
            }
            temp +=sReverse.substring(j*3,j*3+3)+",";
        }
        if(temp.endsWith(",")){
            temp=temp.substring(0,temp.length()-1);
        }

        String resultPace = new StringBuilder(temp).reverse().toString();

        return resultPace;
    }


    public class MeHolder extends RecyclerView.ViewHolder {
        private ImageView meImg;
        private TextView name,number;
        private RelativeLayout totalLayout;

        public MeHolder(View itemView) {
            super(itemView);
            totalLayout= (RelativeLayout) itemView.findViewById(R.id.total_layout);
            meImg= (ImageView) itemView.findViewById(R.id.total_img);
            name= (TextView) itemView.findViewById(R.id.total_name);
            number= (TextView) itemView.findViewById(R.id.total_numb);
        }
    }

    public class PageHolder extends RecyclerView.ViewHolder {
        private ViewPager mViewPager;
        private CustomIndicator mCustomIndicator;
        public PageHolder(View itemView) {
            super(itemView);
            mViewPager= (ViewPager) itemView.findViewById(R.id.page_viewPage);
            mCustomIndicator= (CustomIndicator) itemView.findViewById(R.id.page_point);
        }
    }

    public class WightHolder extends RecyclerView.ViewHolder {
        private TextView widget,nearDay,upData,bmi;
        private Button widgetBt,widgetBt2,widgetBt3;
        private WidgetView mWidgetView;

        public WightHolder(View itemView) {
            super(itemView);
            widget= (TextView) itemView.findViewById(R.id.widget_wid_numb);
            nearDay= (TextView) itemView.findViewById(R.id.widget_text_show);
            upData= (TextView) itemView.findViewById(R.id.widget_up_text);
            widgetBt= (Button) itemView.findViewById(R.id.widget_bt);
            widgetBt2= (Button) itemView.findViewById(R.id.widget_bt2);
            widgetBt3= (Button) itemView.findViewById(R.id.widget_bt3);
            bmi= (TextView) itemView.findViewById(R.id.widget_bmi_num);
            mWidgetView= (WidgetView) itemView.findViewById(R.id.widget_customView);
        }
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView ka,time;

        public HistoryHolder(View itemView) {
            super(itemView);
            ka= (TextView) itemView.findViewById(R.id.hist_ka);
            time= (TextView) itemView.findViewById(R.id.hist_time);
        }
    }

    public class SetHolder extends RecyclerView.ViewHolder {
        private TextView setText;
        public SetHolder(View itemView) {
            super(itemView);
            setText= (TextView) itemView.findViewById(R.id.set_text);
        }
    }

    public class BackHolder extends RecyclerView.ViewHolder {
        private TextView backText;
        public BackHolder(View itemView) {
            super(itemView);
            backText= (TextView) itemView.findViewById(R.id.back_text);
        }
    }


    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(context, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(context);
                mTencent.login(context, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(context);
            updateUserInfo();
        }
    }


    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread(){

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject)response;
                            if(json.has("figureurl")){
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(context, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }


    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
            Log.i("TAG", "----------->数据有误");
        }
    }


    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(context, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(context, "返回为空", "登录失败");
                return;
            }
            Util.showResultDialog(context, response.toString(), "登录成功");
            // 有奖分享处理
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(context, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(context, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }
}
