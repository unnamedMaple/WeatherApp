package com.example.testandroid.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testandroid.R;

/**
 * Created by ASUS on 2017/7/21.
 */

public class nonePlanFrag extends Fragment {
    private View rootview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("frag","onCreateView noneplan");
        rootview = inflater.inflate(R.layout.noneplan,container, false);
        return rootview;
    }
}
