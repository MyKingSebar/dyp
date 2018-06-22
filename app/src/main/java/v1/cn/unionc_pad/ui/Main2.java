package v1.cn.unionc_pad.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.idl.face.example.Config;
import com.baidu.idl.face.example.ExampleApplication;
import com.baidu.idl.face.example.FaceDetectExpActivity;
import com.baidu.idl.face.example.FaceLivenessExpActivity;
import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.Rong.Test;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetGuardianshipInfoData;
import v1.cn.unionc_pad.model.GetRongTokenData;
import v1.cn.unionc_pad.model.IsBindJianhurenData;
import v1.cn.unionc_pad.model.LogInEventData;
import v1.cn.unionc_pad.model.LogInFailEventData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class Main2 extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_bind)
    TextView tv_bind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.pad_main);
        ButterKnife.bind(this);
//        initData();
        if (isLogin()) {

            tvLogin.setText("退出登录");
            tv_bind.setVisibility(View.VISIBLE);
        }
        initBaiDu();
        getAndroiodScreenProperty();
    }

    private void initBaiDu() {
        // 根据需求添加活体动作
        ExampleApplication.livenessList.clear();
        ExampleApplication.livenessList.add(LivenessTypeEnum.Eye);
        ExampleApplication.livenessList.add(LivenessTypeEnum.Mouth);
        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadUp);
        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadDown);
        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadLeft);
        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadRight);
        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadLeftOrRight);

        requestPermissions(99, Manifest.permission.CAMERA);

        initLib();
        setFaceConfig();
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.tv_login, R.id.tv_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:

                break;
            case R.id.bt2:

                break;
            case R.id.bt3:

                break;
            case R.id.bt4:

                break;
            case R.id.bt5:
//                getRongInfo();
                if (isLogin()) {
                    ishasguardian();
//                    goNewActivity(PrepareCallActivity.class);
                } else {
                    showTost("请先登陆");
                    goNewActivity(FaceDetectExpActivity.class);
                }
                break;
            case R.id.tv_login:
//                setFaceConfig();
//                goNewActivity(FaceDetectExpActivity.class);
//                if(TextUtils.equals(tvLogin.getText().toString(),"退出登录")){
//
//                    tvLogin.setText("登录");
//                    showTost("退出登录成功");
//                    tv_bind.setVisibility(View.INVISIBLE);
//                }else if(TextUtils.equals(tvLogin.getText().toString(),"登录")){
//
////goNewActivity(ChrisActivity.class);
//                    Intent intent=new Intent(Main2.this,ChrisActivity.class);
//                    startActivityForResult(intent,1);
//                }
                if (TextUtils.equals(tvLogin.getText().toString(), "退出登录")) {

                    tvLogin.setText("登录");
                    tv_bind.setVisibility(View.INVISIBLE);
                    logout();
                    showTost("退出登录成功");
                } else if (TextUtils.equals(tvLogin.getText().toString(), "登录")) {

//goNewActivity(ChrisActivity.class);
//                    Intent intent=new Intent(Main2.this,ChrisActivity.class);
//                    startActivityForResult(intent,1);

                    goNewActivity(FaceDetectExpActivity.class);
                }

                break;
            case R.id.tv_bind:
