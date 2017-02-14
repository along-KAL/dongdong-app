package com.example.group.dongdong.commom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.group.dongdong.R;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.provider.UserDictionary.Words.APP_ID;

public abstract class BaseActivity extends AppCompatActivity {
    private Tencent mTencent;

    private Unbinder mUnbinder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());

        setContentView(getLayoutId());


        //初始化ButterKnife
        mUnbinder = ButterKnife.bind(this);

        //显示toobar
        setSupportActionBar(mToolbar);

        //初始化数据
        initData();


        //初始化View
        initView();

        //初始化事件
        initEvent();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();

    }

    //返回layoutId
    protected abstract  int getLayoutId();


    protected  abstract void initView();


    protected abstract void initEvent();


    protected  abstract void initData();


}
