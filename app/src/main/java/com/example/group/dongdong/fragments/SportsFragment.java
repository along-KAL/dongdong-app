package com.example.group.dongdong.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.group.dongdong.module.sports.adapter.SportsPagerAdapter;
import com.example.group.dongdong.commom.BaseFragment;
import com.example.group.dongdong.commom.Contants.Constant;
import com.example.group.dongdong.module.sports.activitys.GPSSportsActivity;
import com.example.group.dongdong.module.sports.activitys.TrainPlanActivity;
import com.example.group.dongdong.services.StepService;
import com.example.group.dongdong.widget.SportsBeginTrainView;
import com.example.group.dongdong.widget.SportsDiagramView;
import com.example.group.dongdong.widget.SportsStepCountView;
import com.example.group.dongdong.widget.SportsTextView;
import com.example.group.dongdong.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SportsFragment extends BaseFragment implements Handler.Callback {

    @BindView(R.id.sports_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.sportsStepText1)
    SportsTextView mSportsTextView1;

    @BindView(R.id.sportsStepText2)
    SportsTextView mSportsTextView2;

    @BindView(R.id.sportsStepText3)
    SportsTextView mSportsTextView3;


    @BindView(R.id.diagram_view)
    SportsDiagramView mSportsDiagramView;

    @BindView(R.id.hot_layout)
    LinearLayout mHotLayout;

    @BindView(R.id.kilometres_layout)
    LinearLayout mKilometresLayout;


    private List<View> mViews;

    //SportsTextView的x坐标
    private int mSportsTextView1X;
    private int mSportsTextView2X;
    private int mSportsTextView3X;
    //SportsTextView的宽度
    private int mSportsTextViewWidth;
    //屏幕的宽度
    private int mWidth;

    //
    private int mSportsTextView1MoveDis;
    private int mSportsTextView3MoveDis;
    private SportsStepCountView mSportsStepCountView;
    private SportsBeginTrainView mSportsBeginTrainView1;
    private SportsBeginTrainView mSportsBeginTrainView3;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sports;
    }

    @Override
    protected void initView(View ret) {
        init();
        //获取屏幕的宽度
        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;

        //获取某个mSportsTextView2的宽度并计算联动需要的数值
        mSportsTextView2.post(new Runnable() {
            @Override
            public void run() {
                mSportsTextViewWidth = mSportsTextView2.getWidth();
                mSportsTextView1X = 0;
                mSportsTextView2X = mWidth/2-mSportsTextViewWidth/2;
                mSportsTextView3X = mWidth-mSportsTextViewWidth;

                mSportsTextView1MoveDis = (mWidth/2-mSportsTextViewWidth/2);
                mSportsTextView3MoveDis = (mWidth/2+mSportsTextViewWidth/2)/2;

                mSportsTextView1.setX((int)(mSportsTextView1X));
                mSportsTextView2.setX((int)(mSportsTextView2X));
                mSportsTextView3.setX((int)(mSportsTextView3X));

            }
        });

       initViewPager();

       initmSportsTextView();
    }

    private void initmSportsTextView() {

        mSportsTextView1.setTextView1("大卡");
        mSportsTextView1.setTextView2(String.valueOf(9));
        mSportsTextView1.setTextView3("今日热量消耗");
        mSportsTextView1.setTextColor(Color.parseColor("#fa8932"));


        mSportsTextView2.setTextView1("活跃时间");
        mSportsTextView2.setTextView2("0h 1m");
        mSportsTextView2.setTextColor(Color.parseColor("#FFAEDF73"));

        mSportsTextView3.setTextView1("公里");
        mSportsTextView3.setTextView2(String.valueOf(0.2));
        mSportsTextView3.setTextView3("今日公里数");
    }

    private void initViewPager() {

        mSportsBeginTrainView1 = new SportsBeginTrainView(this.getContext());
        mSportsBeginTrainView1.setText2("新的训练");

        mSportsStepCountView = new SportsStepCountView(this.getContext());
        mSportsStepCountView.setGradeText("不活跃");
        mSportsStepCountView.setTargetCountText(10000);
        mSportsStepCountView.setTodyStepCountText(423);


        mSportsBeginTrainView3 = new SportsBeginTrainView(this.getContext());
        mSportsBeginTrainView3.setBitmap1(((BitmapDrawable)getResources()
                .getDrawable(R.mipmap.icon_pacer)).getBitmap());
        mSportsBeginTrainView3.setBitmap2(Bitmap.createBitmap(1,1, Bitmap.Config.ALPHA_8));
        mSportsBeginTrainView3.setText2("GPS运动");

        mViews = new ArrayList<>();
        mViews.add(mSportsBeginTrainView1);
        mViews.add(mSportsStepCountView);
        mViews.add(mSportsBeginTrainView3);

        PagerAdapter adapter = new SportsPagerAdapter(this.getContext(),mViews);
        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(1);

    }

    @Override
    protected void initEvent() {

        //viewpager事件
        initViewPagerEvent();
        //
        initCustomViewEvent();
    }

    private void initCustomViewEvent() {
        mSportsStepCountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = shakeAnimation(5,1300);
                mSportsStepCountView.startAnimation(animation);
            }
        });

        mSportsBeginTrainView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), TrainPlanActivity.class));
            }
        });

        mSportsBeginTrainView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                getActivity().startActivity(new Intent(getActivity(), GPSSportsActivity.class));

            }
        });
    }

    private void initViewPagerEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //联动
                mSportsTextView1.setX((int)(mSportsTextView1X-
                        mSportsTextView1MoveDis*
                                (position-1+positionOffset)));

                mSportsTextView2.setX((int)(mSportsTextView2X-mWidth*
                        (position-1+positionOffset)));

                mSportsTextView3.setX((int)(mSportsTextView3X-
                        mSportsTextView3MoveDis*
                                (position-1+positionOffset)));

                //渐变
                mSportsTextView2.setAlpha(1-(Math.abs(position-1+positionOffset))*3);

                mSportsDiagramView.setAlpha(1-(Math.abs(position-1+positionOffset))*3);

                if(position-1+positionOffset<0){
                    mSportsTextView1.setTextView1Alpha(1-(Math.abs(position-1+positionOffset))*3);
                    mSportsTextView1.setTextView3Alpha((Math.abs(position-1+positionOffset))*3);

                    mHotLayout.setAlpha((Math.abs(position-1+positionOffset))*3);
                }else{
                    mSportsTextView3.setTextView1Alpha(1-(Math.abs(position-1+positionOffset))*3);
                    mSportsTextView3.setTextView3Alpha((Math.abs(position-1+positionOffset))*3);

                    mKilometresLayout.setAlpha((Math.abs(position-1+positionOffset))*3);
                }

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    Animation animation = scaleAnimation(300);
                    mSportsBeginTrainView1.startAnimation(animation);
                }else if(position == 2){
                    Animation animation = scaleAnimation(300);
                    mSportsBeginTrainView3.startAnimation(animation);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     第一个参数是拉动次数,第二个参数是多长时间内抖动完
     */
    public  Animation shakeAnimation(int counts,int duration){
        Animation translateAnimation = new TranslateAnimation(0, 50, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    public  Animation scaleAnimation(int duration){
        Animation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setInterpolator(new AnticipateInterpolator());
        scaleAnimation.setDuration(duration);
        scaleAnimation.setRepeatCount(1);//设置重复次数
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        return scaleAnimation;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initAdapter() {

    }

    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL = 500;
    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Handler delayHandler;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                // 更新界面上的步数
                mSportsStepCountView.setTodyStepCountText(msg.getData().getInt("step"));
                delayHandler.sendEmptyMessageDelayed(Constant.REQUEST_SERVER, TIME_INTERVAL);
                break;
            case Constant.REQUEST_SERVER:
                try {
                    Message msg1 = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                    msg1.replyTo = mGetReplyMessenger;
                    messenger.send(msg1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
        return false;
    }


    private void init() {
        delayHandler = new Handler(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupService();
    }

    private void setupService() {

        Intent intent = new Intent(this.getActivity(), StepService.class);
        this.getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        this.getActivity().startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getActivity().unbindService(conn);
    }
}
