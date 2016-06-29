package com.beibaoke;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;;
import android.widget.AdapterView.OnItemClickListener;



public class JiaoyouActivity extends MainActivity {
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaoyou);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        }
public void dengluIntent(View view) {
                Intent intent = new Intent(this, DengluActivity.class);

                startActivity(intent);
        }
        public void wodehaoyouIntent(View view) {
                Intent intent = new Intent(this, WodehaoyouActivity.class);

                startActivity(intent);
        }
        public void xunzhaohaoyouIntent(View view) {
                Intent intent = new Intent(this, XunzhaohaoyouActivity.class);

                startActivity(intent);
        }
        private static final char[] wJ = "0123456789abcdef".toCharArray();
        public static String imsi = "204046330839890";
        public static String p = "0";
        public static String keyword = "电话";
        public static String tranlateKeyword = "%E7%94%B5%E8%AF%9D";
//    Note:TO prevent coping code

        }

