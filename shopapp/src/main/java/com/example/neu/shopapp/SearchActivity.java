package com.example.neu.shopapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyGridViewAdapter;
import com.example.neu.util.MyVolleyRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements Response.Listener<JSONObject> {

    private String keyword;
    private GridView goods;
    private List<Map<String,String>> data;
    private MyGridViewAdapter adapter;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        pb=(ProgressBar) this.findViewById(R.id.progressBar);
        goods=(GridView) this.findViewById(R.id.goods);
        data=new ArrayList<Map<String, String>>();
        adapter=new MyGridViewAdapter(data);
        goods.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                goods.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (goods.getNumColumns() > 0) {
                    int ColumnWidth = (goods.getWidth() / goods.getNumColumns()) - goods.getHorizontalSpacing();
                    adapter.setItemWidth(ColumnWidth);
                }

            }
        });





        goods.setAdapter(adapter);

        Map<String,String> params=new HashMap<>();
        params.put("keyword","");
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.searchgoodsurl,params,SearchActivity.this);
        MyApplication.getInstance().addToRequestQueue(mr);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void parse(JSONObject jo) {
        if (jo!=null){
            JSONArray jsonArray=jo.optJSONArray("goodsList");
            if (jsonArray!=null){
                int length=jsonArray.length();
                if (0<length){
                    for (int i=0;i<length;i++){
                        JSONObject jsonObj=jsonArray.optJSONObject(i);
                        Map<String,String> m=new HashMap<>();
                        m.put("id",jsonObj.optString("goodsId"));
                        m.put("text",jsonObj.optString("goodsName")+"\r\n原价：￥"+jsonObj.optString(
                                "goodsPrice")+"\r\n现售：￥"+jsonObj.optString("goodsDiscount"));
                        m.put("url",Connectinfo.contexturl+jsonObj.optString("goodsPic"));
                        data.add(m);

                    }
                }
            }else {
                Toast.makeText(this,"抱歉，没有查找到您所需的商品!",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        keyword=intent.getStringExtra(SearchManager.QUERY);
        System.out.println("keyword:"+keyword);
        Map<String,String> params=new HashMap<String, String>();
        params.put("keyword",keyword);
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.searchgoodsurl,params,SearchActivity.this);
        MyApplication.getInstance().addToRequestQueue(mr);
    }

    @Override
    public void onResponse(JSONObject response) {
        if (response!=null){
            data.clear();
            parse(response);
            pb.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}
