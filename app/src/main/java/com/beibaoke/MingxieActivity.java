package com.beibaoke;

import com.nineoldandroids.animation.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.SimpleDateFormat;

public class MingxieActivity extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingxie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HtmlTextView text = (HtmlTextView) findViewById(R.id.html_text);


// loads html from string and displays cat_pic.png from the app's drawable folder
        text.setHtmlFromString("<h2>关于</h2><ul><li>本程序是一个为旅行爱好者（背包客）量身定做的应用，具有全功能的特性。</li><li>协议：GPL V3+</li></ul><h3>库和资源</h3><ul><li>v4 Appcompat Library(Google)</li><li>v7 Appcompat Library(Google)</li><li>v7 Cardview Library(Google)</li><li>v7 Recyclerview Library(Google)</li><li>FloatingActionButton(Jerzy Chalupski)</li><li>MaterialTabs(Karim Frenn)</li><li>HtmlTextView(Mohammed Lakkadshaw)</li><li>Android Icon Pack & Action Bar Pack icons(Google)</li><li>NineOldAndroids(Jake Wharton)</li><li>Google Map Sdk v2(Google)</li></ul><h3>作者</h3><ul><li>于红博（maintainer）</li><li>谢洪乐</li><li>黄涛（画师）</li></ul><h3>开源许可</h3><ul><li>GPL v3</li><li>GPL v3+</li><li>Apache License v2.0</li><li>Creative Common Attribution 4.0 International License (CC-BY 4.0)</li></ul><h3>CHANGELOG</h3><ul><li>V1.6压缩无用资源，准备发布</li><li>V1.5修复地图不能定位问题</li><li>V1.4改用系统相机，弃用自编相机</li><li>V1.3弃用百度MAPSDK，改用高德地图</li><li>V1.2加入图标</li><li>V1.1改进操作逻辑流程</li><li>V1.0_beta二级菜单构建完毕</li><li>V0.3加入百度sdk和开源项目</li><li>V0.2添加相机功能</li><li>V0.1一级菜单构建完毕</li></ul>", true);

        TextView dateText = (TextView) findViewById(R.id.date);

        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd    hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        dateText.setText(date);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    public void dengluIntent(View view) {
        Intent intent = new Intent(this, DengluActivity.class);

        startActivity(intent);
    }
    private static final char[] wJ = "0123456789abcdef".toCharArray();
    public static String imsi = "204046330839890";
    public static String p = "0";
    public static String keyword = "电话";
    public static String tranlateKeyword = "%E7%94%B5%E8%AF%9D";
//    Note:TO prevent coping code
}
