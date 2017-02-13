package com.example.group.dongdong.module.sports.activitys;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.group.dongdong.beans.SportsTrainPlanBean;
import com.example.group.dongdong.commom.BaseActivity;
import com.example.group.dongdong.module.sports.adapter.SportsPagerAdapter;
import com.example.group.dongdong.utils.CountDownTimer;
import com.example.group.dongdong.widget.CustomViewPager;
import com.example.group.dongdong.widget.SportsTrainPlanChoseView;
import com.example.group.dongdong.widget.SportsTrainPlanCircleView;
import com.example.group.dongdong.widget.SportsTrainPlanScheduleView;
import com.example.group.teamproject2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class TrainPlanActivity extends BaseActivity {


    @BindView(R.id.sportsTrainPlanScheduleView)
    SportsTrainPlanScheduleView mSportsTrainPlanScheduleView;

    @BindView(R.id.trainpaln_viewpager)
    CustomViewPager mTrainPlanViewpager;

    @BindView(R.id.text1)
    TextView text1;

    @BindView(R.id.text2)
    TextView text2;

    @BindView(R.id.trainplan_end_btn)
    Button mEndBtn;

    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.trainplan_begin_image)
    ImageView mBeginImage;

    @BindView(R.id.SportsTrainPlanChoseView)
    SportsTrainPlanChoseView mSportsTrainPlanChoseView;
    private List<View> mViews;

    private int mCurrentPosition;


    private boolean mSign = true;
    private MyTimer mTimer;


    private long mConpleteTime = 0;

    private List<SportsTrainPlanBean> mData;

    private int mDayPosition;
    private PagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_plan;
    }

    @Override
    protected void initView() {


        mTimer = new MyTimer(mData.get(0).getListBeans()
                .get(0).getSportsTotalTime(),1000);


        initViewPager();

        initSchduleView();



    }

    private void initSchduleView() {

        mSportsTrainPlanScheduleView.setTotalTime(mData.get(0).getTotalTime());

    }

    private void initViewPager() {

        mViews = new ArrayList<>();

        for (int i = 0; i < mData.get(0).getListBeans().size(); i++) {
            SportsTrainPlanBean.ListBean listBean = mData.get(0).getListBeans().get(i);
            SportsTrainPlanCircleView sportsBeginTrainView1 = new SportsTrainPlanCircleView(this);

            sportsBeginTrainView1.setSportTypeText((i+1)+"/"+mData.get(0).getListBeans().size(),listBean.getSportType(),listBean.getSportsTotalTime());

            mViews.add(sportsBeginTrainView1);

        }

        mAdapter = new SportsPagerAdapter(this,mViews);
        mTrainPlanViewpager.setAdapter(mAdapter);


    }
    @Override
    protected void initEvent() {

        mBeginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mSign){
                    mSign = false;

                    mRelativeLayout.setVisibility(View.GONE);
                    mEndBtn.setVisibility(View.VISIBLE);

                    mTrainPlanViewpager.setScanScroll(false);

                    ((SportsTrainPlanCircleView)mViews.get(mCurrentPosition)).startAnimate();

                    mTimer.start();

                }else{
                    mSign = true;
                    ((SportsTrainPlanCircleView)mViews.get(mCurrentPosition)).pauseAnimate();
                    mTimer.pause();

                }
            }
        });


        mEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.setVisibility(View.VISIBLE);
                mEndBtn.setVisibility(View.GONE);

                mTimer.cancel();

                mTrainPlanViewpager.setScanScroll(true);

                mCompleteTime = 0;

                mSign = true;

                mSportsTrainPlanScheduleView.setCompleteTime(mCompleteTime);

                updataViewPager();

            }
        });

        //viewpager事件
        initViewPagerEvent();

        initChoseEvent();

    }

    private void initChoseEvent() {

        mSportsTrainPlanChoseView.setOnChoseListener(new SportsTrainPlanChoseView.OnChoseListener() {
            @Override
            public void onChose(int position) {
                mDayPosition = position;
                mSportsTrainPlanScheduleView.setTotalTime(mData.get(position).getTotalTime());

                text1.setText("第"+mData.get(position).getWeek()+"周 第"+mData.get(position).getDay()+"天");
                text2.setText("慢跑"+mData.get(position).getSlowRunTime()+"分钟+步行"+mData.get(position).getWalkTime()+"分钟，重复"+mData.get(position).getRepeatCount()+"次。");

                updataViewPager();
            }
        });


    }

    private void updataViewPager(){
        mViews.clear();
        for (int i = 0; i < mData.get(mDayPosition).getListBeans().size(); i++) {
            SportsTrainPlanBean.ListBean listBean = mData.get(mDayPosition).getListBeans().get(i);
            SportsTrainPlanCircleView sportsBeginTrainView1 = new SportsTrainPlanCircleView(TrainPlanActivity.this);

            sportsBeginTrainView1.setSportTypeText((i+1)+"/"+mData.get(mDayPosition).getListBeans().size(), listBean.getSportType(),listBean.getSportsTotalTime());

            mViews.add(sportsBeginTrainView1);

        }

        mAdapter.notifyDataSetChanged();
        mTrainPlanViewpager.setCurrentItem(0);
    }

    private void initViewPagerEvent() {

        mTrainPlanViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;


                mTimer = new MyTimer(mData.get(mDayPosition).getListBeans()
                        .get(mCurrentPosition).getSportsTotalTime(),1000);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        boolean sign = true;

        mData = new ArrayList<>();
        for (int i = 0; i < 8*3; i++) {

            SportsTrainPlanBean stpb = new SportsTrainPlanBean();

            Random random = new Random();
            int i2 = random.nextInt(10)+1;

            stpb.setSlowRunTime(i);
            stpb.setWeek(i/3+1);
            stpb.setDay(i+1);
            stpb.setWalkTime(1);
            stpb.setRepeatCount(i2);
            stpb.setTotalTime(((i+1)*5+10)*1000*60);
            List<SportsTrainPlanBean.ListBean> data = new ArrayList<>();

            for (int i1 = 0; i1 < i2*2+2; i1++) {
                SportsTrainPlanBean.ListBean listBean = new SportsTrainPlanBean.ListBean();
                if(i1 == 0||i1 == i2*2+1){
                    listBean.setSportsTotalTime(5*1000*60);
                    listBean.setSportType("热身");

                }else if(sign){
                    sign = false;
                    listBean.setSportsTotalTime((i+1)*1000*60);
                    listBean.setSportType("慢跑");
                }else{
                    sign = true;
                    listBean.setSportsTotalTime(1*1000*60);
                    listBean.setSportType("走");
                }
                data.add(listBean);
            }

            stpb.setListBeans(data);
            mData.add(stpb);
        }
    }

    private long mCompleteTime = 0;

    class MyTimer extends CountDownTimer{
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            ((SportsTrainPlanCircleView)mViews.get(mCurrentPosition))
                    .setLeftTime(millisUntilFinished-1000);



            mSportsTrainPlanScheduleView.setCompleteTime(mCompleteTime+mData.get(mDayPosition).getListBeans()
                    .get(mCurrentPosition).getSportsTotalTime()-(millisUntilFinished-2000));

        }

        @Override
        public void onFinish() {

            mCompleteTime = mCompleteTime + mData.get(mDayPosition).getListBeans()
                    .get(mCurrentPosition).getSportsTotalTime();

            mTimer.cancel();
            mSign = true;
            mTrainPlanViewpager.setScanScroll(true);
            mTrainPlanViewpager.setCurrentItem(++mCurrentPosition);

            mTrainPlanViewpager.setScanScroll(false);
        }
    }



}
