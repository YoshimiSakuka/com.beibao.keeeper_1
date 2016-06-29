package com.beibaoke;

import com.nineoldandroids.animation.*;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import android.net.Uri;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private ImageView btn_dial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_component();

    }
    public void init_component() {
        btn_dial = (ImageView) this.findViewById(R.id.bohaobutton);
        //        btn_dial.setOnClickListener(new ButtonOnclickListener());//使用内部类进行按钮监听的注册
        btn_dial.setOnClickListener(new OnClickListener() {//使用匿名内部类，当代码较多时，很难看懂，建议使用ButtonOnclickListener内部类

            @Override
            public void onClick(View v) {
                String num = "110";
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");//调用android自带的拨号Activity
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("tel:"+num));
                startActivity(intent);//启动系统中的任何Acitivity

            }
        });
//        edt_mobile = (EditText) this.findViewById(R.id.edt_mobile);
//        txv_mobile = (TextView) this.findViewById(R.id.txv_mobile);//通过此方法获得布局文件中的控件（但是在布局文件中必须声明控件ID，以供查找）
    }


    /**
     * 当用户点击按钮的时候被调用
     */
    public void fangdaoIntent(View view) {
        Intent intent = new Intent(this, FangdaoActivity.class);

        startActivity(intent);
    }
    public void zujiIntent(View view) {
        Intent intent = new Intent(this, ZujiActivity.class);

        startActivity(intent);
    }
    public void jihuaIntent(View view) {
        Intent intent = new Intent(this, JihuaActivity.class);

        startActivity(intent);
    }
    public void rijiIntent(View view) {
        Intent intent = new Intent(this, RijiActivity.class);

        startActivity(intent);
    }
    public void fenxiangyixiaIntent(View view) {
        Intent intent = new Intent(this, FenxiangActivity.class);

        startActivity(intent);
    }
    public void finishIntent(View view) {
        finish();
    }
    public void wodehaoyouIntent(View view) {
        Intent intent = new Intent(this, WodehaoyouActivity.class);

        startActivity(intent);
    }

    public void clockIntent(View view) {
        Intent intent = new Intent(this, ClockActivity.class);

        startActivity(intent);
    }
    public void startclockIntent(View view) {
        Intent intent = new Intent(this, StartclockActivity.class);

        startActivity(intent);
    }
    public void cameraIntent(View view) {
        Intent intent = new Intent(this, CameraActivity.class);

        startActivity(intent);
    }

    public void photoIntent(View view) {
        Intent intent = new Intent(this,PhotoActivity.class);

        startActivity(intent);
    }

    public void wodeIntent(View view) {
        Intent intent = new Intent(this, WodeActivity.class);

        startActivity(intent);
    }

    public void fandianIntent(View view) {
        Intent intent = new Intent(this, FandianActivity.class);

        startActivity(intent);
    }



    /**
     * 当用户点击按钮的时候被调用
     */
    public void jiaoyouIntent(View view) {
        Intent intent = new Intent(this, JiaoyouActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void jiudianIntent(View view) {
        Intent intent = new Intent(this, JiudianActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void lvyouIntent(View view) {
        Intent intent = new Intent(this, LvyouActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void zixingcheIntent(View view) {
        Intent intent = new Intent(this, ZixingcheActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void mingxieIntent(View view) {
        Intent intent = new Intent(this, MingxieActivity.class);

        startActivity(intent);
    }
    /**
     * 当用户点击按钮的时候被调用
     */
    public void shiwuzhaolingIntent(View view) {
        Intent intent = new Intent(this, ShiwuzhaolingActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void qicaiIntent(View view) {
        Intent intent = new Intent(this, QicaiActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void luntanIntent(View view) {
        Intent intent = new Intent(this, LuntanActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void tiebaIntent(View view) {
        Intent intent = new Intent(this, TiebaActivity.class);

        startActivity(intent);
    }

    /**
     * 当用户点击按钮的时候被调用
     */
    public void jiqiaoIntent(View view) {
        Intent intent = new Intent(this, JiqiaoActivity.class);

        startActivity(intent);
    }
    public void pingfenIntent(View view) {
        Intent intent = new Intent(this, PingfenActivity.class);

        startActivity(intent);
    }
    /**
     * 当用户点击按钮的时候被调用
     */
    public void jibenzhuangbeiIntent(View view) {
        Intent intent = new Intent(this, JibenzhuangbeiActivity.class);

        startActivity(intent);
    }
    private static final char[] wJ = "0123456789abcdef".toCharArray();
    public static String imsi = "204046330839890";
    public static String p = "0";
    public static String keyword = "电话";
    public static String tranlateKeyword = "%E7%94%B5%E8%AF%9D";
//    Note:TO prevent coping code
boolean isRunningInEmualtor() {
    boolean qemuKernel = false;
    Process process = null;
    DataOutputStream os = null;
    try{
        process = Runtime.getRuntime().exec("getprop ro.kernel.qemu");
        os = new DataOutputStream(process.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
        os.writeBytes("exit\n");
        os.flush();
        process.waitFor();
        // getprop ro.kernel.qemu == 1  在模拟器
        // getprop ro.product.model == "sdk"  在模拟器
        // getprop ro.build.tags == "test-keys"  在模拟器
        qemuKernel = (Integer.valueOf(in.readLine()) == 1);
        Log.d("com.droider.checkqemu", "检测到模拟器:" + qemuKernel);
    } catch (Exception e){
        qemuKernel = false;
        Log.d("com.droider.checkqemu", "run failed" + e.getMessage());
    } finally {
        try{
            if (os != null) {
                os.close();
            }
            process.destroy();
        } catch (Exception e) {

        }
        Log.d("com.droider.checkqemu", "run finally");
    }
    return qemuKernel;
}
public int getSignature(String packageName) {
    PackageManager pm = this.getPackageManager();
    PackageInfo pi = null;
    int sig = 0;
    try {
        pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        Signature[] s = pi.signatures;
        sig = s[0].hashCode();
    } catch (Exception e1) {
        sig = 0;
        e1.printStackTrace();
    }
    return sig;
}

}