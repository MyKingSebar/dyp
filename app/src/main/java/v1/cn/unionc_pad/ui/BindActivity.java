package v1.cn.unionc_pad.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.IsBindJianhurenData;
import v1.cn.unionc_pad.model.MeguardianshipData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.utils.ZXingUtils;

public class BindActivity extends BaseActivity {
    Long starttime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_two)
    TextView tv_two;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_linshi2);
        ButterKnife.bind(this);
init();
        handler.sendEmptyMessageDelayed(4000, 1000);
        Date dt= new Date();
        starttime= dt.getTime();
    }

    private void init() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("亲情监护绑定");
        String userid=(String) SPUtil.get(context, Common.USER_ID, "");
        Bitmap bitmap = ZXingUtils.createQRImage("Pad二维码"+userid, 600, 600);
        BitmapDrawable bd=new BitmapDrawable(bitmap);
        tv_two.setBackgroundDrawable(bd);
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 4000) {
                handler.removeMessages(4000);
                Date dt= new Date();
                if((dt.getTime()-starttime)>60*10*1000){
                    finish();
                }else{
                    twocode();

                }


            }

        }
    };

    @Override
    protected void onDestroy() {
        handler.removeMessages(4000);
        super.onDestroy();
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
                                showTost("您已绑定监护人:"+data.getData().getGuardian().get(0).getGuardianName());
                                finish();
                            }else{
                                handler.sendEmptyMessageDelayed(4000, 1000);
                            }



                        } else {
                            showTost(data.getMessage() + "");
                            handler.sendEmptyMessageDelayed(4000, 1000);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                        handler.sendEmptyMessageDelayed(4000, 1000);
                    }
                });

    }
//    private void twocode2() {
//        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        ConnectHttp.connect(UnionAPIPackage.GetGuardianshipListInfo(Token),
//                new BaseObserver<MeguardianshipData>(context) {
//                    @Override
//                    public void onResponse(MeguardianshipData data) {
//                        closeDialog();
//                        if (TextUtils.equals("4000", data.getCode())) {
//                            if(TextUtils.equals(data.getData().getHasGuardian(),"1")){
//                                if(data.getData().getGuardian().size()>0){
//
//                                    showTost("您已绑定监护人："+data.getData().getGuardian().get(0).getGuardianName());
//                                }else{
//                                    showTost("您已绑定监护人");
//                                }
//                                finish();
//                            }else{
//                                handler.sendEmptyMessageDelayed(4000, 1000);
//                            }
//
//
//
//                        } else {
//                            showTost(data.getMessage() + "");
//                            handler.sendEmptyMessageDelayed(4000, 1000);
//                        }
//                    }
//
//                    @Override
//                    public void onFail(Throwable e) {
//                        closeDialog();
//                        handler.sendEmptyMessageDelayed(4000, 1000);
//                    }
//                });
//
//    }
}
