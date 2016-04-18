package com.obo.androidanimationviews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.obo.androidanimationviews.R;
import com.obo.androidanimationviews.views.DashBoardCircleSeekBar;

public class MainActivity extends AppCompatActivity {
    DashBoardCircleSeekBar mCircleSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleSeekBar = (DashBoardCircleSeekBar) findViewById(R.id.view_circle_seek);
        if (mCircleSeekBar != null) {
            mCircleSeekBar.setDegreenValue(50);
        }

        mCircleSeekBar.setDegreenValue(400);
        mCircleSeekBar.setSmallCircleRedius(100);
        mCircleSeekBar.setStrokeWidth(20);
        mCircleSeekBar.setStartDrawDegreen(60);
        mCircleSeekBar.setStrokeWidth(60);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCircleSeekBar.setDegreenValue((float) Math.random() * 360);
                        }
                    });

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
