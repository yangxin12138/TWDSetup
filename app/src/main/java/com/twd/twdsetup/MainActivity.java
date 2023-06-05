package com.twd.twdsetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 投影设置主页
 *
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener , View.OnFocusChangeListener {

    private RelativeLayout double_TrapezoidalRL;
    private RelativeLayout single_TrapezoidalRl;
    private RelativeLayout sizeRl;
    private RelativeLayout projectionRL;

    private ImageView iv_trapezoidal_double_point;
    private ImageView iv_trapezoidal_single_point;
    private ImageView iv_size;
    private ImageView iv_projection;


    private TextView tv_trapezoidal_double_point;
    private TextView tv_trapezoidal_single_point;
    private TextView tv_size;
    private TextView tv_projection;
    private TextView tv_projection_small;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
//        View projectionView = getLayoutInflater().inflate(R.layout.activity_projeciton,null);
        // 获取SharedPreferences实例，传递使用的SharedPreferences文件名称和MODE_PRIVATE模式
        mSharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        boolean pos_pos_check = mSharedPreferences.getBoolean("pos_pos",false);
        boolean pos_neg_check = mSharedPreferences.getBoolean("pos_neg",false);
        boolean neg_pos_check = mSharedPreferences.getBoolean("neg_pos",false);
        boolean neg_neg_check = mSharedPreferences.getBoolean("neg_neg",false);

        double_TrapezoidalRL = (RelativeLayout) findViewById(R.id.trapezoidal_double_point);
        single_TrapezoidalRl = (RelativeLayout) findViewById(R.id.trapezoidal_single_point);
        sizeRl = (RelativeLayout) findViewById(R.id.size);
        projectionRL = (RelativeLayout) findViewById(R.id.projection);

        iv_trapezoidal_double_point = (ImageView) findViewById(R.id.iv_trapezoidal_double_point);
        iv_trapezoidal_single_point = (ImageView) findViewById(R.id.iv_trapezoidal_single_point);
        iv_size = (ImageView) findViewById(R.id.iv_size);
        iv_projection = (ImageView) findViewById(R.id.iv_projection);

        tv_trapezoidal_double_point = (TextView) findViewById(R.id.tv_trapezoidal_double_point);
        tv_trapezoidal_single_point = (TextView) findViewById(R.id.tv_trapezoidal_single_point);
        tv_size = (TextView) findViewById(R.id.tv_size);
        tv_projection = (TextView) findViewById(R.id.tv_projection);

        tv_projection_small = (TextView) findViewById(R.id.tv_projection_small);
        Log.i("投影方式：","pos_pos_check:"+pos_pos_check+",pos_neg_check:"+pos_neg_check+",neg_pos_check:"+neg_pos_check+",neg_neg_check:"+neg_neg_check);
        if (pos_pos_check) tv_projection_small.setText("正装正投");
        if (pos_neg_check) tv_projection_small.setText("正装背投");
        if (neg_pos_check) tv_projection_small.setText("吊装正投");
        if (neg_neg_check) tv_projection_small.setText("吊装背投");

        double_TrapezoidalRL.setOnClickListener(this);
        single_TrapezoidalRl.setOnClickListener(this);
        sizeRl.setOnClickListener(this);
        projectionRL.setOnClickListener(this);

        double_TrapezoidalRL.setOnFocusChangeListener(this);
        single_TrapezoidalRl.setOnFocusChangeListener(this);
        sizeRl.setOnFocusChangeListener(this);
        projectionRL.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.trapezoidal_double_point:
                Toast.makeText(this, "点击两点梯形校正", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,TrapezoidalActivity.class);
                startActivity(intent);
                break;
            case R.id.trapezoidal_single_point:
                Toast.makeText(this, "点击四点梯形校正", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,TrapezoidalSinglePointActivity.class);
                startActivity(intent);
                break;
            case R.id.size:
                Toast.makeText(this, "点击尺寸调节", Toast.LENGTH_SHORT).show();
                intent = new Intent(this,SizeActivity.class);
                startActivity(intent);
                break;
            case R.id.projection:
                intent = new Intent(this,ProjectionActivity.class);
                startActivity(intent);
                Toast.makeText(this, "点击投影方式", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.setBackgroundResource(R.drawable.test_red);
        } else {
            v.setBackgroundResource(R.drawable.test);
        }
    }

}