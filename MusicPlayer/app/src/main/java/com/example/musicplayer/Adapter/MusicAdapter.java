package com.example.musicplayer.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.Bean.SongBean;
import com.example.musicplayer.R;

import java.util.List;

public class MusicAdapter extends BaseAdapter {

    public Context context;
    public List<SongBean> mDatas;


    public MusicAdapter(Context context, List<SongBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_music_list, null);
        ImageView songPic = view.findViewById(R.id.item_list_Iv);
        TextView songName = view.findViewById(R.id.item_list_Tv);
        songPic.setImageResource(mDatas.get(position).imgId);
        songName.setText(mDatas.get(position).name);
        return view;
    }
}
