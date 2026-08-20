package com.example.zheng.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed_name,ed_class,ed_age,ed_ID,ed_select_number;
    private Button btn_insert,btn_select,btn_update,btndelete;
    private TextView findResult;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();//初始化控件
        SQLite sqLite=new SQLite(this);//实例化对象，对象为初始创建的名称
        database = sqLite.getWritableDatabase();//读写操作

    }

    private void Init() {
        ed_name=findViewById(R.id.ed_name);
        ed_class=findViewById(R.id.ed_class);
        ed_age=findViewById(R.id.ed_age);
        ed_ID=findViewById(R.id.ed_number);
        ed_select_number=findViewById(R.id.ed_select_number);
        btn_insert=findViewById(R.id.btn_insert);
        btn_select=findViewById(R.id.btn_select);
        btn_update=findViewById(R.id.btn_update);
        btndelete=findViewById(R.id.btn_delete);
        findResult=findViewById(R.id.textView);
        btn_insert.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sql;
        //这里需要注意一点，敲出来的逗号一定要是英文的，否则会报错
        switch (view.getId())
        {
            case R.id.btn_insert://添加
                sql="insert into user(name,class,age,number)values(?,?,?,?)";//这里的表名和创建时的对应，字段名称和创建时的对应
                database.execSQL(sql,new Object[]{ed_name.getText().toString(),ed_class.getText().toString(),Integer.parseInt(ed_age.getText().toString()),
                ed_ID.getText().toString()});//Integer.parseInt()是对字符串进行转换，转换为int类型
                Toast.makeText(this, "数据添加成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete://删除
                sql="delete from user where number=?";
                database.execSQL(sql,new Object[]{ed_ID.getText().toString()});
                Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_select://查询
                try{
                    findResult.setText("");
                    sql="select * from user where number=?";//查询字段名称number
                    Cursor cursor = database.rawQuery(sql, new String[]{ed_select_number.getText().toString()});//获取一个游标
                    while(cursor.moveToNext())//判断是否可以移动到下一行
                    {
                        //获取这一行名称的数据,对应的是添加时的字段（列名称）
                        String s_name=cursor.getString(cursor.getColumnIndex("name"));
                        String s_class=cursor.getString(cursor.getColumnIndex("class"));
                        int i_age=cursor.getInt(cursor.getColumnIndex("age"));
                        String s_ID=cursor.getString(cursor.getColumnIndex("number"));
                        findResult.setText("姓名："+s_name+"\n"+"班级："+s_class+"\n"+"年龄："+i_age+"\n"+"编号："+s_ID);
                        Toast.makeText(this, "查询成功!", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_update://修改
                sql="update user set number=?,name=?,class=?,age=? where number=?";//通过查询编号进行修改
                 database.execSQL(sql,new Object[]{ed_ID.getText().toString(),ed_name.getText().toString(),ed_class.getText().toString(),
                         Integer.parseInt(ed_age.getText().toString()),ed_select_number.getText().toString()});
                Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
