package com.example.neu.shopapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyListViewAdapter;
import com.example.neu.util.MySQLiteQueryHelper;
import com.example.neu.util.PrefStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.neu.shopapp.R.drawable.shopcar;



/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends Fragment {

    private ArrayList<Map<String,String>> data;
    private MySQLiteQueryHelper myHelper;
    private MyListViewAdapter adapter;
    private Button addorder;
    private ListView shopcar;


    public ShopCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shop_car, container, false);
        shopcar=(ListView) view.findViewById(R.id.shopcar);
        data=new ArrayList<Map<String, String>>();
        addorder=(Button) view.findViewById(R.id.addorder);
        refreshShoopData();
        adapter=new MyListViewAdapter(data,R.layout.shopcar_list_view_item);
        shopcar.setAdapter(adapter);
        if (data==null||data.size()==0){
            addorder.setText("当前购物车为空，快去购物吧");
            addorder.setEnabled(false);
        }


        return view;
    }

    private void refreshShoopData() {
        PrefStore pref=PrefStore.getInstance();
        String user_name=pref.getPref("userName","     ");
        myHelper=new MySQLiteQueryHelper(this.getActivity(),"shop.db",null,1);
        SQLiteDatabase db=myHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select*from shopcar where user_name=?",new String[]{user_name});
        data.clear();

        while (cursor.moveToNext()){
            String cs=cursor.getString(4)+"     "+cursor.getString(5)+"     "+cursor.getString(3)
                    +"件";
            Map<String,String> m=new HashMap<String,String>();
            m.put("text1",cursor.getString(2));
            m.put("url", Connectinfo.contexturl+cursor.getString(6));
            m.put("text2",cs);
            m.put("goods_id",cursor.getString(1));
            m.put("goods_num",cursor.getString(3));
            m.put("goods_size",cursor.getString(4));
            m.put("goods_color",cursor.getString(5));
            data.add(m);
        }
        cursor.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            refreshShoopData();
            adapter.notifyDataSetChanged();
        }
    }
}
