package com.twd.twdsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twd.twdsetup.keystone.SystemPropertiesUtils;

import java.lang.reflect.Type;

/**
 * 投影设置主页
 *
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {
    String theme_code = SystemPropertiesUtils.getPropertyColor("persist.sys.background_blue","0");


    private SharedPreferences mSharedPreferences;
    private final static String TAG = MainActivity.class.getSimpleName();

    private TypedArray tyar;
    private ImageView iv_trapezoidal_double_point;
    private ImageView iv_trapezoidal_single_point;
    private ImageView iv_size;
    private ImageView iv_projection;


    private TextView tv_trapezoidal_double_point;
    private TextView tv_trapezoidal_single_point;
    private TextView tv_size;
    private TextView tv_projection;
    private TextView tv_projection_small;

    ImageView arrow_trapezoidal_double_point;
    ImageView arrow_trapezoidal_single_point;
    ImageView arrow_size;
    ImageView arrow_projection;

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
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    /* 页面初始化 */
    private void initView() {
//        View projectionView = getLayoutInflater().inflate(R.layout.activity_projeciton,null);
        // 获取SharedPreferences实例，传递使用的SharedPreferences文件名称和MODE_PRIVATE模式
        mSharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        boolean pos_pos_check = mSharedPreferences.getBoolean("pos_pos",false);
        boolean pos_neg_check = mSharedPreferences.getBoolean("pos_neg",false);
        boolean neg_pos_check = mSharedPreferences.getBoolean("neg_pos",false);
        boolean neg_neg_check = mSharedPreferences.getBoolean("neg_neg",false);

        RelativeLayout double_TrapezoidalRL = findViewById(R.id.trapezoidal_double_point);
        RelativeLayout single_TrapezoidalRl =  findViewById(R.id.trapezoidal_single_point);
        RelativeLayout sizeRl =  findViewById(R.id.size);
        RelativeLayout projectionRL =  findViewById(R.id.projection);
        ConstraintLayout main_bg = findViewById(R.id.main_background);

        iv_trapezoidal_double_point =  findViewById(R.id.iv_trapezoidal_double_point);
        iv_trapezoidal_single_point =  findViewById(R.id.iv_trapezoidal_single_point);
        iv_size =  findViewById(R.id.iv_size);
        iv_projection =  findViewById(R.id.iv_projection);

        tv_trapezoidal_double_point =  findViewById(R.id.tv_trapezoidal_double_point);
        tv_trapezoidal_single_point =  findViewById(R.id.tv_trapezoidal_single_point);
        tv_size =  findViewById(R.id.tv_size);
        tv_projection =  findViewById(R.id.tv_projection);

        arrow_trapezoidal_double_point = findViewById(R.id.arrow_trapezoidal_double_point);
        arrow_trapezoidal_single_point = findViewById(R.id.arrow_trapezoidal_single_point);
        arrow_size = findViewById(R.id.arrow_size);
        arrow_projection = findViewById(R.id.arrow_projection);
        /* 设置投影方式显示文字 根据mSharedPreferences中提取的值判断显示*/
        tv_projection_small = (TextView) findViewById(R.id.tv_projection_small);
        Log.i("投影方式：","pos_pos_check:"+pos_pos_check+",pos_neg_check:"+pos_neg_check+",neg_pos_check:"+neg_pos_check+",neg_neg_check:"+neg_neg_check);
        if (pos_pos_check) tv_projection_small.setText(R.string.projection_mode＿pos_pos);
        if (pos_neg_check) tv_projection_small.setText(R.string.projection_mode＿pos_neg);
        if (neg_pos_check) tv_projection_small.setText(R.string.projection_mode＿neg_pos);
        if (neg_neg_check) tv_projection_small.setText(R.string.projection_mode＿neg_neg);
        /* 设置监听器 */
        double_TrapezoidalRL.setOnClickListener(this);
        single_TrapezoidalRl.setOnClickListener(this);
        sizeRl.setOnClickListener(this);
        projectionRL.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        /* 点击事件，跳转到对应菜单和活动*/
        Intent intent;
        switch (v.getId()){
            case R.id.trapezoidal_double_point:
//                Toast.makeText(this, "点击两点梯形校正", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,TrapezoidalActivity.class);
                startActivity(intent);
                break;
            case R.id.trapezoidal_single_point:
//                Toast.makeText(this, "点击四点梯形校正", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,TrapezoidalSinglePointActivity.class);
                startActivity(intent);
                break;
            case R.id.size:
//                Toast.makeText(this, "点击尺寸调节", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,SizeActivity.class);
                startActivity(intent);
                break;
            case R.id.projection:
                intent = new Intent(this,ProjectionActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "点击投影方式", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}