package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btnInsert;
    Button btnShow;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBUtil.Connect();

        btnCreate = (Button) findViewById(R.id.btn_creat);//引用组件
        //为按钮设置监听器，使用匿名内部类
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtil.CreateAll();
            }
        });

        btnInsert = (Button) findViewById(R.id.btn_insert);//引用组件
        //为按钮设置监听器，使用匿名内部类
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("跳转到商品管理");
                Intent intent =
                        new Intent(MainActivity.this, CommodityManageActivity.class);
                startActivity(intent);
            }
        });

    }

}