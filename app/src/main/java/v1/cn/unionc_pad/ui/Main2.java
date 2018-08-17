package v1.cn.unionc_pad.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.FaceEnvironment;
import com.baidu.aip.FaceSDKManager;
import com.baidu.aip.fl.Config;
import com.baidu.aip.fl.DetectActivity;
import com.baidu.idl.facesdk.FaceTracker;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.neliveplayer.sdk.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.NESDKConfig;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.callkit.RongCallKit;
import v1.cn.demo.activity.NEMainActivity;
import v1.cn.demo.activity.NEVideoPlayerActivity;
import v1.cn.demo.receiver.NELivePlayerObserver;
import v1.cn.demo.receiver.Observer;
import v1.cn.demo.util.HttpPostUtils;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.DocOrNurseData;
import v1.cn.unionc_pad.model.GetGuardianshipInfoData;
import v1.cn.unionc_pad.model.GetRongTokenData;
import v1.cn.unionc_pad.model.HasUnfinishedAppointData;
import v1.cn.unionc_pad.model.IsBindJianhurenData;
import v1.cn.unionc_pad.model.JiGuangData;
import v1.cn.unionc_pad.model.LogInEventData;
import v1.cn.unionc_pad.model.LogInFailEventData;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.model.saveinterrogationrecordsData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.utils.MacUtil;
import v1.cn.unionc_pad.utils.jiguang.ExampleUtil;
import v1.cn.unionc_pad.utils.jiguang.LocalBroadcastManager;

//import com.baidu.idl.face.example.Config;
//import com.baidu.idl.face.example.ExampleApplication;
//import com.baidu.idl.face.example.FaceDetectExpActivity;
//import com.baidu.idl.face.example.FaceLivenessExpActivity;
//import com.baidu.idl.face.platform.FaceConfig;
//import com.baidu.idl.face.platform.FaceEnvironment;
//import com.baidu.idl.face.platform.FaceSDKManager;
//import com.baidu.idl.face.platform.LivenessTypeEnum;

public class Main2 extends BaseActivity {
    private String docId, nursrId;
    private String decodeType = "software";  //解码类型，默认软件解码
    private String mediaType = "livestream"; //媒体类型，默认网络直播
    Gson gson = new Gson();

    private NESDKConfig config;


    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_bind)
    TextView tv_bind;
    @BindView(R.id.bt2)
    ImageView bt2;

    @BindView(R.id.re_bt1)
    RelativeLayout re_bt1;
    @BindView(R.id.re_bt3)
    RelativeLayout re_bt3;
    @BindView(R.id.bt3_bt)
    RelativeLayout bt3_bt;
    @BindView(R.id.bt1_bt)
    RelativeLayout bt1_bt;
    @BindView(R.id.im_img)
    ImageView im_img;
    @BindView(R.id.im_img3)
    ImageView im_img3;
    @BindView(R.id.tv_call)
    TextView tv_call;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_name3)
    TextView tv_name3;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_add3)
    TextView tv_add3;
    @BindView(R.id.tv_live_name)
    TextView tv_live_name;
    @BindView(R.id.tv_live_time)
    TextView tv_live_time;
    @BindView(R.id.re_live_describe)
    RelativeLayout re_live_describe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.pad_main4);
        context = this;
        /**   6.0权限申请     **/
        bPermission = checkPublishPermission();

        ButterKnife.bind(this);
