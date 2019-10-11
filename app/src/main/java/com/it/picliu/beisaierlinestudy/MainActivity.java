package com.it.picliu.beisaierlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        FrameLayout framelayout = findViewById(R.id.framelayout);
        MyBeisaierLIneView myBeisaierLIneView = new MyBeisaierLIneView(this);
        framelayout.addView(myBeisaierLIneView);

        MyBeiSaierLineViewTwoPoint myBeiSaierLineViewTwoPoint = new MyBeiSaierLineViewTwoPoint(this);
        framelayout.addView(myBeiSaierLineViewTwoPoint);

        MyBeisaierLineRect myBeisaierLineRect = new MyBeisaierLineRect(this);
        framelayout.addView(myBeisaierLineRect);
    }
}
