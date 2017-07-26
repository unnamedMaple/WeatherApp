package com.example.testandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testandroid.R;
import com.example.testandroid.bean.plan;


import java.util.ArrayList;

/**
 * Created by ASUS on 2017/7/21.
 */

public class plansAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    ArrayList<plan> dataList;

    /**
     * 构造函数
     */
    public plansAdapter(Context context, ArrayList<plan> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = data;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder;


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.plan_list_item, null);
            holder = new ViewHolder();


            holder.itemTime = (TextView) convertView.findViewById(R.id.ItemTime);
            holder.itemTittle = (TextView) convertView.findViewById(R.id.ItemTittle);
            holder.itemPlace = (TextView) convertView.findViewById(R.id.ItemPlace);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        String ti = dataList.get(position).getYear()+"-"+dataList.get(position).getMonth()+"-"+dataList.get(position).getDay();
        holder.itemTime.setText(ti);
        holder.itemPlace.setText(dataList.get(position).getPlace());
        holder.itemTittle.setText(dataList.get(position).getTittle());
        return convertView;
    }


    public final class ViewHolder {

        public TextView itemTime;
        public TextView itemTittle;
        public TextView itemPlace;

    }
}
