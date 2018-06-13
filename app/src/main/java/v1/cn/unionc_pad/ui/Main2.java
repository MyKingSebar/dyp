package v1.cn.unionc_pad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.Rong.Test;
import v1.cn.unionc_pad.model.GetGuardianshipInfoData;
import v1.cn.unionc_pad.model.GetRongTokenData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class Main2 extends BaseActivity {
    @BindView(R.id.tv_login)
    TextView tvLogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pad_main);
        ButterKnife.bind(this);
        initData();
        getAndroiodScreenProperty();
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
                getRongInfo();

                break;
            case R.id.tv_login:
                if(TextUtils.equals(tvLogin.getText().toString(),"退出登录")){

                    tvLogin.setText("登录");
                    showTost("退出登录成功");
                }else if(TextUtils.equals(tvLogin.getText().toString(),"登录")){

//goNewActivity(ChrisActivity.class);
                    Intent intent=new Intent(Main2.this,ChrisActivity.class);
                    startActivityForResult(intent,1);
                }

                break;
            case R.id.tv_bind:
goNewActivity(BindActivity.class);
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
private void getRongInfo(){

    ConnectHttp.connect(UnionAPIPackage.GetGuardianshipInfo(PadTest.居家老人token),
            new BaseObserver<GetGuardianshipInfoData>(context) {
                @Override
                public void onResponse(GetGuardianshipInfoData data) {
                    closeDialog();
                    if (TextUtils.equals("4000", data.getCode())) {
                        if (!TextUtils.isEmpty(data.getData().getIMUserId())) {
                            //"HeadImage":"http://192.168.21.93:8080/unionWeb/image","GuardianUserName":"","IMUserId":"debb99a8209f4933b75db9b1e8119a17"
                            Intent intent=new Intent(Main2.this,PrepareCallActivity.class);
                            intent.putExtra("HeadImage",data.getData().getHeadImage());
                            intent.putExtra("GuardianUserName",data.getData().getGuardianUserName());
                            intent.putExtra("IMUserId",data.getData().getIMUserId());
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

    /**
     * 设置跳转  接受返回数据
     * @param requestCode
     *              请求码
     * @param resultCode
     *              返回码
     * @param data
     *              返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  如果请求码与返回码等于预期设置的值  则进行后续操作
        if (requestCode == 1 ){
            // 获取返回的数据

            // 设置给页面的文本TextView显示
            tvLogin.setText("退出登录");
        }
    }
}
