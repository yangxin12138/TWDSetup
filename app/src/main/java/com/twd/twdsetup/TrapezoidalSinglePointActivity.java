package com.twd.twdsetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.twd.twdsetup.keystone.SystemPropertiesUtils;
import com.twd.twdsetup.keystone.keystone;
import com.twd.twdsetup.keystone.keystoneOnePoint;
import com.twd.twdsetup.keystone.keystoneTwoPoint;

public class TrapezoidalSinglePointActivity extends AppCompatActivity implements View.OnFocusChangeListener , View.OnKeyListener {

    private final static String TAG = TrapezoidalSinglePointActivity.class.getSimpleName();
    private ImageView single_left_up;
    private ImageView single_right_up;
    private ImageView single_left_down;
    private ImageView single_right_down;

    private TextView text_left_up;
    private TextView text_right_up;
    private TextView text_left_down;
    private TextView text_right_down;
    private TextView textView;

    private boolean dot_canMoved = false; /* 标记方向键不移动焦点 */
    private keystoneOnePoint mKeystone;
    private static int nowPoint = 0;
    public final int MODE_ONEPOINT = 1;
    public final int MODE_TWOPOINT = 0;
    public final int MODE_UNKOWN = -1;
    public final String ORIGIN_POINT = "0,0";
    protected static SharedPreferences prefsDotValue;

    private TypedArray tyar;
    String theme_code = SystemPropertiesUtils.getPropertyColor("persist.sys.background_blue","0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: theme_code:"+theme_code);
        switch (theme_code){
            case "0": //冰激蓝
                this.setTheme(R.style.Theme_IceBlue);
                break;
            case "1": //木棉白
                this.setTheme(R.style.Theme_KapokWhite);
                break;
            case "2": //星空蓝
                this.setTheme(R.style.Theme_StarBlue);
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezoidal_single_point);
        tyar= this.getTheme().obtainStyledAttributes(new int[]{
                R.attr.arrowBackSrc,//0
                R.attr.textColor,//1
                R.attr.trape_double_Src,//2
                R.attr.trape_single_Src,//3
                R.attr.size_src,//4
                R.attr.projection_Src,//5
                R.attr.unselFrameBG,//6
                R.attr.selFrameBG,//7
                R.attr.arrowSrc,//8
                R.attr.backGround,//9
                R.attr.itemSelected,//10
                R.attr.projection_bg,
                R.attr.trape_dots
        });
        initView();
        nowPoint = 0;

