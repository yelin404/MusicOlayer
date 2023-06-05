package com.example.musicplayer.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicplayer.Adapter.SingerAdapter;
import com.example.musicplayer.Bean.SingerBean;
import com.example.musicplayer.Bean.SingerUtils;
import com.example.musicplayer.Bean.SongBean;
import com.example.musicplayer.R;
import com.example.musicplayer.SingerInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class SingerPage extends Fragment {
    public GridView singerGV;
    List<SingerBean> mDatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mDatas = new ArrayList<>();
        mDatas = SingerUtils.getSingerList();


        //找到特定的view以便使用findViewById方法
        View view = inflater.inflate(R.layout.singer_gv,null);
        singerGV = view.findViewById(R.id.singer_GV);

        //适配器加载数据
        SingerAdapter adapter = new SingerAdapter(getContext(), mDatas);

        //布局绑定适配器
        singerGV.setAdapter(adapter);

        //点击过后应该进入歌手信息界面
        setListener();


        return view;
    }

    private void setListener() {
        singerGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerBean singerBean = mDatas.get(position);
                Intent intent = new Intent(getActivity(), SingerInfoActivity.class);
                intent.putExtra("singer", singerBean);
                startActivity(intent);
            }
        });
    }
}
