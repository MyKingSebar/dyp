package com.baidu.idl.face.example;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.idl.face.example.widget.DefaultDialog;
import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceDetectActivity;

import java.io.File;
import java.util.HashMap;

import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.Contact;
import v1.cn.unionc_pad.model.GetGuardianshipInfoData;
import v1.cn.unionc_pad.model.LogInEventData;
import v1.cn.unionc_pad.model.LogInFailEventData;
import v1.cn.unionc_pad.model.UpdateBaiduFileData;
import v1.cn.unionc_pad.model.UpdateFileData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.Main2;
import v1.cn.unionc_pad.ui.PrepareCallActivity;

public class FaceDetectExpActivity extends FaceDetectActivity {
Context context;
    private DefaultDialog mDefaultDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }

    @Override
    public void onDetectCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onDetectCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
//            showMessageDialog("人脸图像采集", "采集成功");
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
//            showMessageDialog("人脸图像采集", "采集超时");
        }
    }

    private void showMessageDialog(String title, String message) {
        if (mDefaultDialog == null) {
            DefaultDialog.Builder builder = new DefaultDialog.Builder(this);
            builder.setTitle(title).
                    setMessage(message).
                    setNegativeButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mDefaultDialog.dismiss();
                                    finish();
                                }
                            });
            mDefaultDialog = builder.create();
            mDefaultDialog.setCancelable(true);
        }
        mDefaultDialog.dismiss();
        mDefaultDialog.show();
    }

    @Override
    public void finish() {

        super.finish();
    }
    @Override
    public void getBaidu(File f) {

        ConnectHttp.connect(UnionAPIPackage.uploadbaiduImge(f),
                new BaseObserver<UpdateBaiduFileData>(this) {
                    @Override
                    public void onResponse(UpdateBaiduFileData data) {
                        if (TextUtils.equals("4000", data.getCode())) {
                            String identifier = data.getData().getIdentifier() + "";
                            SPUtil.put(context, Common.IDENTIFIER, identifier);
                            SPUtil.put(context, Common.USER_PHONE, data.getData());

                            SPUtil.put(context, Common.USER_TOKEN, (String) data.getData().getToken());
                            SPUtil.put(context, Common.RONG_TOKEN, (String) data.getData().getIMToken());
                            SPUtil.put(context, Common.USER_ID, (String) data.getData().getUserId());
                            LogInEventData eventData = new LogInEventData();
                            eventData.setHasGuardian(data.getData().getHasGuardian());
                            BusProvider.getInstance().post(eventData);
                            Log.d("linshi","message:"+data.getMessage());
//                            if (!TextUtils.isEmpty(data.getData().getIMUserId())) {
//                                //"HeadImage":"http://192.168.21.93:8080/unionWeb/image","GuardianUserName":"","IMUserId":"debb99a8209f4933b75db9b1e8119a17"
//                                Intent intent=new Intent(Main2.this,PrepareCallActivity.class);
//                                intent.putExtra("HeadImage",data.getData().getHeadImage());
//                                intent.putExtra("GuardianUserName",data.getData().getGuardianUserName());
//                                intent.putExtra("IMUserId",data.getData().getIMUserId());
//                                startActivity(intent);
////                            goNewActivity(PrepareCallActivity.class);
//                            }
finish();

                        } else {
                            Toast.makeText(context,data.getMessage(),Toast.LENGTH_LONG);
                            Log.d("linshi","message:"+data.getMessage());
                            LogInFailEventData eventData = new LogInFailEventData();
                            BusProvider.getInstance().post(eventData);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        LogInFailEventData eventData = new LogInFailEventData();
                        BusProvider.getInstance().post(eventData);
                        finish();
                    }
                });
    }
}
