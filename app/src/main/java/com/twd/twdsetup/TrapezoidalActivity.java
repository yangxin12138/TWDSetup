package com.twd.twdsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 梯形校正页面布局
 * 监听实现 遥控器上下左右键的点击事件
 */
public class TrapezoidalActivity extends AppCompatActivity {

    private final static String TAG = "TrapezoidalActivity";
    private ImageView dot_left_up;
    private ImageView dot_right_up;
    private ImageView dot_left_down;
    private ImageView dot_right_down;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView(){
        dot_left_up = (ImageView) findViewById(R.id.iv_left_up);
        dot_right_up = (ImageView) findViewById(R.id.iv_right_up);
        dot_left_down = (ImageView) findViewById(R.id.iv_left_down);
        dot_right_down = (ImageView) findViewById(R.id.iv_right_down);

        dot_left_up.setVisibility(View.VISIBLE);
        dot_right_up.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (action == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_DPAD_UP:
                    //处理上键事件
                    Log.i(TAG,"触发处理上键事件");
                    goUp();
                    setDotsVisible(View.VISIBLE, View.VISIBLE,View.GONE,View.GONE);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    //处理下键事件
                    Log.i(TAG,"触发处理下键事件");
                    goDown();
                    setDotsVisible(View.GONE,View.GONE,View.VISIBLE,View.VISIBLE);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    //处理左键事件
                    Log.i(TAG,"触发处理左键事件");
                    goLeft();
                    setDotsVisible(View.VISIBLE,View.GONE,View.VISIBLE,View.GONE);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    //处理右键事件
                    Log.i(TAG,"触发处理右键事件");
                    goRight();
                    setDotsVisible(View.GONE,View.VISIBLE,View.GONE,View.VISIBLE);
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void goLeft(){
        Log.i("function:goLeft()","左键方法");
    }

    private void goRight(){
        Log.i("function:goRight()","右键方法");
    }

    private void goDown(){
        Log.i("function:goDown()","下键方法");
    }

    private void goUp(){
        Log.i("function:goUp()","上键方法");
    }

    private void setDotsVisible(int left_up,int right_up,int left_down,int right_down){
        dot_left_up.setVisibility(left_up);
        dot_right_up.setVisibility(right_up);
        dot_left_down.setVisibility(left_down);
        dot_right_down.setVisibility(right_down);
    }
}