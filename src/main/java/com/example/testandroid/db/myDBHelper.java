package com.example.testandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 2017/7/17.
 */

public class myDBHelper extends SQLiteOpenHelper {
    private static final String name = "weatherknow"; //数据库名称

    private static final int version = 1; //数据库版本

    public myDBHelper(Context context) {

        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类

        super(context, name, null, version);

    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS plan(id integer primary key autoincrement,tittle varchar(20),place varchar(20),event varchar(128),year integer,month integer,day integer,hour integer,minute integer)");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

}
