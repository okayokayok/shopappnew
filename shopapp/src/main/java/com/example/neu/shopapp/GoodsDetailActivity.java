package com.example.neu.shopapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.example.neu.baseinfo.Connectinfo;

import com.example.neu.util.MyApplication;
import com.example.neu.util.MySQLiteQueryHelper;
import com.example.neu.util.MyVolleyRequest;
import com.example.neu.util.PrefStore;
import com.example.neu.util.imagecycleview.ADInfo;
import com.example.neu.util.imagecycleview.ImageCycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodsDetailActivity extends AppCompatActivity implements Response.Listener<JSONObject>
{
    ImageCycleView imageCycleViewDetail;
    TextView textdisc,textname,textmaterial,textprice,textsales,textpostalfee,textcolorsizes;

    ArrayList<ADInfo> aList=new ArrayList<>();
    ImageLoader imageLoader;
    String[] urlslist,colorslist,sizeslist;
    private ArrayList<String> goodsSizes,goodsColors;
    private int REQEUST_CODE=200;

    private Button addcart;
    private MySQLiteQueryHelper myHeler;
    //private Map<String, Object> goodsDetail;
    private JSONObject jsdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        //goodsDetail = new HashMap<String, Object>();
        addcart=(Button) findViewById(R.id.addcart);
        myHeler=new MySQLiteQueryHelper(this,"shop.db",null,1);

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefStore pref=PrefStore.getInstance(GoodsDetailActivity.this);
                String user_name=pref.getPref("userName","");

                String goods_id=jsdetail.optString("goodsId");
                System.out.println("---------"+goods_id);
                String goods_name=jsdetail.optString("goodsName");
                String goods_pic=jsdetail.optString("goodsPic");
                String cs=textcolorsizes.getText().toString();
                String[] s=cs.split("     ");
                String goods_size=s[0].trim().replace("已选:","     ");
                String goods_color=s[1].trim();
                String goods_num=s[2].trim().replace("件","     ");
                SQLiteDatabase db=myHeler.getReadableDatabase();
                Cursor cursor=db.rawQuery("select*from shopcar where goods_id=? and goods_size=? and goods_color=? and user_name=?",new String[]{goods_id,goods_size,goods_color,user_name});
                if (cursor.moveToNext()){
                    db.execSQL("update shopcar set goods_num=goods_num+?where goods_id=? and goods_size=? and goods_color=? and user_name=?",new String[]{goods_num,goods_id,goods_size,goods_color,user_name});

                }else {
                    db.execSQL("insert into shopcar values(null,?,?,?,?,?,?,?)",new String[]{goods_id,goods_name,goods_num,goods_size,goods_color,goods_pic,user_name});
                    Toast.makeText(GoodsDetailActivity.this,"商品添加成功",Toast.LENGTH_LONG).show();
                }
                cursor.close();

            }

        });




        final Intent intent=getIntent();
        textdisc= (TextView) findViewById(R.id.textdisc);
        textname= (TextView) findViewById(R.id.textname);
        textmaterial= (TextView) findViewById(R.id.textmaterial);
        textprice= (TextView) findViewById(R.id.textprice);
        textsales= (TextView) findViewById(R.id.textsales);
        textpostalfee= (TextView) findViewById(R.id.textpostalfee);
        textcolorsizes= (TextView) findViewById(R.id.textcolorsizes);
        imageCycleViewDetail= (ImageCycleView) findViewById(R.id.imageCycleViewDetail);
        addcart=(Button)findViewById(R.id.addcart);

        goodsSizes=new ArrayList<String>();
        goodsColors=new ArrayList<String>();


        String goodsId=intent.getStringExtra("goodsId");
        Map<String,String> params = new HashMap<String, String>();
        params.put("goodsId", goodsId);
       MyVolleyRequest mr = new MyVolleyRequest(Connectinfo.goodsdetailurl,
                params,GoodsDetailActivity.this);

        MyApplication.getInstance().addToRequestQueue(mr);
        //imageCycleViewDetail.setImageResources(aList,new myImageListener());
        imageLoader= MyApplication.getInstance().getImageLoader();
        textcolorsizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GoodsDetailActivity.this,SizeColorActivity.class);
                intent.putStringArrayListExtra("sizes",goodsSizes);
                intent.putStringArrayListExtra("colors",goodsColors);
                String cs=textcolorsizes.getText().toString();

                String[] s=cs.split("     ");
                String goods_size=s[0].trim().replace("已选：","");
                String goods_color=s[1].trim();
                String goods_num=s[2].trim().replace("件","");
                intent.putExtra("goods_size",goods_size);
                intent.putExtra("goods_color",goods_color);
                intent.putExtra("goods_num",goods_num);
                System.out.println("================goodsNum:"+goods_num);
                GoodsDetailActivity.this.startActivityForResult(intent,REQEUST_CODE);

            }
        });

    }

