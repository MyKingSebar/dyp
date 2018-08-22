package v1.cn.unionc_pad.ui.door;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.YuYueBingData;
import v1.cn.unionc_pad.model.YuYueOkData;
import v1.cn.unionc_pad.model.YuYueTimeData;
import v1.cn.unionc_pad.model.getAddressData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.Main;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class YuyueOkActivity extends BaseActivity {
    private Unbinder unbinder;
private YuYueOkData.DataData yuyuedata;
    int calltime=5;


    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.xiangmu)
    TextView xiangmu;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.tv_gotime)
    TextView tv_gotime;

    @OnClick(R.id.img_back)
    void back() {
        goNewActivity(Main.class);
    }
    @OnClick(R.id.bt_yy)
    void back2() {
        goNewActivity(Main.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_yuyue_ok);
        unbinder = ButterKnife.bind(this);
        BusProvider.getInstance().register(this);
        initData();
        initView();
    }

    private void initData() {
        if(getIntent().hasExtra("data")){
            yuyuedata=(YuYueOkData.DataData)getIntent().getSerializableExtra("data");
        }
    }


    private void initView() {
        tv_gotime.setText("" + calltime);
        if (yuyuedata != null) {
            num.setText(yuyuedata.getOrderId() + "");
            name.setText(yuyuedata.getServiceUserName() + "");
            xiangmu.setText(yuyuedata.getServiceName() + "");
            time.setText(yuyuedata.getServiceTime() + "");
            price.setText(yuyuedata.getPrice() + "元");
        }
        handler.sendEmptyMessageDelayed(5000, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            ArcProgress progressBar = (ArcProgress) msg.obj;
//            progressBar.setProgress(msg.what);
            if (msg.what == 5000) {
                handler.removeMessages(5000);

                if (calltime>0) {
                    calltime--;
                    tv_gotime.setText(""+calltime);
                    handler.sendEmptyMessageDelayed(5000, 1000);
                }else{
                    goNewActivity(Main.class);
                }
            }

        }
    };
//    private void initcollect() {
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        if (TextUtils.isEmpty(token)) {
//            return;
//        }
//        ConnectHttp.connect(UnionAPIPackage.getaddress(token, "1"), new BaseObserver<getAddressData>(context) {
//            @Override
//            public void onResponse(getAddressData data) {
//                Log.d("linshi", "datas:" + new Gson().toJson(data));
//
//                if (TextUtils.equals("4000", data.getCode())) {
//                    if (data.getData().getAddrList().size() != 0) {
//                        getAddressData.DataData.DataDataData data2 = data.getData().getAddrList().get(0);
//                        tv_add.setText(data2.getAddr() + "");
//                        tv_name.setText(data2.getName() + "");
//                        addid = data2.getAddrId();
//
//                    }
//                } else {
//                    showTost(data.getMessage());
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                closeDialog();
//            }
//        });
//    }

//    private void subscribenurses() {
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        if (TextUtils.isEmpty(token)) {
//            return;
//        }
//        ConnectHttp.connect(UnionAPIPackage.subscribenurses(token, docid, tv_time.getText().toString(), "1", id,tv_name.getText().toString(), addid, DiseaseId,tv_des.getText().toString()), new BaseObserver<BaseData>(context) {
//            @Override
//            public void onResponse(BaseData data) {
//                Log.d("linshi", "datas:" + new Gson().toJson(data));
//
//                if (TextUtils.equals("4000", data.getCode())) {
//                    showTost("预约成功！");
//                    goNewActivity(Main.class);
//                } else {
//                    showTost(data.getMessage());
//                }
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//                closeDialog();
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        calltime=-1;
        handler.removeMessages(5000);
    }
}
