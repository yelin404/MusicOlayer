package com.example.musicplayer.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicplayer.Adapter.MusicAdapter;
import com.example.musicplayer.Bean.SongBean;
import com.example.musicplayer.Music.MusicActivity;
import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class SongPage extends Fragment {
    //在这里添加歌曲名
    public static String[] name={"邓紫棋——光年之外","蔡健雅——红色高跟鞋","Taylor Swift——Love Story",
            "朴树——平凡之路","田馥甄——小幸运","周杰伦——七里香","林俊杰——江南"};
    //在这里添加歌曲图片
    public static int[] icons={R.drawable.music0,R.drawable.music1,R.drawable.music2,
            R.drawable.music3,R.drawable.music4,R.drawable.music5,R.drawable.music6};

    public  List<SongBean> mDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mDatas = new ArrayList<>();
        for (int i = 0;i < name.length;i++) {
            SongBean item = new SongBean(name[i],icons[i] );
            mDatas.add(item);
        }

        View view = inflater.inflate(R.layout.music_list, null);

        ListView musicLV = view.findViewById(R.id.music_LV);

        //设置适配器
        MusicAdapter adapter = new MusicAdapter(getContext(),mDatas);

        //适配器与listview绑定
        musicLV.setAdapter(adapter);

        //设置listview每一个选项的项目点击事件,再点击过后应该进入音乐播放界面
        musicLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MusicActivity.class);

                //传值
                intent.putExtra("SongBean",mDatas.get(position));
                intent.putExtra("position",position + "");

                startActivity(intent);


            }
        });



        return view;

    }
}
