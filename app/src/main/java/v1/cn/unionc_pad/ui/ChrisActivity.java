package v1.cn.unionc_pad.ui;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class ChrisActivity extends BaseActivity implements SurfaceHolder.Callback{
    private static final String TAG = "ChrisAcvitity";
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private SurfaceView mView;
    @OnClick(R.id.img_back)
    void back(){
        finish();
    }
    @Override
    // 创建Activity时执行的动作
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview_main);
        ButterKnife.bind(this);
        mView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mView.getHolder();
        mHolder.addCallback(this);
        handler.sendEmptyMessageDelayed(0, 5000);

    }

    @Override
    // apk暂停时执行的动作：把相机关闭，避免占用导致其他应用无法使用相机
    protected void onPause() {
        super.onPause();

        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    // 恢复apk时执行的动作
    protected void onResume() {
        super.onResume();
        if (null!=mCamera){
            mCamera = getCameraInstance();
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch(IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }
    }


    // SurfaceHolder.Callback必须实现的方法
    public void surfaceCreated(SurfaceHolder holder){
        mCamera = getCameraInstance();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch(IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    // SurfaceHolder.Callback必须实现的方法
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        refreshCamera(); // 这一步是否多余？在以后复杂的使用场景下，此步骤是必须的。
        int rotation = getDisplayOrientation(); //获取当前窗口方向
        mCamera.setDisplayOrientation(rotation); //设定相机显示方向
    }

    // SurfaceHolder.Callback必须实现的方法
    public void surfaceDestroyed(SurfaceHolder holder){
        if(mHolder!=null){

            mHolder.removeCallback(this);
        }
        if(mCamera!=null){

            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    // === 以下是各种辅助函数 ===

    // 获取camera实例
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//这就是前置摄像头，亲。
                    c = Camera.open(i);
                }
            }
        } catch(Exception e){
            Log.d("TAG", "camera is not available");
        }
        if (c == null) {
c = Camera.open();}
        return c;

    }

    // 获取当前窗口管理器显示方向
    private int getDisplayOrientation(){
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation){
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        android.hardware.Camera.CameraInfo camInfo =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, camInfo);

        // 这里其实还是不太懂：为什么要获取camInfo的方向呢？相当于相机标定？？
        int result = (camInfo.orientation - degrees + 360) % 360;

        return result;
    }

    // 刷新相机
    private void refreshCamera(){
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch(Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {

        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                    handler.removeMessages(0);
                    // app的功能逻辑处理
                    // 再次发出msg，循环更新
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                    showTost("登陆成功");
                    break;
                default:
                    break;
            }
        };
    };

}
