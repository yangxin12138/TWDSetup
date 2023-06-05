package com.twd.twdsetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

/**
 * 尺寸调节页面
 * SeekBar做调节滑块，TextView 文字显示Progress进度
 */
public class SizeActivity extends AppCompatActivity implements View.OnFocusChangeListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar_level;
    private TextView textView_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        initView();
    }

    private void initView(){
        seekBar_level = (SeekBar) findViewById(R.id.seekbar_level);
        textView_level = (TextView) findViewById(R.id.text_level);

        //初始化，读取seekbar状态
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int seekBarValue = prefs.getInt("seekBarValue",0);
        seekBar_level.setProgress(seekBarValue);
        textView_level.setText("Level:"+seekBar_level.getProgress());

        seekBar_level.setOnFocusChangeListener(this);
        seekBar_level.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            // 此处为得到焦点时的处理内容
            ViewCompat.animate(v)
                    .scaleX(1.10f)
                    .scaleY(1.10f)
                    .translationZ(1)
                    .start();
        } else {
            // 此处为失去焦点时的处理内容
            ViewCompat.animate(v)
                    .scaleX(1)
                    .scaleY(1)
                    .translationZ(1)
                    .start();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //保存进度条
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("seekBarValue",progress);
        editor.apply();

        textView_level.setText("Level:"+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
