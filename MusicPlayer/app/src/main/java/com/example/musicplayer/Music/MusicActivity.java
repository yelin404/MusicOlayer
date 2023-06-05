package com.example.musicplayer.Music;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.Bean.SongBean;
import com.example.musicplayer.Page.SongPage;
import com.example.musicplayer.R;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    public  ImageView exitIv;
    public static ImageView musicIv;
    public static TextView songname,progressTv,totalTv;
    public static SeekBar seekbar;
    public  Button continuePlay,play,pre,next;
    public static Button pause;
    public static MusicService.MusicControl musicControl;
    //动画实现类
    public static ObjectAnimator animator;

    public Intent songIntent;

    private static final String TAG = "MusicActivity";
    //这里主要是为了在handler中能使用Toast，Toast需要Context
    private static Context mContext;


    //这表示的是播放到第几首歌了
    public static int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mContext = getApplicationContext();
        initView();

    }

    private void initView() {
        exitIv = findViewById(R.id.exit_Iv);
        musicIv = findViewById(R.id.music_Iv);
        songname = findViewById(R.id.song_name);
        progressTv = findViewById(R.id.progress_Tv);
        totalTv = findViewById(R.id.total_Tv);
        seekbar = findViewById(R.id.seekbar);
        continuePlay = findViewById(R.id.btn_continue_play);
        pause = findViewById(R.id.pause_btn);
        play = findViewById(R.id.play_btn);
        pre = findViewById(R.id.pre_btn);
        next = findViewById(R.id.next_btn);

        //绑定点击事件
        exitIv.setOnClickListener(this);
        continuePlay.setOnClickListener(this);
        pause.setOnClickListener(this);
        play.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);

        //接收来自SongPage的数据
        songIntent = getIntent();
        SongBean songBean = (SongBean) songIntent.getSerializableExtra("SongBean");
        String name = songBean.getName();
        int imgId = songBean.getImgId();
        ////这表示的是播放到第几首歌了
        String position = songIntent.getStringExtra("position");
        i = parseInt(position);



        //设置播放歌曲的图片
        musicIv.setImageResource(imgId);

        //设置滑动条seekbar的监听事件
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //获取当前进度条的位置
                int progress = seekBar.getProgress();
                musicControl.seekTo(progress);

            }
        });

        //设置图片的旋转动画
        animator =ObjectAnimator.ofFloat(musicIv,"rotation",0f,360.0f);
        //动画持续时间为10秒
        animator.setDuration(10000);
        //设置动画播放速度
        animator.setInterpolator(new LinearInterpolator());
        //-1表示让动画无限次播放
        animator.setRepeatCount(-1);

        //启动服务
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent,cnn,BIND_AUTO_CREATE);


    }

    //创建消息处理对象，其作用就是处理子线程发到主线程的消息
    //注意：所有的方法都是在主线程中完成，子线程仅仅知识调用主线程中handler的方法！！
    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            //获得子线程传递过来的音乐播放数据
            Bundle bundle = msg.getData();
            int duration = bundle.getInt("duration");
            int currentPosition = bundle.getInt("currentPosition");
            //发过来的就两个，一个是歌曲的最大播放时间，一个是当前的播放到歌曲的几分几秒了
            seekbar.setMax(duration);
            seekbar.setProgress(currentPosition);
            //下面计算已经播放的时间和还剩下的时间，注意歌曲总时长为毫秒

            //设置一首歌曲的总时长
            int minute = duration/1000/60;
            int second = (duration/1000) % 60;
            //设置歌曲格式
            totalTv.setText(getNormTime(minute,second));


            //设置歌曲当前播放时长
            minute = currentPosition/1000/60;
            second = (currentPosition/1000) % 60;
            progressTv.setText(getNormTime(minute,second));

            //如果歌曲播放完了，则要自动播放下一首
            if (duration <= currentPosition) {
                if (i == (SongPage.name.length - 1)) {
                    animator.cancel();
                    Toast.makeText(mContext, "已经是最后一首歌曲了，无法播放下一首歌曲！", Toast.LENGTH_SHORT).show();
                } else {
                    i = i + 1;
                    musicIv.setImageResource(SongPage.icons[i]);
                    songname.setText(SongPage.name[i]);
                    musicControl.play(i);
                    pause.setVisibility(View.VISIBLE);
                    animator.start();

                }
            }

        }

        private String getNormTime(int minute,int second) {
            String strMinute,strSecond;
            //设置歌曲格式
            if (minute < 10) {
                strMinute = "0" + minute;
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {
                strSecond = "0" + second;
            } else {
                strSecond = second + "";
            }
            String resultTime = strMinute + ":" + strSecond;

            return resultTime;
        }


    };

    //用于实现连接服务
    public ServiceConnection cnn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_Iv:
                musicControl.pausePlay();
                unbindService(cnn);
                finish();
                break;
            case R.id.btn_continue_play:
                continuePlay.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
                //继续播放
                musicControl.continuePlay();
                //继续播放动画
                animator.resume();
                break;
            case R.id.pause_btn:
                continuePlay.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
                //暂停播放
                musicControl.pausePlay();
                //暂停播放动画
                animator.pause();
                break;
            case R.id.play_btn:
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
                //播放第几首歌
                musicControl.play(i);
                //开始播放动画
                animator.start();
                break;
            case R.id.pre_btn:
                if (i == 0) {
                    Toast.makeText(this, "已经是第一首歌曲了，无法在播放前一首歌曲！", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    i = i - 1;
                    musicIv.setImageResource(SongPage.icons[i]);
                    songname.setText(SongPage.name[i]);
                    musicControl.play(i);
                    pause.setVisibility(View.VISIBLE);
                    animator.start();
                    break;
                }


            case R.id.next_btn:
                if (i == (SongPage.name.length - 1)) {
                    Toast.makeText(this, "已经是最后一首歌曲了，无法播放下一首歌曲！", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    i = i + 1;
                    musicIv.setImageResource(SongPage.icons[i]);
                    songname.setText(SongPage.name[i]);
                    musicControl.play(i);
                    pause.setVisibility(View.VISIBLE);
                    animator.start();
                    break;
                }


        }

    }
}