package com.twd.twdsetup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TrapezoidalSinglePointActivity extends AppCompatActivity implements View.OnFocusChangeListener , View.OnKeyListener {

    private ImageView single_left_up;
    private ImageView single_right_up;
    private ImageView single_left_down;
    private ImageView single_right_down;

    private boolean dot_canMoved = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezoidal_single_point);
        initView();
    }

    private void initView(){
        single_left_up = (ImageView) findViewById(R.id.iv_single_left_up);
        single_right_up = (ImageView) findViewById(R.id.iv_single_right_up);
        single_left_down = (ImageView) findViewById(R.id.iv_single_left_down);
        single_right_down = (ImageView) findViewById(R.id.iv_single_right_down);

        single_left_up.setOnFocusChangeListener(this);
        single_right_up.setOnFocusChangeListener(this);
        single_left_down.setOnFocusChangeListener(this);
        single_right_down.setOnFocusChangeListener(this);

        single_left_up.setFocusable(true);
        single_left_up.setFocusableInTouchMode(true);
        single_right_up.setFocusable(true);
        single_right_up.setFocusableInTouchMode(true);
        single_left_down.setFocusable(true);
        single_left_down.setFocusableInTouchMode(true);
        single_right_down.setFocusable(true);
        single_right_down.setFocusableInTouchMode(true);

        single_left_up.setOnKeyListener(this);
        single_right_up.setOnKeyListener(this);
        single_left_down.setOnKeyListener(this);
        single_right_down.setOnKeyListener(this);

        single_left_up.requestFocus();
    }

    /**
     * 设置焦点
     * @param left_up
     * @param right_up
     * @param left_down
     * @param right_down
     */
    private void setImageResource(int left_up,int right_up,int left_down,int right_down){
        single_left_up.setImageResource(left_up);
        single_right_up.setImageResource(right_up);
        single_left_down.setImageResource(left_down);
        single_right_down.setImageResource(right_down);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            switch (v.getId()){
                case R.id.iv_single_left_up:
                    //焦点在左上
                    setImageResource(R.drawable.trape_dot,R.drawable.unselected,R.drawable.unselected,R.drawable.unselected);
                    break;
                case R.id.iv_single_right_up:
                    //焦点在右上
                    setImageResource(R.drawable.unselected,R.drawable.trape_dot,R.drawable.unselected,R.drawable.unselected);
                    break;
                case R.id.iv_single_left_down:
                    //焦点在左下
                    setImageResource(R.drawable.unselected,R.drawable.unselected,R.drawable.trape_dot,R.drawable.unselected);
                    break;
                case R.id.iv_single_right_down:
                    //焦点在右下
                    setImageResource(R.drawable.unselected,R.drawable.unselected,R.drawable.unselected,R.drawable.trape_dot);
                    break;
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i("function:onKey()","进onKey,keycode:"+keyCode);
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            if (!dot_canMoved){
                Log.i("function:onKey()","焦点不可移动，执行自定义方法");
                switch (keyCode){
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        Log.i("function:onKey()","执行左键方法,dot_canMoved:"+dot_canMoved);
                        goLeft();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        Log.i("function:onKey()","执行右键方法,dot_canMoved:"+dot_canMoved);
                        goRight();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        Log.i("function:onKey()","执行上键方法,dot_canMoved:"+dot_canMoved);
                        goUp();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        Log.i("function:onKey()","执行下键方法,dot_canMoved:"+dot_canMoved);
                        goDown();
                        return true;
                    case KeyEvent.KEYCODE_ESCAPE:
                        dot_canMoved = true;
                        Log.i("function:onKey()","按下退出键,dot_canMoved:"+dot_canMoved);
                        return true;
                }
            } else if (dot_canMoved){
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    dot_canMoved = false;
                    Log.i("function:onKey()","按下OK键,dot_canMoved="+dot_canMoved);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 自定义方向键代码在此处
     */
    private void goRight() {
        /**
         * 右键
         * TODO:
         */
        Toast.makeText(this, "往右拉", Toast.LENGTH_SHORT).show();
    }

    private void goLeft() {
        /**
         * 左键
         * TODO:
         */
        Toast.makeText(this, "往左拉", Toast.LENGTH_SHORT).show();
    }

    private void goDown() {
        /**
         * 下键
         * TODO:
         */
        Toast.makeText(this, "往下拉", Toast.LENGTH_SHORT).show();
    }

    private void goUp() {
        /**
         * 上键
         * TODO:
         */
        Toast.makeText(this, "往上拉", Toast.LENGTH_SHORT).show();
    }
}
