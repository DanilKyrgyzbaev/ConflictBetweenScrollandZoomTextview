package com.example.conflictbetweenscrollandzoomtextview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private TextView textView;
    private ScrollView scrollView;
    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setTextSize(mRatio + 15);
        scrollView = findViewById(R.id.scrollView);

        textView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getPointerCount() == 1) {
                    Log.d("Scroll","1-pointer touch");
                    v.getParent().requestDisallowInterceptTouchEvent(false);

                }
                if(event.getPointerCount() == 2){
                    Log.d("Zoom","2-pointer touch");
                    int action = event.getAction();
                    int mainaction = action&MotionEvent.ACTION_MASK;
                    if (mainaction == MotionEvent.ACTION_POINTER_DOWN){
                        mBaseDist = getDistance(event);
                        mBaseRatio = mRatio;
                    }else {
                        float scale = (getDistance(event)- mBaseDist)/STEP;
                        float factor = (float) Math.pow(2,scale);
                        mRatio = Math.min(1024.0f,Math.max(0.1f,mBaseRatio*factor));
                        textView.setTextSize(mRatio+15);
                    }
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                }
                return true;
            }
        });

    }

    private int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }
}
