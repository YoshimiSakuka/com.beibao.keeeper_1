package com.beibaoke;
import java.io.File;
import java.io.FileOutputStream;
import com.nineoldandroids.animation.*;
import java.io.IOException;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 照相机
 */
public class CameraActivity extends Activity {

    private static final String TAG = "MainActivity";
    private SurfaceView surfaceView;
    private Camera camera;
    private ImageButton startCaemraBtn;
    private ImageButton flashModeBtn;

    // Popwindow显示
    private PopupWindow tipPopupWindow;
    private RelativeLayout layout;
    private ListView listView;
    private String title[] = { "自动", "开启", "关闭" };
    private AudioManager mAudioManager;
    private int ringMode;
    public static Bitmap prviewImageBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);
        initView();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setRingMode(mAudioManager.getRingerMode());
        Toast.makeText(CameraActivity.this, "进入无声拍照模式！", Toast.LENGTH_SHORT)
                .show();

    }

    // 初始化界面的UI
    private void initView() {
        startCaemraBtn = (ImageButton) findViewById(R.id.startCameraBtn);
        flashModeBtn = (ImageButton) findViewById(R.id.flashModeBtn);

        startCaemraBtn.setOnClickListener(new startCameraOnClick());
        flashModeBtn.setOnClickListener(new flashModeCameraOnClick());

        SharedPreferences sharedPreferences = getSharedPreferences(
                "issetValueXml", Context.MODE_PRIVATE);
        int i = sharedPreferences.getInt("onoff", 0);
        if (i == 0) {
            flashModeBtn.setBackgroundResource(R.drawable.flashmode_auto);
        } else if (i == 1) {
            flashModeBtn.setBackgroundResource(R.drawable.flashmode_open);
        } else if (i == 2) {
            flashModeBtn.setBackgroundResource(R.drawable.flashmode_off);
        }
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().setFixedSize(surfaceView.getWidth(),
                surfaceView.getHeight());
        // 设置SurfaceHolder对象的类型
        surfaceView.getHolder()
                .setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 指定用于捕捉拍照事件的SurfaceHolder.Callback对象
        surfaceView.getHolder().addCallback(new SurfaceCallback());
    }

    // 回调接口
    private final class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Camera.Parameters parameters = camera.getParameters();
            // 根据屏幕方向设置预览尺寸
            parameters.setPreviewSize(256, 256);
            // 下面的参数有可能导致某些手机花屏(后续解决)