//    private void parse(JSONObject jo) {
//        if (jo != null) {
//            JSONObject jsonObj = jo.optJSONObject("goodsDetail");
//            if (jsonObj != null) {
//                for (int i=0;i<jsonObj.length();i++){
//                    Map<String,String> m=new HashMap<>();
//                    JSONObject a=jsonObj.optJSONObject(i);
//                    m.put("url",Connectinfo.contexturl+a.optString("catePic"));
//                    m.put("id",a.optString("cateId"));
//                    m.put("text",a.optString("cateName"));
//                    //+ "\r\n现售： ￥"+a.optString("goodsDiscount")
//                    data3.add(m);
//            }
//            else{
//                Toast.makeText(GoodsDetailActivity.this, "抱歉，暂无该商品信息！", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    class myImageListener implements ImageCycleView.ImageCycleViewListener{

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            imageLoader.get(imageURL,
                    ImageLoader.getImageListener(
                            imageView,R.drawable.default_photo,R.drawable.error_photo));
        }

        @Override
        public void onImageClick(ADInfo info, int postion, View imageView) {

        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject!=null) {
            jsdetail = jsonObject.optJSONObject("goodsDetail");
            JSONObject jscolors = jsonObject.optJSONObject("goodsColors");
            JSONObject jssizes = jsonObject.optJSONObject("goodsSizes");
            JSONArray japic = jsdetail.optJSONArray("pics");
            JSONArray jasizes = jssizes.optJSONArray("sizes");
            JSONArray jacolors = jscolors.optJSONArray("colors");
            Map<String,String> dm=new HashMap<>();
//            dm.put("goodsPic",goodsPic);
            dm.put("goodsId",jsdetail.optString("goodsId"));
            dm.put("cateId",jsdetail.optString("cateId"));
            dm.put("goodsDisc",jsdetail.optString("goodsDisc"));
            dm.put("goodsName",jsdetail.optString("goodsName"));
            dm.put("goodsPrice",jsdetail.optString("goodsPrice"));
            dm.put("goodsDiscount",jsdetail.optString("goodsDiscount"));
            dm.put("goodsStock",jsdetail.optString("goodsStock"));
            dm.put("goodsOrigin",jsdetail.optString("goodsOrigin"));
            dm.put("goodsMaterial",jsdetail.optString("goodsMaterial"));
            dm.put("goodsPostalfee",jsdetail.optString("goodsPostalfee"));
            dm.put("goodsDate",jsdetail.optString("goodsDate"));
            dm.put("goodsSales",jsdetail.optString("goodsSales"));
            urlslist=new String[japic.length()];
            colorslist=new String[jacolors.length()];
            sizeslist=new String[jasizes.length()];
            for (int i=0;i<japic.length();i++)
            {


                JSONObject js=japic.optJSONObject(i);
                urlslist[i]=Connectinfo.contexturl+js.optString("picUrl");
            }
            for(int i=0;i<urlslist.length;i++){
                ADInfo ad=new ADInfo();
                ad.setUrl(urlslist[i]);

                aList.add(ad);
            }
            imageCycleViewDetail.setImageResources(aList,new myImageListener());

            JSONObject jos = jsonObject.optJSONObject("goodsSizes");
            JSONArray sizes=jos.optJSONArray("sizes");
            if(sizes!=null&&0<sizes.length()){
                for(int i=0;i<sizes.length();i++){
                    JSONObject josize = sizes.optJSONObject(i);
                    goodsSizes.add(josize.optString("sizeName"));
                }
            }


            JSONObject jjs = jsonObject.optJSONObject("goodsColors");
            System.out.println("=============================="+jjs);
            JSONArray colors=jjs.optJSONArray("colors");
            if(colors!=null&&0<colors.length()){
                for(int i=0;i<colors.length();i++){
                    JSONObject jocolor = colors.optJSONObject(i);
                    goodsColors.add(jocolor.optString("colorName"));
                }
            }




            textcolorsizes.setText("已选："+goodsSizes.get(0).toString()
                    +"     "+goodsColors.get(0).toString()
                    +"     1件");

            Map<String,String> cm=new HashMap<>();
            Map<String,String> sm=new HashMap<>();
            textdisc.setText(dm.get("goodsDisc"));
            textmaterial.setText(dm.get("goodsMaterial"));
            textname.setText(dm.get("goodsName"));
            textprice.setText("原价：￥"+dm.get("goodsPrice")+"    "+"现售：￥"+dm.get("goodsDiscount"));
            textpostalfee.setText("运费：￥"+dm.get("goodsPostalfee"));
            textsales.setText("共售出"+dm.get("goodsSales")+"件");
            //textcolorsizes.setText("已选："+"超级大"+"  "+"彩色"+"  "+"1件");
            for (int i=0;i<urlslist.length;i++){
                //Toast.makeText(this, "aList"+urlslist[i], Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQEUST_CODE){
            if (data!=null){
                textcolorsizes.setText("已选："+data.getStringExtra("selSize")+"     "+data.getStringExtra("selColor")+"     "+data.getStringExtra("selNum")+"件");
            }
        }
    }
}
