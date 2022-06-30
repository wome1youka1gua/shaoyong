package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityManageActivity extends AppCompatActivity {

    private List<Map<String, String>> itemMap = new ArrayList<>();
    private SimpleAdapter adapter;
    private ListView listView;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage);

        listView = (ListView) findViewById(R.id.commodity_list);
        //启动线程，将数据从DB同步到ArrayList对象
        System.out.println("将数据从DB同步到ArrayList");
        CommoditySyncThread myThread = new CommoditySyncThread();
        Thread thread = new Thread(myThread);
        thread.start();

        //连接适配器
        while(true) {
            if(flag){
                adapter = new SimpleAdapter(
                        CommodityManageActivity.this,
                        itemMap,
                        R.layout.list_item_commodity,
                        new String[]{"comKey", "name", "kind", "num", "price"},
                        new int[]{R.id.list_item_commodity_comKey,
                                R.id.list_item_commodity_name,
                                R.id.list_item_commodity_kind,
                                R.id.list_item_commodity_num,
                                R.id.list_item_commodity_price});
                listView.setAdapter(adapter);
                break;
            }
        }

//        MyAdapter myAdapter = new MyAdapter(
//                CommodityManageActivity.this, R.layout.activity_store_manage, itemList);
//        listView = (ListView)findViewById(R.id.list_item);
//        listView.setAdapter(myAdapter);
    }

    //同步线程
    private class CommoditySyncThread implements Runnable{
        @Override
        public void run() {
            try {
                itemMap = DBUtil.CommodityList();
                System.out.println("同步Commodity：成功");
                flag = true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("同步Commodity：失败");
            }
        }
    }

    //适配器类
//    class MyAdapter extends ArrayAdapter {
//        private final int resourceId; //ListView子项布局ID
//
//        public MyAdapter(Context context, int resource, List<CommodityListItem> objects) {
//            super(context, resource, objects);
//            resourceId = resource;    //ListItem布局ID
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){//position 相当于索引
//            CommodityListItem item = (CommodityListItem) getItem(position); //获取当前项的Item实例
//            //为ListView 子项加载传入的布局
//            View view =
//                    LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
//            //列表控件资源的id、ViewGroup类型视图组对象、false
//            //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
//
//            TextView comKeyView =   (TextView)view.findViewById(R.id.list_item_commodity_comKey);
//            TextView nameView =     (TextView)view.findViewById(R.id.list_item_commodity_name);
//            TextView kindView =     (TextView)view.findViewById(R.id.list_item_commodity_kind);
//            TextView numView =      (TextView)view.findViewById(R.id.list_item_commodity_num);
//            TextView priceView =    (TextView)view.findViewById(R.id.list_item_commodity_price);
//            comKeyView.setText(item.comKey);
//            nameView.setText(item.name);
//            kindView.setText(item.kind);
//            numView.setText(item.num.toString());
//            priceView.setText(item.price.toString());
//            return view;/*返回子项布局*/
//        }
//    }

}




