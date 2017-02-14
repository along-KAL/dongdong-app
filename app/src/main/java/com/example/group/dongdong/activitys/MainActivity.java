package com.example.group.dongdong.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.group.dongdong.commom.BaseActivity;
import com.example.group.dongdong.fragments.AnalyzeFragment;
import com.example.group.dongdong.fragments.GroupFragment;
import com.example.group.dongdong.fragments.MyFragment;
import com.example.group.dongdong.fragments.TargetFragment;
import com.example.group.dongdong.R;

import butterknife.BindView;

import com.example.group.dongdong.fragments.SportsFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    private Fragment mTempFragment = null;
    private FragmentManager mManager;

    private SportsFragment mSportsFragment ;
    private MyFragment mMyFragment ;
    private AnalyzeFragment mAnalyzeFragment ;
    private TargetFragment mTargetFragment ;
    private GroupFragment mGroupFragment ;




    @Override
    protected int getLayoutId() {

        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        //初始化4个fragment
        initFragments();
    }

    @Override
    protected void initEvent() {

        //初始化radiogroup点击事件
        initRadioGroupListener();

    }

    @Override
    protected void initData() {

    }

    private void initRadioGroupListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.sports_radio_button:
                        showFragment(mSportsFragment);
                        break;
                    case R.id.my_radio_button:
                        showFragment(mMyFragment);
                        break;
                    case R.id.analyze_radio_button:
                        showFragment(mAnalyzeFragment);
                        break;
                    case R.id.target_radio_button:
                        showFragment(mTargetFragment);
                        break;
                    case R.id.group_radio_button:
                        showFragment(mGroupFragment);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initFragments() {

        //创建四个Fragment对象
        mSportsFragment = new SportsFragment();
        mMyFragment = new MyFragment();
        mAnalyzeFragment = new AnalyzeFragment();
        mTargetFragment = new TargetFragment();
        mGroupFragment = new GroupFragment();

        mManager = getSupportFragmentManager();
        mManager.beginTransaction()
                .add(R.id.container, mSportsFragment)
                .commit();

        mTempFragment = mSportsFragment;

        ((RadioButton)mRadioGroup.getChildAt(2)).setChecked(true);
    }



    public void showFragment(Fragment fragment){

        if(fragment.isAdded()){
            mManager.beginTransaction()
                    .hide(mTempFragment)
                    .show(fragment)
                    .commit();
            mTempFragment = fragment;
        }else{
            mManager.beginTransaction()
                    .hide(mTempFragment)
                    .add(R.id.container,fragment)
                    .commit();
            mTempFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
  /*  @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        mFragmentTouchEvent.onFragmentTouchEvent(ev);

        return super.dispatchTouchEvent(ev);
    }


    FragmentTouchEvent mFragmentTouchEvent;

    public void setFragmentTouchEvent(FragmentTouchEvent fragmentTouchEvent){
        this.mFragmentTouchEvent = fragmentTouchEvent;
    }

    public interface FragmentTouchEvent{

        void onFragmentTouchEvent(MotionEvent event);

    }*/

   /* private RadioGroup mRadioGroup;

    private Fragment mTempFragment = null;
    private List<Fragment> mFragmentList;
    private FragmentManager mManager;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        initFragments();
    }

    @Override
    protected void initEvent() {
        initRadioGroupListener();
    }

    @Override
    protected void initData() {

    }


    private void initRadioGroupListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int index = 0;
                if(checkedId<3){
                    index = checkedId-1;
                }else{
                    index = checkedId-2;
                }
                if(mFragmentList.get(index).isAdded()){
                    mManager.beginTransaction()
                            .hide(mTempFragment)
                            .show(mFragmentList.get(index))
                            .commit();
                    mTempFragment = mFragmentList.get(index);
                }else{
                    mManager.beginTransaction()
                            .hide(mTempFragment)
                            .add(R.id.container,mFragmentList.get(index))
                            .commit();
                    mTempFragment = mFragmentList.get(index);
                }
            }
        });


    }


    private void initFragments() {
        SportsFragment sportsFragment = new SportsFragment();
        MyFragment myFragment = new MyFragment();
        AnalyzeFragment analyzeFragment = new AnalyzeFragment();
        TargetFragment targetFragment = new TargetFragment();
        GroupFragment groupFragment = new GroupFragment();

        mFragmentList = new ArrayList<>();

        mFragmentList.add(sportsFragment);
        mFragmentList.add(myFragment);
        mFragmentList.add(analyzeFragment);
        mFragmentList.add(targetFragment);
        mFragmentList.add(groupFragment);


        mManager = getSupportFragmentManager();
        mManager.beginTransaction()
                .add(R.id.container, mFragmentList.get(0))
                .commit();

        mTempFragment = mFragmentList.get(0);

        ((RadioButton)mRadioGroup.getChildAt(0)).setChecked(true);
    }*/
}

