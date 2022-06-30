package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommodityManageActivity extends AppCompatActivity {

    private List<CommodityListItem> itemList = new ArrayList<>();
    private ListView listView;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage);

        //启动线程，将数据从DB同步到ArrayList对象
        System.out.println("将数据从DB同步到ArrayList对象");
        CommoditySyncThread myThread = new CommoditySyncThread();
        Thread thread = new Thread(myThread);
        thread.start();
        System.out.println("显示数据");
        while (true) {
            if (flag == 1) {
                ListView listView = findViewById(R.id.list_item);
                String[] test = {"1   辣条   食品   5   10.00"};
//                MyAdapter myAdapter = new MyAdapter(this, android.R.layout.simple_list_item_1,
//                        itemList);
//                listView.setAdapter(myAdapter);
//                ArrayAdapter<List> arrayAdapter = new ArrayAdapter<List>(this, android.R.layout.simple_list_item_1, Collections.singletonList(itemList));
//                listView.setAdapter(arrayAdapter);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
                listView.setAdapter(arrayAdapter);
                break;
            }
        }
        System.out.println("显示成功");
    }

    //同步线程
    private class CommoditySyncThread implements Runnable{
        @Override
        public void run() {
            try {
                itemList = DBUtil.CommodityList();
                System.out.println("同步Commodity：成功");
                flag = 1;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("同步Commodity：失败");
            }
        }
    }

    //适配器类
    class MyAdapter extends ArrayAdapter {
        private final int resourceId; //ListView子项布局ID

        public MyAdapter(Context context, int resource, List<CommodityListItem> objects) {
            super(context, resource, objects);
            resourceId = R.id.list_item;    //ListItem布局ID
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){//position  相当于索引*/
            CommodityListItem item = (CommodityListItem) getItem(position); //获取当前项的Item实例
            //为ListView 子项加载传入的布局
            View view =
                    LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
            //列表控件资源的id、ViewGroup类型视图组对象、false
            //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局

            TextView comKeyView =   (TextView)view.findViewById(R.id.list_item_commodity_comKey);
            TextView nameView =     (TextView)view.findViewById(R.id.list_item_commodity_name);
            TextView kindView =     (TextView)view.findViewById(R.id.list_item_commodity_kind);
            TextView numView =      (TextView)view.findViewById(R.id.list_item_commodity_num);
            TextView priceView =    (TextView)view.findViewById(R.id.list_item_commodity_price);
            comKeyView.setText(item.comKey);
            nameView.setText(item.name);
            kindView.setText(item.kind);
            numView.setText(item.num.toString());
            priceView.setText(item.price.toString());
            return view;/*返回子项布局*/
        }
    }
}




