package com.twd.twdsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twd.twdsetup.keystone.SystemPropertiesUtils;
import com.twd.twdsetup.keystone.keystone;
import com.twd.twdsetup.keystone.keystoneTwoPoint;

/**
 * 梯形校正页面布局
 * 监听实现 遥控器上下左右键的点击事件
 */
public class TrapezoidalActivity extends AppCompatActivity {

    private final static String TAG = "TrapezoidalActivity";
    private Context mContext;
    private keystoneTwoPoint mKeystone;
    private TextView mTextY;//上下调节
    private TextView mTextX;//左右调节

    protected static SharedPreferences prefsTextValue;

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
        setContentView(R.layout.activity_trapezoidal_double_point);
       // mKeystone = new keystone(this);
        tyar= this.getTheme().obtainStyledAttributes(new int[]{
                R.attr.arrowBackSrc,
                R.attr.textColor,
                R.attr.trape_double_Src,
                R.attr.trape_single_Src,
                R.attr.size_src,
                R.attr.projection_Src,
                R.attr.unselFrameBG,
                R.attr.selFrameBG,
                R.attr.arrowSrc,
                R.attr.backGround,
                R.attr.itemSelected,
                R.attr.projection_bg
        });
        initView();
        SharedPreferences prefs = this.getSharedPreferences("keystone_mode", Context.MODE_PRIVATE);
        int mode = prefs.getInt("mode",0);

        mKeystone = new keystoneTwoPoint(this);
        if(mode == 0){
            mKeystone.resetKeystone();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("mode",1);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @SuppressLint("ResourceType")
    private void initView(){
        //初始化并读取数据
        mTextY = (TextView) findViewById(R.id.tv_vertical);
        mTextX = (TextView) findViewById(R.id.tv_horizontal);
        prefsTextValue = this.getSharedPreferences("text_value",MODE_PRIVATE);
        String textX = prefsTextValue.getString("horizontalValue","0");
        String textY = prefsTextValue.getString("verticalValue","0");
        mTextX.setText(textX);
        mTextX.setTextColor(tyar.getColor(1,0));
        mTextY.setText(textY);
        mTextY.setTextColor(tyar.getColor(1,0));
    }

    /* 保存调整坐标数据 */
    private void saveTextValue(String VorH,String value){
        SharedPreferences.Editor editor = getSharedPreferences("text_value",MODE_PRIVATE).edit();
        editor.putString(VorH,value);
        editor.apply();
    }

    /* 处理按键方法 */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (action == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_DPAD_UP:
                    //处理上键事件
                    Log.i(TAG,"触发处理上键事件");
                    mKeystone.twoTop();
                    int y =mKeystone.getTwoPointYInfo();
                    mTextY.setText("("+y+")");
                    saveTextValue("verticalValue","("+y+")");
                    goUp();
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    //处理下键事件
                    Log.i(TAG,"触发处理下键事件");
                    mKeystone.twoBottom();
                    int yy=mKeystone.getTwoPointYInfo();
                    mTextY.setText("("+yy+")");
                    saveTextValue("verticalValue","("+yy+")");
                    goDown();
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    //处理左键事件
                    Log.i(TAG,"触发处理左键事件");
                    mKeystone.twoLeft();
                    int x =mKeystone.getTwoPointXInfo();
                    mTextX.setText("("+x+")");
                    saveTextValue("horizontalValue","("+x+")");
                    goLeft();
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    //处理右键事件
                    Log.i(TAG,"触发处理右键事件");
                    mKeystone.twoRight();
                    int xx =mKeystone.getTwoPointXInfo();
                    mTextX.setText("("+xx+")");
                    saveTextValue("horizontalValue","("+xx+")");
                    goRight();
                    break;
                case KeyEvent.KEYCODE_MENU:
                    mKeystone.resetKeystone();
                    int xxx =mKeystone.getTwoPointXInfo();
                    mTextX.setText("("+xxx+")");
                    saveTextValue("horizontalValue","("+xxx+")");
                    int yyy=mKeystone.getTwoPointYInfo();
                    mTextY.setText("("+yyy+")");
                    saveTextValue("verticalValue","("+yyy+")");
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
}