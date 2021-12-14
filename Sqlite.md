package com.example.zheng.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context) {
        super(context, "SQL.db", null, 1);
        //第一个参数对应的是一个上下文，第二个参数对应的是数据库的名称，第三个参数一般为空就好了，第四个参数对应的是数据库的版本号默认为一就好了
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {//创建表
        String sql="create table user(name varchar(30),class varchar(30),age Integer,number varchar(30))";
        //创建一个表，表的名称叫user这里面会有四个字段，Integer是整形,varchar数据量的大小是根据后面括号进行变化
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
