package com.example.group.dongdong.activitys;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group.teamproject2.R;

import java.util.ArrayList;
import java.util.List;

import com.example.group.dongdong.widget.customs.PaceTableV1;
import com.example.group.dongdong.widget.customs.PaceTableV2;
import com.example.group.dongdong.widget.customs.PaceTableV3;
import com.example.group.dongdong.widget.customs.ShowCustom1;
import com.example.group.dongdong.widget.customs.SingleItemScrollView;
import com.example.group.dongdong.widget.customs.SingleItemScrollView.Adapter;

public class ShowActivity extends AppCompatActivity {
    private SingleItemScrollView mScrollView;
    private TextView showTime,avNumb,allNumb;
    private Button rotation;
    private Adapter mAdapter;
    private LayoutInflater mInflater;
    private List<View> mViews=new ArrayList<>();
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 101:
                   mViews= (List<View>) msg.obj;


                    initAdapter(mViews);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initView();
        initData();

    }

    private void initData() {
        mInflater=LayoutInflater.from(this);
        View showV1 = mInflater.inflate(R.layout.show_one_item,null);
        ShowCustom1 showCustom1= (ShowCustom1) showV1.findViewById(R.id.show_one_custom);
        List<Integer> list=new ArrayList<>();
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(18);
        list.add(18);
        list.add(15);
        list.add(15);
        showCustom1.setColumnHeight(list);
        mViews.add(showV1);

        View showV2=mInflater.inflate(R.layout.show_two_item,null);
        PaceTableV1 paceTableV1= (PaceTableV1) showV2.findViewById(R.id.show_two_custom);
        List<Integer> list2=new ArrayList<>();
        list2.add(10);
        list2.add(10);
        list2.add(10);
        list2.add(18);
        list2.add(18);
        list2.add(15);
        list2.add(15);
        paceTableV1.setColumnHeight(list2);
        mViews.add(showV2);

        View showV3=mInflater.inflate(R.layout.show_three_item,null);
        PaceTableV2 paceTableV2= (PaceTableV2) showV3.findViewById(R.id.show_three_custom);
        List<Integer> list3=new ArrayList<>();
        list3.add(8*3);
        list3.add(8*3);
        list3.add(9*3);
        list3.add(8*3);
        list3.add(8*3);
        list3.add(8*3);
        list3.add(9*3);
        paceTableV2.setBroken(list3);
        mViews.add(showV3);

        View showV4=mInflater.inflate(R.layout.show_four_item,null);
        PaceTableV3 paceTableV3= (PaceTableV3) showV4.findViewById(R.id.show_four_custom);
        List<Integer> list4=new ArrayList<>();
        list4.add(10);
        list4.add(10);
        list4.add(10);
        list4.add(18);
        list4.add(18);
        list4.add(15);
        list4.add(15);
        paceTableV3.setColumnHeight(list4);
        mViews.add(showV4);

        View showV5=mInflater.inflate(R.layout.show_five_item,null);
        PaceTableV1 pageV5= (PaceTableV1) showV5.findViewById(R.id.show_fiv_custom);
        List<Integer> list5=new ArrayList<>();
        list5.add(10);
        list5.add(10);
        list5.add(10);
        list5.add(18);
        list5.add(18);
        list5.add(15);
        list5.add(15);
        pageV5.setColumnHeight(list5);
        mViews.add(showV5);
        Message msg=Message.obtain();
        msg.what=101;
        msg.obj=mViews;
        mhandler.sendMessage(msg);

    }

    private void initAdapter(final List<View> mViews) {

        mAdapter=new Adapter() {
            @Override
            public View getView(SingleItemScrollView parent, int position) {
                Log.i("TAG", "----------->mviews:有多少" +mViews.size());
                Log.i("TAG", "----------->mviews:具体位置" +mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        };

        mScrollView.setAdapter(mAdapter);

        mScrollView.setOnItemClickListener(new SingleItemScrollView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }
        });
    }

    private void initView() {
        mScrollView= (SingleItemScrollView) findViewById(R.id.show_scrollView);
        showTime= (TextView) findViewById(R.id.show_time);
        avNumb= (TextView) findViewById(R.id.show_av_numb);
        allNumb= (TextView) findViewById(R.id.show_all_numb);
        rotation= (Button) findViewById(R.id.show_rotation);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
