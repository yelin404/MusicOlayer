package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.Bean.SingerBean;

import java.io.Serializable;

public class SingerInfoActivity extends AppCompatActivity {

    public TextView infoTvName,infoTvNameGender,infoTvWork,infoTvAchievement;
    public ImageView musicIv,backIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_info);

        //初始化组件
        initView();

        //接受传递过来的数据
        Intent intent = getIntent();
        SingerBean singer = (SingerBean) intent.getSerializableExtra("singer");

        //为每一个view赋值
        infoTvName.setText(singer.getName());
        infoTvNameGender.setText(singer.getSex());
        infoTvWork.setText(singer.getWork());
        infoTvAchievement.setText(singer.getSuccess());
        musicIv.setImageResource(singer.getImgId());
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        infoTvName = findViewById(R.id.info_tv_name);
        infoTvNameGender = findViewById(R.id.info_tv_gender);
        infoTvWork = findViewById(R.id.info_tv_work);
        infoTvAchievement = findViewById(R.id.info_tv_achievement);
        backIv = findViewById(R.id.back_Iv);
        musicIv = findViewById(R.id.music_Iv);
    }
}