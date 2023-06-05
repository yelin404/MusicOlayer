package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.musicplayer.Page.SingerPage;
import com.example.musicplayer.Page.SongPage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView songTv,singerTv;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化组件
        initView();

        //将fragment内容添加到framlayout当中
        ft.replace(R.id.content, new SongPage());
        ft.commit();
    }

    private void initView() {
        songTv = findViewById(R.id.song);
        singerTv = findViewById(R.id.singer);
        songTv.setOnClickListener(this);
        singerTv.setOnClickListener(this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
    }

    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.song:
                ft.replace(R.id.content, new SongPage());
                break;
            case R.id.singer:
                //点击歌手，切换到歌手信息
                ft.replace(R.id.content, new SingerPage());
                break;
        }
        ft.commit();
    }
}