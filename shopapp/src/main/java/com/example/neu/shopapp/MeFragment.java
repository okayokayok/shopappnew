package com.example.neu.shopapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements Response.Listener<JSONObject> {

    ListView order;
    List<Map<String,Object>> data;
    SimpleAdapter adapter;
    ProgressBar pb;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_me,container,false);
        pb=(ProgressBar) view.findViewById(R.id.progressBar);
        data=new ArrayList<Map<String,Object>>();
        order=(ListView)view.findViewById(R.id.order);
        adapter=new SimpleAdapter(this.getActivity(),data,R.layout.order_list_view_item,new String[]{"code",
        "date","status"},new int[]{R.id.code,R.id.date,R.id.status});
        order.setAdapter(adapter);
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.orderurl,this);
        MyApplication.getInstance().addToRequestQueue(mr);
        return view;
    }

    @Override
    public void onResponse(JSONObject response) {
        if (response!=null){
            parse(response);
            pb.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void parse(JSONObject jo) {
        if (jo!=null){
            JSONArray jsonArray=jo.optJSONArray("ordersList");
            if (jsonArray!=null){
                int length=jsonArray.length();
                if (0<length){
                    for (int i=0;i<length;i++){
                        JSONObject jsonObj=jsonArray.optJSONObject(i);
                        if (jsonObj==null)
                            continue;
                        Map<String,Object> m=new HashMap<>();
                        m.put("id",jsonObj.optString("orderId"));
                        m.put("code","订单号："+jsonObj.optString("orderCode"));
                        m.put("date","下单日期："+jsonObj.optString("orderDate"));
                        int status=jsonObj.optInt("orderStatus");
                        String str="";
                        switch (status){
                                case 0:
                                    str="等待付款";
                                    break;
                                case 1:
                                    str="未发货";
                                    break;
                                case 2:
                                    str="申请退款中";
                                    break;
                                case 3:
                                    str="退款成功";
                                    break;
                                case 4:
                                    str="已发货";
                                    break;
                                case 5:
                                    str="交易成功";
                                    break;
                                case 6:
                                    str="申请退货中";
                                    break;
                                case 7:
                                    str="退货中";
                                    break;
                                case 8:
                                    str="退货成功";
                                    break;
                                case 9:
                                    str="交易关闭";
                                    break;
                            }
                            m.put("status",str);
                            data.add(m);
                        }
                    }
                }else{
                    Toast.makeText(MeFragment.this.getActivity(),"抱歉，您还没有任何订单！",Toast.LENGTH_LONG).show();

                }
            }
        }


    private String changeDate(String time) {
        Date date=new Date(Long.valueOf(time)+28800000);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
