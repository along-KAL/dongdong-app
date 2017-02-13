package com.example.group.dongdong.fragments;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group.dongdong.activitys.ShowActivity;
import com.example.group.dongdong.commom.BaseFragment;
import com.example.group.dongdong.module.my.adapter.MyPagerAdapter;
import com.example.group.dongdong.widget.customs.CustomIndicator;
import com.example.group.dongdong.widget.customs.PaceTableV1;
import com.example.group.dongdong.widget.customs.PaceTableV2;
import com.example.group.dongdong.widget.customs.PaceTableV3;
import com.example.group.teamproject2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyzeFragment extends BaseFragment {
    @BindView(R.id.analyze_tableyLayout)
    public TabLayout mLayout;
    @BindView(R.id.analyze_viewPager)
    public ViewPager mViewPager;
    @BindView(R.id.analyze_custom)
    public CustomIndicator point;

    private List<View> strs;
    private MyPagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analyze;
    }

    @Override
    protected void initView(View v) {
        strs=new ArrayList<>();
        View ret= LayoutInflater.from(getActivity()).inflate(R.layout.analyze1_item,null);
        TextView everNumb= (TextView) ret.findViewById(R.id.analyze_table1_everNumb);
        TextView allNumb= (TextView) ret.findViewById(R.id.analyze_table1_allNumb);
        ImageView rotation= (ImageView) ret.findViewById(R.id.analyze_rotation);
        PaceTableV1 paceTableV1= (PaceTableV1) ret.findViewById(R.id.analyze1_custom);
        everNumb.setText("1062");
        allNumb.setText("7440");

        List<Integer> list=new ArrayList<>();
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(18);
        list.add(18);
        list.add(15);
        list.add(15);

        paceTableV1.setColumnHeight(list);

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                startActivity(intent);
            }
        });

        strs.add(ret);

        View ret2=LayoutInflater.from(getActivity()).inflate(R.layout.analyze2_item,null);
        TextView newsNumb= (TextView) ret2.findViewById(R.id.analyze_table2_newsNumb);
        TextView bmiNumb= (TextView) ret2.findViewById(R.id.analyze_table2_bmiNumb);
        ImageView rotation2= (ImageView) ret2.findViewById(R.id.analyze_rotation2);
        PaceTableV2 tableV2= (PaceTableV2) ret2.findViewById(R.id.analyze2_pageV2_column);
        newsNumb.setText("61.3");
        bmiNumb.setText("20.5");
        List<Integer> list2=new ArrayList<>();
        list2.add(8*3);
        list2.add(8*3);
        list2.add(9*3);
        list2.add(8*3);
        list2.add(8*3);
        list2.add(8*3);
        list2.add(9*3);
        tableV2.setBroken(list2);
        rotation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                startActivity(intent);
            }
        });
        strs.add(ret2);


        View ret3=LayoutInflater.from(getActivity()).inflate(R.layout.analyze3_item,null);
        TextView everNumb3= (TextView) ret3.findViewById(R.id.analyze_table3_everNumb);
        TextView allNumb3= (TextView) ret3.findViewById(R.id.analyze_table3_allNumb);
        ImageView rotation3= (ImageView) ret3.findViewById(R.id.analyze_rotation3);
        PaceTableV3 paceTableV3= (PaceTableV3) ret3.findViewById(R.id.analyze3_custom3);
        everNumb3.setText("39");
        allNumb3.setText("278");
        List<Integer> list3=new ArrayList<>();
        list3.add(10);
        list3.add(10);
        list3.add(10);
        list3.add(18);
        list3.add(18);
        list3.add(15);
        list3.add(15);
        paceTableV3.setColumnHeight(list3);
        rotation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                startActivity(intent);
            }
        });
        strs.add(ret3);

        View ret4=LayoutInflater.from(getActivity()).inflate(R.layout.analyze4_item,null);
        ret4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                startActivity(intent);
            }
        });
        strs.add(ret4);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        TabLayout.Tab tab = mLayout.newTab();
        tab.setText("步数");

        TabLayout.Tab tab2= mLayout.newTab();
        tab2.setText("体重");

        TabLayout.Tab tab3 = mLayout.newTab();
        tab3.setText("热量");

        TabLayout.Tab tab4 = mLayout.newTab();
        tab4.setText("高级");
        mLayout.setTabTextColors(R.color.font_unchecked,R.color.font_checked);
        mLayout.addTab(tab);
        mLayout.addTab(tab2);
        mLayout.addTab(tab3);
        mLayout.addTab(tab4);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new MyPagerAdapter(getActivity(), strs);
        mViewPager.setAdapter(mAdapter);

        point.setCount(strs.size());

        point.setMoveX(0,0);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mLayout));

        mLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                point.setMoveX(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
