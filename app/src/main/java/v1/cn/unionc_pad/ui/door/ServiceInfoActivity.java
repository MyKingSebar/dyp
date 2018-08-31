package v1.cn.unionc_pad.ui.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class ServiceInfoActivity extends BaseActivity {
    private visitnurseservicesData.DataData.ServiceData serdata;
    private String id;
    private String docid;
    private String pricenum;
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;


    @BindView(R.id.im_service)
    ImageView im_service;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.describe)
    TextView describe;
    @BindView(R.id.price)
    TextView price;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }
    @OnClick(R.id.toplayout)
    void toplayout(){
        finish();
    }
    @OnClick(R.id.next)
    void next() {
        Intent intent = new Intent(ServiceInfoActivity.this, NowYuyueActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("docid", docid);
        intent.putExtra("price", pricenum);
        startActivity(intent);
    }
    @OnClick(R.id.next2)
    void next2() {
        Intent intent = new Intent(ServiceInfoActivity.this, NowYuyueActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("docid", docid);
        intent.putExtra("price", pricenum);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_serviceinfo);
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
//        if(getIntent().hasExtra("data")){
//            serdata=(visitnurseservicesData.DataData.ServiceData)getIntent().getSerializableExtra("user");
//        }
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
            if (!TextUtils.isEmpty(id)) {
                initcollect(id);
            }

        }
        if (getIntent().hasExtra("docid")) {
            docid = getIntent().getStringExtra("docid");


        }
    }


    private void initView() {


    }


    private void initcollect(String id) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.visitnurseservices(token, id), new BaseObserver<visitnurseservicesData>(context) {
            @Override
            public void onResponse(visitnurseservicesData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(data));

                if (TextUtils.equals("4000", data.getCode())) {
                    if (data.getData().getData().size() != 0) {
                        visitnurseservicesData.DataData.ServiceData data2 = data.getData().getData().get(0);
                        if (TextUtils.isEmpty(data2.getServiceImage())) {
                            im_service.setImageResource(R.drawable.icon_push);
                        } else {
                            Glide.with(context)
                                    .load(data2.getServiceImage())
                                    .placeholder(R.drawable.icon_push).dontAnimate()
                                    .error(R.drawable.icon_push)
                                    .into(im_service);

                        }
                        title.setText(data2.getServiceName() + "");
                        name.setText(data2.getServiceName() + "");
                        price.setText("￥" + data2.getServicePrice() + "/次 ");
                        pricenum=data2.getServicePrice();
                        describe.setText(data2.getServiceDesc() + "");
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


}
