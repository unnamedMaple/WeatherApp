package com.example.testandroid.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testandroid.MainActivity;
import com.example.testandroid.R;
import com.example.testandroid.bean.plan;
import com.example.testandroid.db.myDBHelper;

import java.util.ArrayList;

/**
 * Created by ASUS on 2017/7/19.
 */


//日历
public class DateFrag extends Fragment {

    private int year;
    private int month;
    private int day;
    private CalendarView myCal;
    private View rootview;
    private TextView add;
    private ArrayList<plan> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("fragA","onCreateView date");

        rootview = inflater.inflate(R.layout.date,container, false);
        return rootview;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        myCal = (CalendarView)rootview.findViewById(R.id.calendarView);


        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        FragmentManager fm1=getFragmentManager();
        FragmentTransaction transaction1=fm1.beginTransaction();
        list = readTheDayPlanList();
        if(list.size() != 0)
        {
            theDayPlansFrag plans = new theDayPlansFrag();
            Bundle bundle = new Bundle();

            bundle.putSerializable("list",list);
            plans.setArguments(bundle);
            transaction1.replace(R.id.dayPlan,plans);
        }
        else
        {
            nonePlanFrag none = new nonePlanFrag();
            transaction1.replace(R.id.dayPlan,none);
        }
        transaction1.commit();



        add = (TextView)rootview.findViewById(R.id.ToAdd2);
        //进入添加页面
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("click","toAdd");
                MainActivity activity = (MainActivity) getActivity();
                PlanFrag newplan = new PlanFrag();
                Bundle bundle = new Bundle();
                bundle.putInt("op", PlanFrag.FromAdd);
                bundle.putInt("year",year);
                bundle.putInt("month",month);
                bundle.putInt("day",day);
                newplan.setArguments(bundle);
                FragmentManager fm1=getFragmentManager();
                FragmentTransaction transaction1=fm1.beginTransaction();
                transaction1.replace(R.id.fragment,newplan);
                transaction1.commit();



            }
        });


        //点击日期，显示当天所有日程
        myCal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                MainActivity activity = (MainActivity) getActivity();
                year = y;
                month = m+1;
                day = d;
                FragmentManager fm1=getFragmentManager();
                FragmentTransaction transaction1=fm1.beginTransaction();
                list = readTheDayPlanList();
                if(list.size() != 0)
                {
                    theDayPlansFrag plans = new theDayPlansFrag();
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("list",list);
                    plans.setArguments(bundle);
                    transaction1.replace(R.id.dayPlan,plans);
                }
                else
                {
                    nonePlanFrag none = new nonePlanFrag();
                    transaction1.replace(R.id.dayPlan,none);
                }
                transaction1.commit();
            }
        });
    }



    //从数据库中读出特定日期的日程
    private ArrayList<plan> readTheDayPlanList(){
        ArrayList<plan> list = new ArrayList<plan>();
        myDBHelper help = new myDBHelper(rootview.getContext());
        SQLiteDatabase db = help.getReadableDatabase();

        String query = "select * from plan where year="+year+" and month="+month+" and day="+day;
        Log.v("query",query);
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext())
        {

            //"id,tittle,place ,event,year ,month,day ,hour ,minute "

            plan one = new plan(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6), cursor.getInt(7),cursor.getInt(8));

            list.add(one);

        }
        return list;
    }







}
