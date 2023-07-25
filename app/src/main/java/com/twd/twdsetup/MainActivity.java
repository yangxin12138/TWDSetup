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
public class MainActivity extends AppCompatActivity  implements View.OnClickListener , View.OnFocusChangeListener {
    String theme_code = SystemPropertiesUtils.getPropertyColor("persist.sys.background_blue","0");


    private SharedPreferences mSharedPreferences;
    private final static String TAG = MainActivity.class.getSimpleName();

    private TypedArray tyar;
    ImageView iv_trapezoidal_double_point;
    ImageView iv_trapezoidal_single_point;
    ImageView iv_size;
    ImageView iv_projection;

    TextView tv_trapezoidal_double_point;
    TextView tv_trapezoidal_single_point;
    TextView tv_size;
    TextView tv_projection;
    TextView tv_projection_small;

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
                R.attr.projection_bg
        });
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
        tv_projection_small =  findViewById(R.id.tv_projection_small);
        Log.i("投影方式：","pos_pos_check:"+pos_pos_check+",pos_neg_check:"+pos_neg_check+",neg_pos_check:"+neg_pos_check+",neg_neg_check:"+neg_neg_check);
        if (pos_pos_check) tv_projection_small.setText("正装正投");
        if (pos_neg_check) tv_projection_small.setText("正装背投");
        if (neg_pos_check) tv_projection_small.setText("吊装正投");
        if (neg_neg_check) tv_projection_small.setText("吊装背投");
        /* 设置监听器 */
        double_TrapezoidalRL.setOnClickListener(this);
        single_TrapezoidalRl.setOnClickListener(this);
        sizeRl.setOnClickListener(this);
        projectionRL.setOnClickListener(this);

        double_TrapezoidalRL.setOnFocusChangeListener(this);
        single_TrapezoidalRl.setOnFocusChangeListener(this);
        sizeRl.setOnFocusChangeListener(this);
        projectionRL.setOnFocusChangeListener(this);
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

    /* 聚焦监听 选中变红*/
    @SuppressLint("ResourceType")
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.setBackground(tyar.getDrawable(7));
            if (theme_code.equals("0") || theme_code.equals("2")){
                if (v.getId() == R.id.trapezoidal_double_point){
                    iv_trapezoidal_double_point.setImageResource(R.drawable.icon_trapezoidal_double_point);
                    tv_trapezoidal_double_point.setTextColor(getResources().getColor(R.color.black));
                    arrow_trapezoidal_double_point.setImageResource(R.drawable.arrow);
                } else if (v.getId() == R.id.trapezoidal_single_point) {
                    iv_trapezoidal_single_point.setImageResource(R.drawable.icon_trapezoidal_single_point);
                    tv_trapezoidal_single_point.setTextColor(getResources().getColor(R.color.black));
                    arrow_trapezoidal_single_point.setImageResource(R.drawable.arrow);
                } else if (v.getId() == R.id.size) {
                    iv_size.setImageResource(R.drawable.icon_size_color);
                    tv_size.setTextColor(getResources().getColor(R.color.black));
                    arrow_size.setImageResource(R.drawable.arrow);
                } else if (v.getId() == R.id.projection) {
                    iv_projection.setImageResource(R.drawable.icon_projection_color);
                    tv_projection.setTextColor(getResources().getColor(R.color.black));
                    tv_projection_small.setTextColor(getResources().getColor(R.color.black));
                    arrow_projection.setImageResource(R.drawable.arrow);
                }
            }
        } else {
            v.setBackground(tyar.getDrawable(6));
            if (theme_code.equals("0") || theme_code.equals("2")){
                if (v.getId() == R.id.trapezoidal_double_point){
                    iv_trapezoidal_double_point.setImageResource(R.drawable.icon_trapezoidal_double_point_white);
                    tv_trapezoidal_double_point.setTextColor(getResources().getColor(R.color.white));
                    arrow_trapezoidal_double_point.setImageResource(R.drawable.arrow_white);
                } else if (v.getId() == R.id.trapezoidal_single_point) {
                    iv_trapezoidal_single_point.setImageResource(R.drawable.icon_trapezoidal_single_point_white);
                    tv_trapezoidal_single_point.setTextColor(getResources().getColor(R.color.white));
                    arrow_trapezoidal_single_point.setImageResource(R.drawable.arrow_white);
                } else if (v.getId() == R.id.size) {
                    iv_size.setImageResource(R.drawable.icon_size_white);
                    tv_size.setTextColor(getResources().getColor(R.color.white));
                    arrow_size.setImageResource(R.drawable.arrow_white);
                } else if (v.getId() == R.id.projection) {
                    iv_projection.setImageResource(R.drawable.icon_projection_white);
                    tv_projection.setTextColor(getResources().getColor(R.color.white));
                    tv_projection_small.setTextColor(getResources().getColor(R.color.white));
                    arrow_projection.setImageResource(R.drawable.arrow_white);
                }
            }
        }
    }

}