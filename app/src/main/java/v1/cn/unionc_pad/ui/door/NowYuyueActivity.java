package v1.cn.unionc_pad.ui.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.Main;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class NowYuyueActivity extends BaseActivity {
    private String id;
    private String docid;
    private String addid;
    private String price;
    private String DiseaseId;
    private String time;
    private Intent intent;
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;


    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_bing)
    TextView tv_bing;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_des)
    EditText tv_des;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_yuyue);
        unbinder = ButterKnife.bind(this);
        BusProvider.getInstance().register(this);
        initData();
        initView();
    }

    private void initData() {
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");

        }
        if (getIntent().hasExtra("docid")) {
            docid = getIntent().getStringExtra("docid");


        }
        if (getIntent().hasExtra("price")) {
            price = getIntent().getStringExtra("price");


        }
    }


    private void initView() {
        title.setText("立即预约");
        tv_des.clearFocus();
        initcollect();


    }
    @Subscribe
    public void subscribeEvent(YuYueBingData data) {
        Log.d("linshi", "getId"+data.getId());
        Log.d("linshi", "getName"+data.getName());
        DiseaseId=data.getId();
        tv_bing.setText(data.getName());
    }
    @Subscribe
    public void subscribeEvent(YuYueTimeData data) {
        Log.d("linshi", "getAlltime"+data.getAlltime());
        time=data.getAlltime();
        tv_time.setText(data.getAlltime());
    }


    @OnClick({R.id.ll_time, R.id.ll_bing, R.id.bt_yy})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_time:
                goNewActivity(YuyueTimeActivity.class);
                break;
            case R.id.ll_bing:
                goNewActivity(YuyueBingActivity.class);
                break;
            case R.id.bt_yy:
                if (TextUtils.isEmpty(tv_time.getText().toString())||TextUtils.equals(tv_time.getText().toString(),"选择服务时间")) {
                    showTost("请选择时间");
                    return;
                }
                if (TextUtils.isEmpty(tv_bing.getText().toString())||TextUtils.equals(tv_bing.getText().toString(),"疾病选择")) {
                    showTost("请选择疾病");
                    return;
                }
                subscribenurses();
                break;

        }
    }

    private void initcollect() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.getaddress(token, "1"), new BaseObserver<getAddressData>(context) {
            @Override
            public void onResponse(getAddressData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(data));

                if (TextUtils.equals("4000", data.getCode())) {
                    if (data.getData().getAddrList().size() != 0) {
                        getAddressData.DataData.DataDataData data2 = data.getData().getAddrList().get(0);
                        tv_add.setText(data2.getAddr() + "");
                        tv_name.setText(data2.getName() + "");
                        addid = data2.getAddrId();

                    }
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    private void subscribenurses() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        String la = (String) SPUtil.get(context, Common.LATITUDE, "");
        String lo = (String) SPUtil.get(context, Common.LONGITUDE, "");
        Log.d("linshi","la2:"+la + "," + lo);
        ConnectHttp.connect(UnionAPIPackage.subscribenurses(token, docid, tv_time.getText().toString(), "1", id,tv_name.getText().toString(), addid, DiseaseId,tv_des.getText().toString(),price,lo,la), new BaseObserver<YuYueOkData>(context) {
            @Override
            public void onResponse(YuYueOkData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(data));

                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("预约成功！");
//                    goNewActivity(Main.class);
                    Intent intent = new Intent();
                    intent.setClass(NowYuyueActivity.this, YuyueOkActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data.getData());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }
}
