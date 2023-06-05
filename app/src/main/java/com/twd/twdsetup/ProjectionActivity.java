package com.twd.twdsetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 投影方式 二级菜单
 * 四个方式单选 选中项显示在上层菜单中
 */
public class ProjectionActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private RelativeLayout pos_pos;//正装正投
    private RelativeLayout pos_neg;//正装背投
    private RelativeLayout neg_pos;//吊装正投
    private RelativeLayout neg_neg;//吊装背投

    private ImageView sel_pos_pos;
    private ImageView sel_pos_neg;
    private ImageView sel_neg_pos;
    private ImageView sel_neg_neg;

    private TextView tv_pos_pos;
    private TextView tv_pos_neg;
    private TextView tv_neg_pos;
    private TextView tv_neg_neg;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String POS_POS = "pos_pos";
    private static final String POS_NEG = "pos_neg";
    private static final String NEG_POS = "neg_pos";
    private static final String NEG_NEG = "neg_neg";

    //项是否被勾选的标记
    public static boolean pos_pos_check;
    public static boolean pos_neg_check;
    public static boolean neg_pos_check;
    public static boolean neg_neg_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projeciton);
        initView();
    }

    private void initView(){
        pos_pos = (RelativeLayout) findViewById(R.id.pro_pos_pos);
        pos_neg = (RelativeLayout) findViewById(R.id.pro_pos_neg);
        neg_pos = (RelativeLayout) findViewById(R.id.pro_neg_pos);
        neg_neg = (RelativeLayout) findViewById(R.id.pro_neg_neg);

        tv_pos_pos = (TextView) findViewById(R.id.tv_pos_pos);
        tv_pos_neg = (TextView) findViewById(R.id.tv_pos_neg);
        tv_neg_pos = (TextView) findViewById(R.id.tv_neg_pos);
        tv_neg_neg = (TextView) findViewById(R.id.tv_neg_neg);

        sel_pos_pos = (ImageView) findViewById(R.id.sel_pos_pos);
        sel_pos_pos.setImageResource(R.drawable.selected);
        sel_pos_neg = (ImageView) findViewById(R.id.sel_pos_neg);
        sel_neg_pos = (ImageView) findViewById(R.id.sel_neg_pos);
        sel_neg_neg = (ImageView) findViewById(R.id.sel_neg_neg);

        //读取上次保存的数据
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean pos_pos_isChecked = prefs.getBoolean(POS_POS,false);
        if (pos_pos_isChecked) sel_pos_pos.setImageResource(R.drawable.selected); else sel_pos_pos.setImageResource(R.drawable.unselected);

        boolean pos_neg_isChecked = prefs.getBoolean(POS_NEG,false);
        if (pos_neg_isChecked) sel_pos_neg.setImageResource(R.drawable.selected);

        boolean neg_pos_isChecked = prefs.getBoolean(NEG_POS,false);
        if (neg_pos_isChecked) sel_neg_pos.setImageResource(R.drawable.selected);

        boolean neg_neg_isChecked = prefs.getBoolean(NEG_NEG,false);
        if (neg_neg_isChecked) sel_neg_neg.setImageResource(R.drawable.selected);

        pos_pos.setOnClickListener(this);
        pos_neg.setOnClickListener(this);
        neg_pos.setOnClickListener(this);
        neg_neg.setOnClickListener(this);

        pos_pos.setOnFocusChangeListener(this);
        pos_neg.setOnFocusChangeListener(this);
        neg_pos.setOnFocusChangeListener(this);
        neg_neg.setOnFocusChangeListener(this);

    }

    /**
     * 保存选中状态
     * @param items
     */
    private void setSelect(boolean[] items){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(POS_POS,items[0]);
        editor.putBoolean(POS_NEG,items[1]);
        editor.putBoolean(NEG_POS,items[2]);
        editor.putBoolean(NEG_NEG,items[3]);
        editor.apply();
    }
    @Override
    public void onClick(View v) {
        boolean[] isChecked = new boolean[4];
        switch (v.getId()){
            case R.id.pro_pos_pos:
                setSelImageSource(R.drawable.selected, R.drawable.unselected, R.drawable.unselected, R.drawable.unselected);
                isChecked[0] = true; isChecked[1] = false; isChecked[2] = false; isChecked[3] = false;
                setSelect(isChecked);
                setItemsBoolean(true,false,false,false);
                break;
            case R.id.pro_pos_neg:
                setSelImageSource(R.drawable.unselected, R.drawable.selected, R.drawable.unselected, R.drawable.unselected);
                isChecked[0] = false; isChecked[1] = true; isChecked[2] = false; isChecked[3] = false;
                setSelect(isChecked);
                setItemsBoolean(false,true,false,false);
                break;
            case R.id.pro_neg_pos:
                setSelImageSource(R.drawable.unselected, R.drawable.unselected, R.drawable.selected, R.drawable.unselected);
                isChecked[0] = false; isChecked[1] = false; isChecked[2] = true; isChecked[3] = false;
                setSelect(isChecked);
                setItemsBoolean(false,false,true,false);
                break;
            case R.id.pro_neg_neg:
                setSelImageSource(R.drawable.unselected, R.drawable.unselected, R.drawable.unselected, R.drawable.selected);
                isChecked[0] = false; isChecked[1] = false; isChecked[2] = false; isChecked[3] = true;
                setSelect(isChecked);
                setItemsBoolean(false,false,false,true);
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

    /**
     * 设置被选中时的选中图标
     * @param pos_pos
     * @param pos_neg
     * @param neg_pos
     * @param neg_neg
     */
    private void setSelImageSource(int pos_pos,int pos_neg,int neg_pos,int neg_neg){
        sel_pos_pos.setImageResource(pos_pos);
        sel_pos_neg.setImageResource(pos_neg);
        sel_neg_pos.setImageResource(neg_pos);
        sel_neg_neg.setImageResource(neg_neg);
    }

    /**
     * 设置标记FLag
     * @param pos_pos
     * @param pos_neg
     * @param neg_pos
     * @param neg_neg
     */
    private void setItemsBoolean(boolean pos_pos,boolean pos_neg,boolean neg_pos,boolean neg_neg){
        pos_pos_check = pos_pos;
        pos_neg_check = pos_neg;
        neg_pos_check = neg_pos;
        neg_neg_check = neg_neg;
    }
}
