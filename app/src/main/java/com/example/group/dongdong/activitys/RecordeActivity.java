package com.example.group.dongdong.activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.example.group.dongdong.R;

public class RecordeActivity extends AppCompatActivity {
//    @BindView(R.id.record_recycle)
//    public RecyclerView mRecycleView;
//    private MyRecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorde);
//        initAdapter();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View ret= LayoutInflater.from(this).inflate(R.layout.dialog_item,null);
        Button button= (Button) ret.findViewById(R.id.dialog_buy);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(RecordeActivity.this, PayDemoActivity.class);
                startActivity(intent);
            }
        });

        builder.setView(ret);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    private void initAdapter() {
//        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecycleView.setLayoutManager(manager);
//        mAdapter = new MyRecordAdapter(getApplicationContext());
//        mRecycleView.setAdapter(mAdapter);
//    }
}
