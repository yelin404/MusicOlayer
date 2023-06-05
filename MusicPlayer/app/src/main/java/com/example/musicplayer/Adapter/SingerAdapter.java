package com.example.musicplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicplayer.Bean.SingerBean;
import com.example.musicplayer.R;

import java.util.List;

public class SingerAdapter extends BaseAdapter {

    public Context context;
    public List<SingerBean> mDatas;

    public SingerAdapter(Context context, List<SingerBean> mDatas) {
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
        ViewHolder viewholder = null;
        if (convertView == null) {
            //将item布局转换成view视图
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid,null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        SingerBean singerBean = mDatas.get(position);
        viewholder.singerIv.setImageResource(singerBean.getImgId());
        viewholder.singerTv.setText(singerBean.getName());
        return convertView;
    }

    class ViewHolder {

        ImageView singerIv;
        TextView singerTv;

        public ViewHolder(View view) {
            singerIv = view.findViewById(R.id.item_grid_Iv);
            singerTv =  view.findViewById(R.id.item_grd_Tv);
        }
    }
}
