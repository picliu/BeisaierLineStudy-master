package com.it.picliu.beisaierlinestudy;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * *  @name:picliu
 * *  @date: 2019-10-16
 */
public class WaveActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        initView();
    }

    private void initView() {
        final MyWaveView mywaveview = findViewById(R.id.mywaveview);
        mywaveview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mywaveview.cancelAnimotor();
                mywaveview.changeirectionD();
            }
        });
        mywaveview.startAnimator();

        MyWaveView mywaveview1 = findViewById(R.id.mywaveview1);
        mywaveview1.startAnimator();
    }


}
