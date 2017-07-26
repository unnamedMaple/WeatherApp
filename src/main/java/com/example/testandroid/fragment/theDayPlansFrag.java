package com.example.testandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.testandroid.R;
import com.example.testandroid.adapter.plansAdapter;
import com.example.testandroid.bean.plan;

import java.util.ArrayList;

/**
 * Created by ASUS on 2017/7/21.
 */
//返回特定日期的日程布局
public class theDayPlansFrag extends Fragment{
    private ListView plView;

    private ArrayList<plan> planList;
    private View rootview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("frag","onCreateView myPlans");
        rootview = inflater.inflate(R.layout.thedayplans,container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        plView = (ListView)rootview.findViewById(R.id.thedayPlans);

        planList = (ArrayList<plan>)getArguments().get("list");
        plansAdapter one = new plansAdapter(this.getActivity(),planList);
        plView.setAdapter(one);

    }



}
