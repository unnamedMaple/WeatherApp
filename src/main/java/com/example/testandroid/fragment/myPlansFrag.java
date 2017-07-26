package com.example.testandroid.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testandroid.MainActivity;
import com.example.testandroid.R;
import com.example.testandroid.adapter.plansAdapter;
import com.example.testandroid.bean.plan;
import com.example.testandroid.db.dbManager;
import com.example.testandroid.db.myDBHelper;

import java.util.ArrayList;

/**
 * Created by ASUS on 2017/7/20.
 */

public class myPlansFrag extends Fragment {
    private ListView plList;
    private TextView toAdd;
    private ArrayList<plan> planList;
    private View rootview;
    private  plansAdapter oneAd;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("frag","onCreateView myPlans");
        rootview = inflater.inflate(R.layout.myplans,container, false);
        return rootview;
    }


    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        toAdd = (TextView)rootview.findViewById(R.id.ToAdd);

        //添加日程
        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("click","toAdd");
                MainActivity activity = (MainActivity) getActivity();

                PlanFrag newplan = new PlanFrag();
                Bundle bundle = new Bundle();
                bundle.putInt("op", PlanFrag.FromAdd);
                bundle.putInt("year",2017);
                bundle.putInt("month",7);
                bundle.putInt("day",21);


                newplan.setArguments(bundle);

                FragmentManager fm1=getFragmentManager();
                FragmentTransaction transaction1=fm1.beginTransaction();
                transaction1.replace(R.id.fragment,newplan);
                transaction1.commit();

            }
        });

        plList = (ListView)rootview.findViewById(R.id.planList);

        planList = readPlanList();

        Log.v("context","getActivity");
        oneAd = new plansAdapter(this.getActivity(),planList);
        plList.setAdapter(oneAd);

        //点击进入编辑页面
        plList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                  {
                                      //                      对应listview           对应viewItem  posion    id
                                      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                                      {
                                          MainActivity activity = (MainActivity) getActivity();


                                          plan one = planList.get(arg2);

                                          PlanFrag newplan = new PlanFrag();
                                          Bundle bundle=new Bundle();
                                          bundle.putInt("op", PlanFrag.FromEdit);
                                          bundle.putSerializable("plan",one);
                                          newplan.setArguments(bundle);

                                          FragmentManager fm1=getFragmentManager();
                                          FragmentTransaction transaction1=fm1.beginTransaction();
                                          transaction1.replace(R.id.fragment,newplan);
                                          transaction1.commit();
                                      }

                                  }
        );





        //长按删除日程
        plList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long l) {



                AlertDialog.Builder builder=new AlertDialog.Builder(rootview.getContext());
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int id = planList.get(position).getId();
                        dbManager mymanager = new dbManager();
                        mymanager.deletePlan(rootview.getContext(),id);

                        if(planList.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        oneAd.notifyDataSetChanged();

                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();

                return true;
            }
        });
    }

    //从数据库中读出日程
    private ArrayList<plan> readPlanList(){
        ArrayList<plan> list = new ArrayList<plan>();
        myDBHelper help = new myDBHelper(rootview.getContext());
        SQLiteDatabase db = help.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from plan",null);
        while(cursor.moveToNext())
        {

            //"id,tittle,place ,event,year ,month,day ,hour ,minute "

            plan one = new plan(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6), cursor.getInt(7),cursor.getInt(8));

            list.add(one);

        }
        return list;
    }

}
