package com.beibaoke;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class FangdaoActivity extends MainActivity {


    private SoundPool soundPool;
    private int soundNum;
    private TextView textViewInfo = null;
    private TextView textViewX = null;
    private TextView textViewY = null;
    private TextView textViewZ = null;
    private SensorManager sensorManager = null;
    private Sensor sensor = null;
    private float gravity[] = new float[3];
    private static final int FORCE_THRESHOLD = 350;
    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private long mLastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangdao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        soundPool= new SoundPool(5,AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this,R.raw.warning,1);
        // 获取传感器管理器
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 获取加速度传感器
        Sensor acceleromererSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(listener, acceleromererSensor, SensorManager.SENSOR_DELAY_NORMAL);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "报警停止", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                soundPool.pause(soundNum);
            }
        });
    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {

        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            long now = System.currentTimeMillis();
            long diff = now - mLastTime;
            float speed = Math.abs(event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                soundNum=soundPool.play(1,1, 1, 0, -1, 1);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(listener);
    }

}

