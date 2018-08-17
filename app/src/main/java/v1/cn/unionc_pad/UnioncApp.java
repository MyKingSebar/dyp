package v1.cn.unionc_pad;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baidu.aip.FaceEnvironment;
import com.baidu.aip.FaceSDKManager;
import com.baidu.aip.fl.Config;
import com.baidu.idl.facesdk.FaceTracker;
import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import v1.cn.unionc_pad.ui.health.LogUtils;
import v1.cn.unionc_pad.utils.Density;

public class UnioncApp extends MultiDexApplication {
    //百度人脸识别
//    public static List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
    public static boolean isLivenessRandom = false;

    public DisplayMetrics displayMetrics;

    private static UnioncApp app;

    public static UnioncApp getInstance() {
        if (app == null)
            throw new NullPointerException("app not create or be terminated!");
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        Density.setDensity(this, 640f);
        app = this;
        displayMetrics = getResources().getDisplayMetrics();

//蓝牙心电记录仪SDK的初始化
        initmHelath365SDK();
        initRongYun();
        initJiGuang();
//        initLib();
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics .widthPixels ;
    }

    private void initJiGuang() {
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
    private void initRongYun() {
        //        RongIM.init(this);
        RongIM.init(this,"3argexb63mx4e");
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                return false;
            }
        });
    }


    /**
     * 初始化SDK
     */
    private void initLib() {
        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
        // 应用上下文
        // 申请License取得的APPID
        // assets目录下License文件名
        FaceSDKManager.getInstance().init(this, Config.licenseID, Config.licenseFileName);
        setFaceConfig();
    }

    private void setFaceConfig() {
        FaceTracker tracker = FaceSDKManager.getInstance().getFaceTracker(this);
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整

        // 模糊度范围 (0-1) 推荐小于0.7
        tracker.set_blur_thr(FaceEnvironment.VALUE_BLURNESS);
        // 光照范围 (0-1) 推荐大于40
        tracker.set_illum_thr(FaceEnvironment.VALUE_BRIGHTNESS);
        // 裁剪人脸大小
        tracker.set_cropFaceSize(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        // 人脸yaw,pitch,row 角度，范围（-45，45），推荐-15-15
        tracker.set_eulur_angle_thr(FaceEnvironment.VALUE_HEAD_PITCH, FaceEnvironment.VALUE_HEAD_ROLL,
                FaceEnvironment.VALUE_HEAD_YAW);

        // 最小检测人脸（在图片人脸能够被检测到最小值）80-200， 越小越耗性能，推荐120-200
        tracker.set_min_face_size(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        //
        tracker.set_notFace_thr(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 人脸遮挡范围 （0-1） 推荐小于0.5
        tracker.set_occlu_thr(FaceEnvironment.VALUE_OCCLUSION);
        // 是否进行质量检测
        tracker.set_isCheckQuality(true);
        // 是否进行活体校验
        tracker.set_isVerifyLive(false);
    }

    //蓝牙心电记录仪SDK的初始化
    //************************************************************************************心电图***********************************************************************
    EcgOpenApiCallback.OsdkCallback displayMessage;
    public void setOsdkCallback(EcgOpenApiCallback.OsdkCallback osdkCallback) {
        displayMessage = osdkCallback;
    }
    EcgOpenApiCallback.OsdkCallback mOsdkCallback = new EcgOpenApiCallback.OsdkCallback() {

        @Override
        public void deviceSocketLost() {
            if (displayMessage != null)
                displayMessage.deviceSocketLost();
        }
        @Override
        public void deviceSocketConnect() {
            if (displayMessage != null)
                displayMessage.deviceSocketConnect();
        }
        @Override
        public void devicePlugOut() {
            if (displayMessage != null)
                displayMessage.devicePlugOut();
        }
        @Override
        public void devicePlugIn() {
            if (displayMessage != null)
                displayMessage.devicePlugIn();
        }
        @Override
        public void deviceReady(int sample) {
            if (displayMessage != null)
                displayMessage.deviceReady(sample);
        }
        @Override
        public void deviceNotReady(int msg) {
            if (displayMessage != null)
                displayMessage.deviceNotReady(msg);
        }
    };
    private void initmHelath365SDK() {
        EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
        String thirdPartyId = "9002e85acfc29e3687c849a88e46c40b";
        String appId = "d9fe9670dc955d53cab8c3e9295e1240";
        String appSecret = "";
        String UserOrgName = "壹家医养";
        String pakageName = "v1.cn.unionc_pad";
        LogUtils.LOGD("心电thirdPartyId",thirdPartyId);
        LogUtils.LOGD("心电appId",appId);
        LogUtils.LOGD("心电UserOrgName",UserOrgName);
        LogUtils.LOGD("心电pakageName",pakageName);
        try {
            mHelper.initOsdk(getApplicationContext(), thirdPartyId, appId, appSecret, UserOrgName, mOsdkCallback, pakageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void finishSdk() throws IOException {
        EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
        mHelper.finishSdk();
    }
    //蓝牙心电记录仪SDK的初始化完毕
    //************************************************************************************心电图完毕***********************************************************************

    public static Context getContext() {
        return getInstance().getBaseContext();
    }
}
