package com.example.neu.shopapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyGridView;
import com.example.neu.util.MyGridViewAdapter;
import com.example.neu.util.MyVolleyRequest;
import com.example.neu.util.imagecycleview.ADInfo;
import com.example.neu.util.imagecycleview.ImageCycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ImageCycleView adView;
    private ArrayList<ADInfo> adList=new ArrayList<>();
    private String[] urls={Connectinfo.adImg1,Connectinfo.adImg2,Connectinfo.adImg3};
    private ImageLoader imageLoader;

    private MyGridView newGoods;
    private MyGridViewAdapter adapter1;
    private ArrayList<Map<String,String>> data1;

    private MyGridView salesGoods;
    private MyGridViewAdapter adapter2;
    private ArrayList<Map<String,String>> data2;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        if (adList!=null&&adList.size()==0)
            for (int i=0;i<urls.length;i++){
                ADInfo ad=new ADInfo();
                ad.setUrl(urls[i]);
                adList.add(ad);
        }
        adView=(ImageCycleView) view.findViewById(R.id.ad_view);
        adView.setImageResources(adList,new MyImageListener());
        imageLoader= MyApplication.getInstance().getImageLoader();

        newGoods=(MyGridView) view.findViewById(R.id.newGoods);
        data1=new ArrayList<>();
        adapter1=new MyGridViewAdapter(data1,R.layout.goods_grid_view_item);
        newGoods.setAdapter(adapter1);
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.newgoodsurl,new NewGoodsListener());
        MyApplication.getInstance().addToRequestQueue(mr);

        salesGoods=(MyGridView) view.findViewById(R.id.saleGoods);
        data2=new ArrayList<>();
        adapter2=new MyGridViewAdapter(data2,R.layout.goods_grid_view_item);
        salesGoods.setAdapter(adapter2);
        MyVolleyRequest nmr=new MyVolleyRequest(Connectinfo.salesgoodsurl,new SaleGoodsListener());
        MyApplication.getInstance().addToRequestQueue(nmr);



        return view;
    }

    private class MyImageListener implements ImageCycleView.ImageCycleViewListener {
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            imageLoader.get(imageURL,ImageLoader.getImageListener(imageView,R.drawable.default_photo,R.drawable.error_photo));
        }

        @Override
        public void onImageClick(ADInfo info, int postion, View imageView) {

        }
    }

    private class NewGoodsListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {
            if (jsonObject!=null){
                parse(jsonObject);
                adapter1.notifyDataSetChanged();
            }
        }
    }

    private void parse(JSONObject jsonObject) {
       JSONArray ja= jsonObject.optJSONArray("newGoods");
        if (ja!=null){
            if (ja.length()>0){
                for (int i=0;i<ja.length();i++){
                    Map<String,String> m=new HashMap<>();
                    JSONObject a=ja.optJSONObject(i);
                    m.put("url",Connectinfo.contexturl+a.optString("goodsPic"));
                    m.put("id",a.optString("goodsId"));
                    m.put("text",a.optString("goodsName")+ "\r\n现售： ￥"+a.optString("goodsDiscount"));
                    data1.add(m);
                }
            }else{
                Toast.makeText(HomeFragment.this.getActivity(),"当前没有新到商品",Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SaleGoodsListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {
            if (jsonObject!=null){
                parseNew(jsonObject);
                adapter2.notifyDataSetChanged();
            }
        }
    }

    private void parseNew(JSONObject jsonObject) {

        JSONArray j= jsonObject.optJSONArray("salesGoods");
        if (j!=null){
            if (j.length()>0){
                for (int i=0;i<j.length();i++){
                    Map<String,String> m=new HashMap<>();
                    JSONObject a=j.optJSONObject(i);
                    m.put("url",Connectinfo.contexturl+a.optString("goodsPic"));
                    m.put("id",a.optString("goodsId"));
                    m.put("text",a.optString("goodsName")+ "\r\n现售： ￥"+a.optString("goodsDiscount"));
                    data2.add(m);
                }
            }else{
                Toast.makeText(HomeFragment.this.getActivity(),"当前没有热销商品",Toast.LENGTH_LONG).show();
            }
        }

    }
}
