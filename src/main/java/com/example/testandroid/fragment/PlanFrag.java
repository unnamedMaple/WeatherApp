package com.example.testandroid.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testandroid.R;
import com.example.testandroid.bean.plan;
import com.example.testandroid.db.dbManager;

/**
 * Created by ASUS on 2017/7/20.
 */

public class PlanFrag extends Fragment {

    public static int FromAdd = 1;
    public static int FromEdit = 0;

    private View rootview;

    private EditText tittle;
    private EditText place;
    private EditText ev;
    private TextView timeselView;
    private TextView dateselView;

    private Button add;
    private Button edit;


    private DatePickerDialog datepick;
    private TimePickerDialog timepick;

    private plan myplan;



    private int from;

    private int year = 2017;
    private int month = 7;
    private int day =21;
    private int hour = 12;
    private int minute = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("frag","onCreateView myPlans");
        rootview = inflater.inflate(R.layout.plan,container, false);
        return rootview;
    }

    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        tittle = (EditText)rootview.findViewById(R.id.tittle);
        place = (EditText)rootview.findViewById(R.id.place);
        ev = (EditText)rootview.findViewById(R.id.event);
        timeselView = (TextView)rootview.findViewById(R.id.timeselView);
        timeselView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepick.show();
            }
        });



        dateselView = (TextView)rootview.findViewById(R.id.dateselView);
        dateselView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepick.show();
            }
        });


        add = (Button)rootview.findViewById(R.id.addEventB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myplan = new plan(0,tittle.getText().toString(),place.getText().toString(),ev.getText().toString(),year,month,day,hour,minute);
                dbManager mymanager = new dbManager();

                mymanager.insertPlan(rootview.getContext(),myplan);
                Toast.makeText(rootview.getContext(),"add successfully",Toast.LENGTH_SHORT).show();
                tittle.setText("");
                place.setText("");
                ev.setText("");
            }
        });




        edit = (Button)rootview.findViewById(R.id.editEventB);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myplan.setYear(year);
                myplan.setDay(day);
                myplan.setMonth(month);
                myplan.setHour(hour);
                myplan.setMinute(minute);
                myplan.setTittle(tittle.getText().toString());
                myplan.setPlace(place.getText().toString());
                myplan.setEvent(ev.getText().toString());
                dbManager mymanager = new dbManager();
                mymanager.updatePlan(rootview.getContext(),myplan);
                Toast.makeText(rootview.getContext(),"edit successfully",Toast.LENGTH_SHORT).show();

            }
        });



        from = getArguments().getInt("op");

        if(from == FromAdd)
        {
            edit.setVisibility(View.INVISIBLE);

            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day= getArguments().getInt("day");
            String defaultdate =year+"年"+month+"月"+day+"日";
            dateselView.setText(defaultdate);
            timeselView.setText("12:00");

        }
        else
        {
            add.setVisibility(View.INVISIBLE);
            myplan = (plan)getArguments().getSerializable("plan");
            tittle.setText(myplan.getTittle());
            place.setText(myplan.getPlace());
            ev.setText(myplan.getEvent());
            year = myplan.getYear();
            month = myplan.getMonth();
            day = myplan.getDay();
            hour = myplan.getHour();
            minute = myplan.getMinute();

            updateDate();
            updateTime();

        }


        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {



                year=myyear;
                month=monthOfYear+1;
                day=dayOfMonth;

                updateDate();

            }
            //当DatePickerDialog关闭时，更新日期显示

        };

        datepick = new DatePickerDialog(this.getActivity(),dateListener,year,month-1,day);


        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {


                hour = h;
                minute = m;

                updateTime();

            }
            //当DatePickerDialog关闭时，更新日期显示

        };

        timepick = new TimePickerDialog(this.getActivity(),1,timeListener,12,30,true);




    }
    private void updateTime()
    {
        String defaulttime = "";
        if(minute<10) {
            defaulttime =hour+":0"+minute;
        }
        else{
            defaulttime =hour+":"+minute;
        }

        timeselView.setText(defaulttime);
    }

    private void updateDate()
    {
        String defaultdate =year+"年"+month+"月"+day+"日";
        dateselView.setText(defaultdate);
    }
}