//            parameters.setPreviewFrameRate(3);
            // 设置照片格式
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.set("jpeg-quality", 100);
            // 设置拍摄照片的实际分辨率，本例中的分辨率是1024×768
            //parameters.setPictureSize(display.getWidth() / 2,
                    //display.getHeight() / 2);

			 if
			 (CameraActivity.this.getResources().getConfiguration().orientation
			 != Configuration.ORIENTATION_LANDSCAPE) { // 如果是竖屏
			 parameters.set("orientation", "portrait");
			 camera.setDisplayOrientation(90); } else {
			 parameters.set("orientation", "landscape");
			 camera.setDisplayOrientation(0); }
            // 设置保存的图像大小
            camera.setParameters(parameters);

            try {
                // 设置用于显示拍照影像的SurfaceHolder对象
                camera.setPreviewDisplay(surfaceView.getHolder());
                camera.startPreview();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            if (camera == null) return;

        }
    }

    // 回调接口
    private final class TakePictureCallback implements PictureCallback {
        // 该方法用于处理拍摄后的照片数据
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.v("--------", "执行");
            mAudioManager.setRingerMode(getRingMode());
            // data参数值就是照片数据，将这些数据以key-value形式保存，以便其他调用该Activity的程序可以获得照片数据
            try {
                Matrix matrix = new Matrix();
                matrix.reset();
                matrix.setRotate(90);
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.length);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                setPrviewImageBitMap(bitmap);//为预览提供一个BitMap（转换）
                Intent intent = new Intent(CameraActivity.this,
                        PreviewImageView.class);
                startActivity(intent);
                // 停止照片拍摄
                camera.stopPreview();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    // 无声处理
    ShutterCallback sc = new ShutterCallback() {
        @Override
        public void onShutter() {
            Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    };

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            // 结束拍照
            camera.autoFocus(new AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    // success为true表示对焦成功
                    if (success) {
                        SharedPreferences sharedPreferences = getSharedPreferences(
                                "issetValueXml", Context.MODE_PRIVATE);
                        int i = sharedPreferences.getInt("onoff", 2);
                        if (i == 0) {
                            Camera.Parameters parameters = camera
                                    .getParameters();
                            parameters
                                    .setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);
                            camera.setParameters(parameters);
                        } else if (i == 1) {
                            Camera.Parameters parameters = camera
                                    .getParameters();
                            parameters
                                    .setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                            camera.setParameters(parameters);

                        } else if (i == 2) {
                            Camera.Parameters parameters = camera
                                    .getParameters();
                            parameters
                                    .setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camera.setParameters(parameters);
                        }
                    }
                }
            });
        return super.onTouchEvent(event);
    }

    // 快门事件
    class startCameraOnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "issetValueXml", Context.MODE_PRIVATE);
            int i = sharedPreferences.getInt("onoff", 2);
            if (i == 0) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);
                camera.setParameters(parameters);
                camera.takePicture(sc, null, new TakePictureCallback());
            } else if (i == 1) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                camera.setParameters(parameters);
                camera.takePicture(sc, null, new TakePictureCallback());
            } else if (i == 2) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.takePicture(sc, null, new TakePictureCallback());
            }
        }
    }

    // 显示pop窗口
    public void showTipPopup() {
        layout = (RelativeLayout) LayoutInflater.from(CameraActivity.this)
                .inflate(R.layout.bubble_dialog, null);
        listView = (ListView) layout.findViewById(R.id.lv_dialog);
        listView.setAdapter(new ArrayAdapter<String>(CameraActivity.this,
                R.layout.bubble_text, R.id.tv_text, title));
        tipPopupWindow = new PopupWindow(CameraActivity.this);
        tipPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        tipPopupWindow.setWidth(80);
        tipPopupWindow.setHeight(250);
        tipPopupWindow.setOutsideTouchable(true);
        tipPopupWindow.setFocusable(true);
        tipPopupWindow.setContentView(layout);
        // showAsDropDown会把里面的view作为参照物，所以要那满屏幕parent
        tipPopupWindow.showAtLocation(findViewById(R.id.flashModeBtn),
                Gravity.LEFT | Gravity.TOP, 80, 30);
        // 需要指定Gravity，默认情况是center.
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                SharedPreferences sharedPreferences = getSharedPreferences(
                        "issetValueXml", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (arg2 == 0) {
                    editor.putInt("onoff", 0);
                    editor.apply();
                    flashModeBtn
                            .setBackgroundResource(R.drawable.flashmode_auto);
                    tipPopupWindow.dismiss();
                } else if (arg2 == 1) {
                    editor.putInt("onoff", 1);
                    editor.apply();
                    flashModeBtn
                            .setBackgroundResource(R.drawable.flashmode_open);
                    tipPopupWindow.dismiss();
                } else if (arg2 == 2) {
                    editor.putInt("onoff", 2);
                    editor.apply();
                    flashModeBtn
                            .setBackgroundResource(R.drawable.flashmode_off);
                    tipPopupWindow.dismiss();
                }
            }
        });
    }

    // 闪光灯事件
    class flashModeCameraOnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            showTipPopup();
        }
    }

    public int getRingMode() {
        return ringMode;
    }

    public void setRingMode(int ringMode) {
        this.ringMode = ringMode;
    }

    public static Bitmap getPrviewImageBitMap() {
        return prviewImageBitMap;
    }

    public static void setPrviewImageBitMap(Bitmap prviewImageBitMap) {
        CameraActivity.prviewImageBitMap = prviewImageBitMap;
    }

}