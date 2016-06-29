package com.beibaoke;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.View.OnClickListener;
        import android.widget.ImageButton;
        import android.widget.ImageView;

public class PreviewImageView extends CameraActivity {
    private ImageView imageView;
    private ImageButton imageViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.preview_image);
        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        imageView = (ImageView) findViewById(R.id.previewImage);
        imageViewBtn = (ImageButton) findViewById(R.id.submitCameraBtn);
        imageViewBtn.setOnClickListener(new subMitCameraOnClick());
        imageView.setImageBitmap(CameraActivity.getPrviewImageBitMap());
    }

    // 提交照片事件(提供给以后的集成)
    class subMitCameraOnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            finish();

        }
    }
}