//        initData();
        if (isLogin()) {
            tvLogin.setText("退出登录");
            tv_bind.setVisibility(View.VISIBLE);
        }
        initView();
        initBaiDu();
        getAndroiodScreenProperty();
        initlive();
        initrecommenddoctor();
        registerMessageReceiver();

        String mac = MacUtil.getMac(this);
        Log.d("linshi", "mac:" + mac);
        if (isLogin()) {

            initDocOrNurse();
            initlivelist();
        }

        ViewTreeObserver viewTreeObserver = bt2.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                bt2.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                Log.d("linshi",
                        "---->" + bt2.getHeight() + "  " + bt2.getWidth());

            }
        });
    }

    private void initView() {
        bt1_bt.setVisibility(View.GONE);
        bt3_bt.setVisibility(View.GONE);

    }

    private void initlivelist() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        Log.d("linshi","token:"+token);
        ConnectHttp.connect(UnionAPIPackage.getlivelist(token, "1", "20"), new BaseObserver<NetCouldPullData>(context) {
            @Override
            public void onResponse(NetCouldPullData data) {
                if (TextUtils.equals("4000", data.getCode())) {


                    if (data.getData().getLives().size() > 0) {
                        tv_live_name.setText("" + data.getData().getLives().get(0).getLiveTitle());
                        tv_live_time.setText("" + data.getData().getLives().get(0).getStartTime());
                        re_live_describe.setVisibility(View.VISIBLE);
                        try {
                        Glide.with(context)
                                .load(data.getData().getLives().get(0).getBanner())
                                .placeholder(R.drawable.pad_main2).dontAnimate()
                                .error(R.drawable.pad_main2)
                                .into(bt2);
                        } catch (Exception mE) {
                            return;
                        }

                    } else {
                        re_live_describe.setVisibility(View.INVISIBLE);
                    }
                } else {
                    showTost(data.getMessage() + "");
                    re_live_describe.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFail(Throwable e) {
                showTost("暂无直播");
                re_live_describe.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initDocOrNurse() {

        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        Log.d("linshi","token:"+Token);
        ConnectHttp.connect(UnionAPIPackage.getdocornurse(Token),
                new BaseObserver<DocOrNurseData>(context) {
                    @Override
                    public void onResponse(DocOrNurseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (TextUtils.equals(data.getData().getHasDoctor(), "1")) {
                                if (null != context) {
                                    try {
                                        Glide.with(context)
                                                .load(data.getData().getDoctorMap().getDoctImagePath())
                                                .placeholder(R.drawable.default_avatar).dontAnimate()
                                                .error(R.drawable.default_avatar)
                                                .into(im_img);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return;
                                    }

                                }
                                tv_name.setText(data.getData().getDoctorMap().getDoctName());
                                tv_add.setText(data.getData().getDoctorMap().getClinicName());
                                bt1_bt.setVisibility(View.VISIBLE);
                                docId = data.getData().getDoctorMap().getDoctId();
                            } else {
                                bt1_bt.setVisibility(View.GONE);
                            }
                            if (TextUtils.equals(data.getData().getHasNurse(), "1")) {
                                if (null != context) {
                                    try {
                                        Glide.with(context)
                                                .load(data.getData().getNurseMap().getDoctImagePath())
                                                .placeholder(R.drawable.default_avatar).dontAnimate()
                                                .error(R.drawable.default_avatar)
                                                .into(im_img3);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return;
                                    }

                                }
                                tv_name3.setText(data.getData().getNurseMap().getDoctName());
                                tv_add3.setText(data.getData().getNurseMap().getClinicName());
                                bt3_bt.setVisibility(View.VISIBLE);
                                nursrId = data.getData().getNurseMap().getDoctId();
                            } else {
                                bt3_bt.setVisibility(View.GONE);
                            }


                        } else {
                            bt1_bt.setVisibility(View.GONE);
                            bt3_bt.setVisibility(View.GONE);
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        bt1_bt.setVisibility(View.GONE);
                        bt3_bt.setVisibility(View.GONE);
                        closeDialog();
                    }
                });

    }

    private void getDoc() {
        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getdocornurse(Token),
                new BaseObserver<DocOrNurseData>(context) {
                    @Override
                    public void onResponse(DocOrNurseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            //"isOpen":"0：未开通视频问诊1：已开通视频问诊"
                            if (TextUtils.equals(data.getData().getIsOpen(), "0")) {
                                showTost("医院暂未开通此服务！");
                                return;
                            }
                            if (TextUtils.equals(data.getData().getHasDoctor(), "1")) {

                                Glide.with(context)
                                        .load(data.getData().getDoctorMap().getDoctImagePath())
                                        .placeholder(R.drawable.default_avatar).dontAnimate()
                                        .error(R.drawable.default_avatar)
                                        .into(im_img);
                                tv_name.setText(data.getData().getDoctorMap().getDoctName());
                                tv_add.setText(data.getData().getDoctorMap().getClinicName());
                                bt1_bt.setVisibility(View.VISIBLE);
                                docId = data.getData().getDoctorMap().getDoctId();
                                if (TextUtils.equals(data.getData().getIsOnline(), "0")) {
                                    showTost("医生不在线！");
                                    return;
                                }
                                if (!TextUtils.isEmpty(data.getData().getDoctorMap().getIdentifier())) {
                                    Log.d("linshi", "RongCallKit");
//                                    RongCallKit.startSingleCall(Main2.this,"0392cd92ee004f73aaa091df8fe742a3", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                                    RongCallKit.startSingleCall(Main2.this, data.getData().getDoctorMap().getIdentifier(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//                                    saverecord(docId);
                                }
                            } else {
                                bt1_bt.setVisibility(View.GONE);
                            }

                        } else {
                            bt1_bt.setVisibility(View.GONE);
                            bt3_bt.setVisibility(View.GONE);
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        bt1_bt.setVisibility(View.GONE);
                        bt3_bt.setVisibility(View.GONE);
                        closeDialog();
                    }
                });

    }

    private void saverecord(String id) {

        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.saveinterrogation(Token, id),
                new BaseObserver<saveinterrogationrecordsData>(context) {
                    @Override
                    public void onResponse(saveinterrogationrecordsData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {

                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });

    }

    private void initBaiDu() {
        // 根据需求添加活体动作
//        ExampleApplication.livenessList.clear();
//        ExampleApplication.livenessList.add(LivenessTypeEnum.Eye);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.Mouth);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadUp);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadDown);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadLeft);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadRight);
//        ExampleApplication.livenessList.add(LivenessTypeEnum.HeadLeftOrRight);
        requestPermissions(99, Manifest.permission.CAMERA);
        initLib();
//        setFaceConfig();
    }

    @OnClick({R.id.re_bt1, R.id.bt2, R.id.re_bt3, R.id.bt4, R.id.bt5, R.id.tv_login, R.id.tv_bind})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.re_bt1:
                if (isLogin()) {
//                    RongCallKit.startSingleCall(Main2.this, "bfc37490f5b94604a4a78cfc7a34446b", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//                    getDoc();
                    goNewActivity(DoctorListActivity.class);
                } else {
                    showTost("请先登陆");
//                    goNewActivity(FaceDetectExpActivity.class);
                    goNewActivity(DetectActivity.class);
                }

                break;
            case R.id.bt2:
                //健康直播
//                goNewActivity(NEMainActivity.class);
                if (isLogin()) {
//                    initrecommenddoctor2();
                    goNewActivity(LiveListActivity.class);
                } else {
                    showTost("请先登陆");
//                    goNewActivity(FaceDetectExpActivity.class);
                    goNewActivity(DetectActivity.class);
                }
                break;
            case R.id.re_bt3:
                if (isLogin()) {
//                    unfinishedappoint();
                    //一户上门
                    goNewActivity(NurseAndWorkerActivity.class);

                } else {
                    showTost("请先登陆");
                    goNewActivity(DetectActivity.class);
//                    goNewActivity(FaceDetectExpActivity.class);
                }


                break;
            case R.id.bt4:
                if (isLogin()) {
                    goNewActivity(SuperviseActivity.class);
                } else {
                    showTost("请先登陆");
                    goNewActivity(DetectActivity.class);
//                    goNewActivity(FaceDetectExpActivity.class);
                }

                break;
            case R.id.bt5:
//                getRongInfo();
                if (isLogin()) {
                    ishasguardian();
//                    goNewActivity(PrepareCallActivity.class);
                } else {
                    showTost("请先登陆");
                    goNewActivity(DetectActivity.class);
//                    goNewActivity(FaceDetectExpActivity.class);
//                    goNewActivity(DetectActivity.class);
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

//                    goNewActivity(FaceDetectExpActivity.class);
                    goNewActivity(DetectActivity.class);
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
                            Log.d("linshi", "111");
                            if (TextUtils.equals(data.getData().getHasGuardian(), "1")) {
                                Log.d("linshi", "222");
                                goNewActivity(PrepareCallActivity.class);
                            } else {
                                Log.d("linshi", "333");
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
                            if (TextUtils.equals(data.getData().getHasGuardian(), "1")) {
                                showTost("您已绑定监护人");
                            } else {
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

    private void unfinishedappoint() {
        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.unfinishedappoint(Token),
                new BaseObserver<HasUnfinishedAppointData>(context) {
                    @Override
                    public void onResponse(HasUnfinishedAppointData data) {
                        Log.e("linshi", "HasUnfinishedAppointData" + gson.toJson(data));
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (TextUtils.equals(data.getData().getHasUnfinishedAppoint(), "1")) {
                                showTost("您已有订单");
                            } else {
                                Intent intent = new Intent(Main2.this, WebViewActivity.class);
                                intent.putExtra("type", 1);
                                intent.putExtra("nurseId", nursrId);
                                Log.d("linshi", "main.nurseId:" + nursrId);
                                startActivity(intent);
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
//    private void initLib() {
//        // 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
//        // 应用上下文
//        // 申请License取得的APPID
//        // assets目录下License文件名
////        FaceSDKManager.getInstance().initialize(this, Config.licenseID, Config.licenseFileName);
////        setFaceConfig();
//    }

//    private void setFaceConfig() {
////        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
////        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
////        config.setLivenessTypeList(ExampleApplication.livenessList);
////        config.setLivenessRandom(ExampleApplication.isLivenessRandom);
////        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
////        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
////        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
////        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
////        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
////        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
////        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
////        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
////        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
////        config.setCheckFaceQuality(true);
////        config.setLivenessRandomCount(2);
////        config.setFaceDecodeNumberOfThreads(2);
////
////        FaceSDKManager.getInstance().setFaceConfig(config);
//    }
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

        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
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
        initDocOrNurse();
        initlivelist();
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


    /**
     * 直播相关*******************************************************************************************************************************************************
     */
    void initlive() {
        config = new NESDKConfig();
        //动态加载
        config.dynamicLoadingConfig = new NEDynamicLoadingConfig();
        //是否开启动态加载功能，默认关闭
//		config.dynamicLoadingConfig.isDynamicLoading = true;
        config.dynamicLoadingConfig.isArmeabi = true;
        config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;
        config.supportDecodeListener = mOnSupportDecodeListener;
        //SDK将内部的网络请求以回调的方式开给上层，如果需要上层自己进行网络请求请实现config.dataUploadListener，如果上层不需要自己进行网络请求而是让SDK进行网络请求，这里就不需要操作config.dataUploadListener
        config.dataUploadListener = mOnDataUploadListener;
        NELivePlayer.init(this, config);
        NELivePlayerObserver.getInstance().observeNELivePlayerObserver(observer, true);
    }

    /**
     * 6.0权限处理
     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    private Observer<Void> observer = new Observer<Void>() {
        @Override
        public void onEvent(Void aVoid) {
            //接收到播放器资源释放结束通知
            Log.i("linshi", "onEvent -> NELivePlayer RELEASE SUCCESS!");
        }
    };

    private NELivePlayer.OnDynamicLoadingListener mOnDynamicLoadingListener = new NELivePlayer.OnDynamicLoadingListener() {
        @Override
        public void onDynamicLoading(NEDynamicLoadingConfig.ArchitectureType type, boolean isCompleted) {
            Log.d("linshi", "type:" + type + "，isCompleted:" + isCompleted);
        }
    };

    private NELivePlayer.OnSupportDecodeListener mOnSupportDecodeListener = new NELivePlayer.OnSupportDecodeListener() {
        @Override
        public void onSupportDecode(boolean isSupport) {
            Log.d("linshi", "是否支持H265硬件解码 onSupportDecode isSupport:" + isSupport);
            //如果支持H265硬件解码，那么可以使用H265的视频源进行播放
        }
    };


    private NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {
        @Override
        public boolean onDataUpload(String url, String data) {
            Log.d("linshi", "onDataUpload url:" + url + ", data:" + data);
            sendData(url, data);
            return true;
        }

        @Override
        public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
            Log.d("linshi", "onDataUpload url:" + url + ", params:" + params + ",filepaths:" + filepaths);
            return (new HttpPostUtils(url, params, filepaths).connPost());
        }
    };

    private boolean sendData(final String urlStr, final String content) {
        int response = 0;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());

            response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.i("linshi", " sendData finished,data:" + content);

            } else {
                Log.i("linshi", " sendData, response: " + response);

            }
        } catch (IOException e) {
            Log.e("linshi", "sendData, recv code is error: " + e.getMessage());

        } catch (Exception e) {
            Log.e("linshi", "sendData, recv code is error2: " + e.getMessage());

        }
        return (response == HttpURLConnection.HTTP_OK);
    }

    private void initrecommenddoctor() {
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        ConnectHttp.connect(UnionAPIPackage.getlivelist(token,"1","20"), new BaseObserver<NetCouldPullData>(context) {
//            @Override
//            public void onResponse(NetCouldPullData data) {
//                if(data.getData().getLives().size()>0){
//                    Log.d("linshi","doctorsdata:"+data.getData().getLives().get(0).toString());
//                    findDutyDoctorListAdapter.setData(data.getData().getLives());
//                }else{
//                    mainRecycleview.setVisibility(View.GONE);
//                }
//
//            }
//            @Override
//            public void onFail(Throwable e) {
//                mainRecycleview.setVisibility(View.GONE);
//            }
//        });
    }

    private void initrecommenddoctor2() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.getlivelist(token, "1", "20"), new BaseObserver<NetCouldPullData>(context) {
            @Override
            public void onResponse(NetCouldPullData data) {
                if (data.getData().getLives().size() > 0) {
                    Log.d("linshi", "doctorsdata:" + data.getData().getLives().get(0).toString());
                    Intent intent = new Intent(Main2.this, NEVideoPlayerActivity.class);


                    Log.d("linshi0", "url:" + data.getData().getLives().get(0).getHttpPullUrl());

                    if (!bPermission) {
                        Toast.makeText(getApplication(), "请先允许app所需要的权限", Toast.LENGTH_LONG).show();
                        bPermission = checkPublishPermission();
                        return;
                    }
                    if (config != null && config.dynamicLoadingConfig != null && config.dynamicLoadingConfig.isDynamicLoading && !NELivePlayer.isDynamicLoadReady()) {
                        Toast.makeText(getApplication(), "请等待加载so文件", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // //直播状态：1-未开始，2-直播中，3-已结束,4-已删除
                    if (TextUtils.equals(data.getData().getLives().get(0).getLiveStatus(), "1")) {
                        showTost("直播未开始");
                        return;
                    } else if (TextUtils.equals(data.getData().getLives().get(0).getLiveStatus(), "2")) {
                        //把多个参数传给NEVideoPlayerActivity
                        intent.putExtra("media_type", mediaType);
                        intent.putExtra("decode_type", decodeType);
                        intent.putExtra("videoPath", data.getData().getLives().get(0).getHttpPullUrl());
                        startActivity(intent);
                    } else if (TextUtils.equals(data.getData().getLives().get(0).getLiveStatus(), "3")) {
                        showTost("直播已结束");
                        return;
                    } else if (TextUtils.equals(data.getData().getLives().get(0).getLiveStatus(), "4")) {
                        showTost("直播已删除");
                        return;
                    }

                } else {
                    showTost("暂无直播");
                }

            }

            @Override
            public void onFail(Throwable e) {
                showTost("暂无直播");
            }
        });
    }

    /**
     * 推送****************************************************************************************************************************************
     */
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Common.MESSAGE_JGPUSH_PAD_ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, filter);
    }


    public class MessageReceiver extends BroadcastReceiver {
        //推送分类：1-活动，2-直播，3-医生，4-护士
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("linshi", "action():" + intent.getAction());
            if (Common.MESSAGE_JGPUSH_PAD_ACTION.equals(intent.getAction())) {
                if (!isLogin()) {
                    return;
                }
                String extras = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);
                StringBuilder showMsg = new StringBuilder();
                if (!ExampleUtil.isEmpty(extras)) {
                    Log.d("linshi", "extras():" + extras);
                    JiGuangData child2 = gson.fromJson(extras, JiGuangData.class);
                    if (TextUtils.equals("2", child2.getPushCategory())) {
                        Glide.with(context)
                                .load(child2.getBanner())
                                .placeholder(R.drawable.pad_main2).dontAnimate()
                                .error(R.drawable.pad_main2)
                                .into(bt2);
                        tv_live_name.setText("" + child2.getLiveTitle());
                        tv_live_time.setText("" + child2.getStartTime());
                        re_live_describe.setVisibility(View.VISIBLE);

                    } else if (TextUtils.equals("3", child2.getPushCategory())) {
                        Glide.with(context)
                                .load(child2.getDoctImagePath())
                                .placeholder(R.drawable.default_avatar).dontAnimate()
                                .error(R.drawable.default_avatar)
                                .into(im_img);
                        tv_name.setText(child2.getDoctName());
                        tv_add.setText(child2.getClinicName());
                        bt1_bt.setVisibility(View.VISIBLE);
                        docId = child2.getDoctId();
                    } else if (TextUtils.equals("4", child2.getPushCategory())) {
                        Glide.with(context)
                                .load(child2.getDoctImagePath())
                                .placeholder(R.drawable.default_avatar).dontAnimate()
                                .error(R.drawable.default_avatar)
                                .into(im_img3);
                        tv_name3.setText(child2.getDoctName());
                        tv_add3.setText(child2.getClinicName());

                        bt3_bt.setVisibility(View.VISIBLE);
                        nursrId = child2.getDoctId();
                    }


                }
            }

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

}
