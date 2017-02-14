package com.example.group.dongdong.module.sports.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.group.dongdong.utils.CountDownTimer;
import com.example.group.dongdong.widget.SportsGPSLayoutView;
import com.example.group.dongdong.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GPSSportsActivity extends AppCompatActivity implements LocationSource
        , AMapLocationListener {

    @BindView(R.id.gps_swap_btn)
     Button mSwapBtn;
    @BindView(R.id.gps_location_btn)
    Button mLocationBtn;
    @BindView(R.id.gps_linearlayout)
     LinearLayout mLinearlayout;

    @BindView(R.id.gps_SportsGPSLayoutView)
     SportsGPSLayoutView mSportsGPSLayoutView;

    @BindView(R.id.map)
     MapView mMapView;

    @BindView(R.id.gps_distance_text)
     TextView mDistanceText;

    @BindView(R.id.gps_time_text)
     TextView mTimeText;

    @BindView(R.id.gps_hot_text)
     TextView mHotText;

    @BindView(R.id.gps_speed_text)
     TextView mSpeedText;

    @BindView(R.id.gps_step_text)
     TextView mStepText;


    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private Unbinder mUnbinder;
    private MyTimer mTimer;
    private float mDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpssports);
        //初始化ButterKnife
        mUnbinder = ButterKnife.bind(this);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        init();

        initEvnet();

        mTimer = new MyTimer(Integer.MAX_VALUE,1000);
        mTimer.start();
    }

    private AMap aMap;

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
         }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {


        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        etupLocationStyle();
    }

    private void etupLocationStyle(){
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.ic_launcher));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
    }

    private void initEvnet() {

        mSwapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
                alphaAnimation.setDuration(300);
                alphaAnimation.setFillAfter(true);
                mLinearlayout.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mLinearlayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                mSportsGPSLayoutView.setConsumeEvent(true);
                mSportsGPSLayoutView.startInAnimate();

            }
        });

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mStartLat, mStartLong)));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

            }
        });

        mSportsGPSLayoutView.setOnOutListener(new SportsGPSLayoutView.OnOutListener() {
            @Override
            public void onOut() {

              /*  mLinearlayout.setVisibility(View.VISIBLE);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
                alphaAnimation.setDuration(3000);
                alphaAnimation.setFillAfter(true);
                mLinearlayout.startAnimation(alphaAnimation);*/
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
        mUnbinder.unbind();
        mTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }




    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;


    private double mStartLat;
    private double mStartLong;
    private boolean mStartSign = true;
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

                if(mStartSign){
                    mStartLat = amapLocation.getLatitude();
                    mStartLong = amapLocation.getLongitude();
                    mStartSign = false;
                }

                mDistance = AMapUtils.calculateLineDistance(new LatLng(mStartLat, mStartLong)
                        , new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()))/1000;
                //设置距离
                DecimalFormat df=new DecimalFormat("#.##");
                mDistanceText.setText(String.valueOf(df.format(mDistance)));

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }





    class MyTimer extends CountDownTimer {
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

        private long currentTime = 0;
        @Override
        public void onTick(long millisUntilFinished) {

            //格式化时间
            SimpleDateFormat format1=new SimpleDateFormat("HH:mm:ss");
            Date d1=new Date(currentTime+=1000);
            mTimeText.setText(format1.format(d1));

            mSpeedText.setText(String.valueOf(mDistance/currentTime/60));

        }

        @Override
        public void onFinish() {


        }
    }

}
