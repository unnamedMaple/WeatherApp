package com.example.testandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.testandroid.bean.plan;

/**
 * Created by ASUS on 2017/7/21.
 */

public class dbManager {


    public void insertPlan(Context context,plan one){
        myDBHelper help = new myDBHelper(context);
        SQLiteDatabase db = help.getWritableDatabase();

        //"id,tittle,place ,event,year ,month,day ,hour ,minute "
        String sql = "insert into plan values(null,'"+one.getTittle()+"','"+
                                                      one.getPlace()+"','"+
                                                      one.getEvent()+"',"+
                                                      one.getYear()+","+
                                                      one.getMonth()+","+
                                                      one.getDay()+","+
                                                      one.getHour()+","+
                                                      one.getMinute()+")";
        db.execSQL(sql);
        help.close();
    }

    public void updatePlan(Context context,plan one){
        myDBHelper help = new myDBHelper(context);
        SQLiteDatabase db = help.getWritableDatabase();
        String sql = "update plan set tittle='"+one.getTittle()+"',"+
                                      "place='"+one.getPlace()+"',"+
                                       "event='"+one.getEvent()+"',"+
                                        "year=" +one.getYear()+","+
                                         "month="+one.getMonth()+","+
                                         "day="+ one.getDay()+","+
                                         "hour="+one.getHour()+","+
                                         "minute="+one.getMinute()+
                                          " where id="+one.getId();
        db.execSQL(sql);
        help.close();
    }

    public void deletePlan(Context context,int id){
        myDBHelper help = new myDBHelper(context);
        SQLiteDatabase db = help.getWritableDatabase();
        String sql = "delete from plan where id="+id;
        db.execSQL(sql);
        help.close();
    }



}