//                goNewActivity(BindActivity.class);
                twocode();
                break;
        }
    }

    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }

    private void getRongInfo() {

        ConnectHttp.connect(UnionAPIPackage.GetGuardianshipInfo(PadTest.居家老人token),
                new BaseObserver<GetGuardianshipInfoData>(context) {
                    @Override
                    public void onResponse(GetGuardianshipInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(data.getData().getIMUserId())) {
                                //"HeadImage":"http://192.168.21.93:8080/unionWeb/image","GuardianUserName":"","IMUserId":"debb99a8209f4933b75db9b1e8119a17"
                                Intent intent = new Intent(Main2.this, PrepareCallActivity.class);
                                intent.putExtra("HeadImage", data.getData().getHeadImage());
                                intent.putExtra("GuardianUserName", data.getData().getGuardianUserName());
                                intent.putExtra("IMUserId", data.getData().getIMUserId());
                                startActivity(intent);
//                            goNewActivity(PrepareCallActivity.class);
                            }


                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });


    }

    private void initData() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getRongToken(PadTest.居家老人token),
                new BaseObserver<GetRongTokenData>(context) {
                    @Override
                    public void onResponse(GetRongTokenData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(data.getData().getIMToken())) {
                                connect(data.getData().getIMToken());
                            }


                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });

    }
    private void ishasguardian() {
        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.ishasguardian(Token),
                new BaseObserver<IsBindJianhurenData>(context) {
                    @Override
                    public void onResponse(IsBindJianhurenData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if(TextUtils.equals(data.getData().getHasGuardian(),"1")){
                                goNewActivity(PrepareCallActivity.class);
                            }else{
                                showTost("请先绑定监护人");
                                goNewActivity(BindActivity.class);
                            }



                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });

    }
    private void twocode() {
        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.ishasguardian(Token),
                new BaseObserver<IsBindJianhurenData>(context) {
                    @Override
                    public void onResponse(IsBindJianhurenData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if(TextUtils.equals(data.getData().getHasGuardian(),"1")){
                                showTost("您已绑定监护人");
                            }else{
                                goNewActivity(BindActivity.class);
                            }



                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });

    }

    /**
     * 设置跳转  接受返回数据
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  如果请求码与返回码等于预期设置的值  则进行后续操作
        if (requestCode == 1) {
            // 获取返回的数据

            // 设置给页面的文本TextView显示
            tvLogin.setText("退出登录");
            tv_bind.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 初始化SDK
     */
    private void initLib() {
        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
//        setFaceConfig();
    }

    private void setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        config.setLivenessTypeList(ExampleApplication.livenessList);
        config.setLivenessRandom(ExampleApplication.isLivenessRandom);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setLivenessRandomCount(2);
        config.setFaceDecodeNumberOfThreads(2);

        FaceSDKManager.getInstance().setFaceConfig(config);
    }

    public void requestPermissions(int requestCode, String permission) {
        if (permission != null && permission.length() > 0) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    // 检查是否有权限
                    int hasPer = checkSelfPermission(permission);
                    if (hasPer != PackageManager.PERMISSION_GRANTED) {
                        // 是否应该显示权限请求
                        boolean isShould = shouldShowRequestPermissionRationale(permission);
                        requestPermissions(new String[]{permission}, requestCode);
                    }
                } else {

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        boolean flag = false;
        for (int i = 0; i < permissions.length; i++) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[i]) {
                flag = true;
            }
        }
        if (!flag) {
            requestPermissions(99, Manifest.permission.CAMERA);
        }
    }

    @Subscribe
    public void loginReturn(final LogInEventData data) {
        String Token = (String) SPUtil.get(context, Common.RONG_TOKEN, "");

        connect(Token);
        // 设置给页面的文本TextView显示
        tvLogin.setText("退出登录");
        tv_bind.setVisibility(View.VISIBLE);
        showTost("登陆成功");
        //直接创建，不需要设置setDataSource
        MediaPlayer mMediaPlayer;
        mMediaPlayer = MediaPlayer.create(this, R.raw.login);
        mMediaPlayer.start();
//        SoundPool soundPool=new  SoundPool(100, AudioManager.STREAM_MUSIC,0);//构建对象
//        int soundId=soundPool.load(context,R.raw.login,1);//加载资源，得到soundId
//        int streamId= soundPool.play(soundId, 1,1,1,0,1);//播放，得到StreamId
        if (!TextUtils.equals(data.getHasGuardian(), "1")) {
            goNewActivity(BindActivity.class);
        }

    }

    @Subscribe
    public void loginFailReturn(final LogInFailEventData data) {
        MediaPlayer mMediaPlayer;
        mMediaPlayer = MediaPlayer.create(this, R.raw.loginfail);
        mMediaPlayer.start();
//        SoundPool soundPool=new  SoundPool(100, AudioManager.STREAM_MUSIC,0);//构建对象
//        int soundId=soundPool.load(context,R.raw.loginfail,1);//加载资源，得到soundId
//        int streamId= soundPool.play(soundId, 1,1,1,0,1);//播放，得到StreamId
    }
}