        SharedPreferences prefs = this.getSharedPreferences("keystone_mode", Context.MODE_PRIVATE);
        int mode = prefs.getInt("mode",MODE_UNKOWN);
        Log.d("SinglePoint", "TrapezoidalSinglePointActivity mode: "+mode);
        if(mode == MODE_TWOPOINT || mode == MODE_UNKOWN){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("mode",MODE_ONEPOINT);
            editor.apply();
            mKeystone = new keystoneOnePoint(this);
            mKeystone.restoreKeystone();
            resetView();
        }else{
            mKeystone = new keystoneOnePoint(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    /* 初始化页面 */
    @SuppressLint("ResourceType")
    private void initView(){
        single_left_up = (ImageView) findViewById(R.id.iv_single_left_up);
        single_right_up = (ImageView) findViewById(R.id.iv_single_right_up);
        single_left_down = (ImageView) findViewById(R.id.iv_single_left_down);
        single_right_down = (ImageView) findViewById(R.id.iv_single_right_down);

        text_left_up = (TextView) findViewById(R.id.tv_single_left_up);
        text_right_up = (TextView) findViewById(R.id.tv_single_right_up);
        text_left_down = (TextView) findViewById(R.id.tv_single_left_down);
        text_right_down = (TextView) findViewById(R.id.tv_single_right_down);

//        text_left_up.setTextColor(tyar.getColor(1,0));
//        text_right_up.setTextColor(tyar.getColor(1,0));
//        text_left_down.setTextColor(tyar.getColor(1,0));
//        text_right_down.setTextColor(tyar.getColor(1,0));
        //读取数据
        prefsDotValue = this.getSharedPreferences("single_text_value",MODE_PRIVATE);
        text_left_up.setText(prefsDotValue.getString("text_left_up",ORIGIN_POINT));
        text_right_up.setText(prefsDotValue.getString("text_right_up",ORIGIN_POINT));
        text_left_down.setText(prefsDotValue.getString("text_left_down",ORIGIN_POINT));
        text_right_down.setText(prefsDotValue.getString("text_right_down",ORIGIN_POINT));


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
    private void setImageResource(Drawable left_up, Drawable right_up, Drawable left_down, Drawable right_down){
        single_left_up.setImageDrawable(left_up);
        single_right_up.setImageDrawable(right_up);
        single_left_down.setImageDrawable(left_down);
        single_right_down.setImageDrawable(right_down);
    }

    private void setTextVisible(int luVisible,int ruVisible,int ldVisible,int rdVisible){
        text_left_up.setVisibility(luVisible);
        text_right_up.setVisibility(ruVisible);
        text_left_down.setVisibility(ldVisible);
        text_right_down.setVisibility(rdVisible);
    }

    private void resetView(){
        text_left_up.setText(ORIGIN_POINT);
        saveTextValue("text_left_up",ORIGIN_POINT);

        text_right_up.setText(ORIGIN_POINT);
        saveTextValue("text_right_up",ORIGIN_POINT);

        text_left_down.setText(ORIGIN_POINT);
        saveTextValue("text_left_down",ORIGIN_POINT);

        text_right_down.setText(ORIGIN_POINT);
        saveTextValue("text_right_down",ORIGIN_POINT);
    }
	@SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            switch (v.getId()){
                case R.id.iv_single_left_up:
                    //焦点在左上
                    setImageResource(tyar.getDrawable(12),getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected));
                    //setTextVisible(View.VISIBLE,View.GONE,View.GONE,View.GONE);
                    break;
                case R.id.iv_single_right_up:
                    //焦点在右上
                    setImageResource(getDrawable(R.drawable.unselected),tyar.getDrawable(12),getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected));
                    //setTextVisible(View.GONE,View.VISIBLE,View.GONE,View.GONE);
                    break;
                case R.id.iv_single_left_down:
                    //焦点在左下
                    setImageResource(getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected),tyar.getDrawable(12),getDrawable(R.drawable.unselected));
                    //setTextVisible(View.GONE,View.GONE,View.VISIBLE,View.GONE);
                    break;
                case R.id.iv_single_right_down:
                    //焦点在右下
                    setImageResource(getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected),getDrawable(R.drawable.unselected),tyar.getDrawable(12));
                    //setTextVisible(View.GONE,View.GONE,View.GONE,View.VISIBLE);
                    break;
            }
        }
    }

    /* 保存数据 */
    private void saveTextValue(String DotName,String value){
        SharedPreferences.Editor editor = getSharedPreferences("single_text_value",MODE_PRIVATE).edit();
        editor.putString(DotName,value);
        editor.apply();
    }

    private void updateText(View currentFocus,int id){
        if ( currentFocus != null){
            String name = String.valueOf(id);
            Log.i("TAG","---------"+name);
//            if (id == R.id.iv_single_left_up){
//                Log.i("左上","-------------左上----------");
//                text_left_up.setText(mKeystone.getOnePointInfo(nowPoint));
//            } else if (id == R.id.iv_single_right_up) {
//                Log.i("右上","-------------右上----------");
//                text_right_up.setText(mKeystone.getOnePointInfo(nowPoint));
//            } else if (id == R.id.tv_single_left_down) {
//                Log.i("左下","-------------左下----------");
//                text_left_down.setText(mKeystone.getOnePointInfo(nowPoint));
//            } else if (id == R.id.tv_single_right_down) {
//                Log.i("右下","------------右下-----------------");
//                text_right_down.setText(mKeystone.getOnePointInfo(nowPoint));
//            }
            switch (id) {
                case R.id.iv_single_left_up:
                    Log.i("左上","-------------左上----------");
                    text_left_up.setText(mKeystone.getOnePointInfo(nowPoint));
                    saveTextValue("text_left_up",mKeystone.getOnePointInfo(nowPoint));
                    break;
                case R.id.iv_single_right_up:
                    Log.i("右上","-------------右上----------");
                    text_right_up.setText(mKeystone.getOnePointInfo(nowPoint));
                    saveTextValue("text_right_up",mKeystone.getOnePointInfo(nowPoint));
                    break;
                case R.id.iv_single_left_down:
                    Log.i("左下","-------------左下----------");
                    text_left_down.setText(mKeystone.getOnePointInfo(nowPoint));
                    saveTextValue("text_left_down",mKeystone.getOnePointInfo(nowPoint));
                    break;
                case R.id.iv_single_right_down:
                    Log.i("右下","------------右下-----------------");
                    text_right_down.setText(mKeystone.getOnePointInfo(nowPoint));
                    saveTextValue("text_right_down",mKeystone.getOnePointInfo(nowPoint));
                    break;
            }
        }
    }

    /* 处理按键方法 */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i("function:onKey()","进onKey,keycode:"+keyCode);
        if (event.getAction() == KeyEvent.ACTION_DOWN){
                Log.i("function:onKey()","焦点不可移动，执行自定义方法");
                View view = getCurrentFocus();
                int focusId = view.getId();
                switch (keyCode){
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        Log.i("function:onKey()","执行左键方法,dot_canMoved:"+dot_canMoved);
                        mKeystone.oneLeft(nowPoint);
                        updateText(view,focusId);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        Log.i("function:onKey()","执行右键方法,dot_canMoved:"+dot_canMoved);
                        mKeystone.oneRight(nowPoint);
                        updateText(view,focusId);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        Log.i("function:onKey()","执行上键方法,dot_canMoved:"+dot_canMoved);
                        mKeystone.oneTop(nowPoint);
                        updateText(view,focusId);
                        return true;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        Log.i("function:onKey()","执行下键方法,dot_canMoved:"+dot_canMoved);
                        mKeystone.oneBottom(nowPoint);
                        updateText(view,focusId);
                        return true;
					case KeyEvent.KEYCODE_MENU:
                        mKeystone.restoreKeystone();
                        resetView();
                        break;
                    case KeyEvent.KEYCODE_ENTER:
						nowPoint++;
                        if(nowPoint>3)
                            nowPoint =0;
                        Log.i("function:onKey()","按下OK键,dot_canMoved="+dot_canMoved);
                        Log.i("function:onKey()","当前控件是："+focusId);
                        switch (focusId) {
                            case R.id.iv_single_left_up:
                                Log.i("function:onKey()","切换到右上");
                                single_right_up.requestFocus();
                                break;
                            case R.id.iv_single_right_up:
                                Log.i("function:onKey()","切换到右下");
                                single_right_down.requestFocus();
                                break;
                            case R.id.iv_single_right_down:
                                Log.i("function:onKey()","切换到左下");
                                single_left_down.requestFocus();
                                break;
                            case R.id.iv_single_left_down:
                                Log.i("function:onKey()","切换到左上");
                                single_left_up.requestFocus();
                                break;
                        }
                        return true;
                }
        }
//        textView.setText(mKeystone.getOnePointInfo(nowPoint));
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
//        Toast.makeText(this, "往右拉", Toast.LENGTH_SHORT).show();
    }

    private void goLeft() {
        /**
         * 左键
         * TODO:
         */
//        Toast.makeText(this, "往左拉", Toast.LENGTH_SHORT).show();
    }

    private void goDown() {
        /**
         * 下键
         * TODO:
         */
//        Toast.makeText(this, "往下拉", Toast.LENGTH_SHORT).show();
    }

    private void goUp() {
        /**
         * 上键
         * TODO:
         */
//        Toast.makeText(this, "往上拉", Toast.LENGTH_SHORT).show();
    }
}
