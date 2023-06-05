package com.example.musicplayer.Music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {

    private MediaPlayer player; //声明媒体播放器，这个播放器用来播放视频和音频
    private Timer timer;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();  //实例化
    }

    @Override
    public IBinder onBind(Intent intent) {
       return new MusicControl();
    }

    //定义音乐播放控制类
    class  MusicControl extends Binder {
        public void play(int i) {

            //这里就是音乐存放的位置
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music" + i);
            try {
                //重置音乐播放器
                player.reset();
                //添加多媒体文件
                player = MediaPlayer.create(getApplicationContext(),uri);
                //开始播放音乐
                player.start();
                //添加计时器
                addTimer();
            }catch (Exception e) {
                e.printStackTrace();
            }


        }

        //继续播放
        public void continuePlay() {
            player.start();
        }

        //暂停音乐
        public void pausePlay() {
            player.pause();
        }

        //跳转到音乐的特定位置，比如说1分32秒出开始播放
        public void seekTo(int progress) {
            player.seekTo(progress);
        }


    }

    //添加计时器用于设置音乐播放器中的播放进度条
    public void addTimer() {
        if (timer == null) {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (player == null) {
                        return;
                    }
                    //一首歌的播放时间
                    int duration = player.getDuration();
                    //歌曲当前的播放进度
                    int currentPosition = player.getCurrentPosition();
                    //创建消息对象
                    Message msg = MusicActivity.handler.obtainMessage();

                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    //将消息发送到消息队列
                    MusicActivity.handler.sendMessage(msg);


                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
                player.release();    //释放占用资源
                player = null;
            }
        }
    }


}