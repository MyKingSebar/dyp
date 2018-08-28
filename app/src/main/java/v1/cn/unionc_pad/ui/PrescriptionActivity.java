package v1.cn.unionc_pad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.PrescriptionAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.PrescriptionInfoData;
import v1.cn.unionc_pad.model.videohasprescriptionData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class PrescriptionActivity extends BaseActivity {
private String recordId;
private PrescriptionAdapter prescriptionAdapter;
    List<PrescriptionInfoData.DataData.PrescriptionData.MedicinesData> datas=new ArrayList<>();
    private Unbinder unbinder;


    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.tv_clinicname)
    TextView tv_clinicname;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_keshi)
    TextView tv_keshi;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_zhenduan)
    TextView tv_zhenduan;
    @BindView(R.id.tv_allmoney)
    TextView tv_allmoney;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_qianming)
    TextView tv_qianming;
    @BindView(R.id.tv_buchong)
    TextView tv_buchong;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @OnClick(R.id.img_back)
     void back(){
        goNewActivity(CommentDocActivity.class);
        finish();
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("linshi","PrescriptionActivity:");
        setContentView(R.layout.activity_bingli_info);
        unbinder= ButterKnife.bind(this);
        initData();
        initView();
        initprescription();
    }

    private void initData() {
        Intent intent=getIntent();
        if(intent.hasExtra("prescriptionId")){
            recordId=intent.getStringExtra("recordId");
            Log.d("recordId","recordId:"+recordId);
        }
        if(TextUtils.isEmpty(Common.docvideoid)){
            Log.d("linshi", "on isEmpty");
//            showTost("on isEmpty");
            finish();
        }else{
            recordId=Common.docvideoid;
            Log.d("linshi", "recordIdd"+recordId);
//            showTost("recordIdd"+recordId);
        }
//        recordId="680";
    }

    @Override
    public void onDestroy() {
        Log.d("linshi", "on destroy");
        super.onDestroy();
        unbinder.unbind();
    }



    private void initprescription() {
//        showTost("initprescription"+recordId);
        Log.e("linshi","recordId:"+recordId);
        final String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        if(TextUtils.isEmpty(recordId)){
            finish();
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.videohasprescription(token,recordId), new BaseObserver<videohasprescriptionData>(context) {
            @Override
            public void onResponse(videohasprescriptionData data) {
                Log.e("linshi","data1:"+data.getCode());
                if (TextUtils.equals("4000", data.getCode())) {
                   if(TextUtils.equals(data.getData().getHasPrescription(),"1")){
                       prescriptioninfo(token,recordId);
                   }else {
                       goNewActivity(CommentDocActivity.class);
                       finish();
                   }
                }else{
//                    showTost(data.getMessage());
                    goNewActivity(CommentDocActivity.class);
                    finish();


                }

            }
            @Override
            public void onFail(Throwable e) {
            }
        });

    }
private void prescriptioninfo(String token,String recordId){
    ConnectHttp.connect(UnionAPIPackage.prescriptioninfo(token,recordId), new BaseObserver<PrescriptionInfoData>(context) {
        @Override
        public void onResponse(PrescriptionInfoData data) {
            Log.e("linshi","data:"+data.getCode());
            if (TextUtils.equals("4000", data.getCode())) {
//                if(TextUtils.isEmpty(data.getData().getPrescription().getUserName())){
//                    finish();
//                    return;
//                }
                datas.clear();
                tv_clinicname.setText(data.getData().getPrescription().getClinicName());
                tv_num.setText("NO.1702141212");
                tv_keshi.setText(data.getData().getPrescription().getDepartName());
                tv_name.setText(data.getData().getPrescription().getUserName());
                tv_sex.setText(data.getData().getPrescription().getGender());
                tv_age.setText(data.getData().getPrescription().getAge());
                tv_zhenduan.setText(data.getData().getPrescription().getClinicalDiagnosis());
                tv_allmoney.setText(data.getData().getPrescription().getCharge());
                tv_date.setText(data.getData().getPrescription().getCreatedTime());
                tv_qianming.setText(data.getData().getPrescription().getDoctName());
                tv_buchong.setText(data.getData().getPrescription().getSupplement());
                datas=data.getData().getPrescription().getMedicines();
                prescriptionAdapter.setData(datas);
//                    String src="医生姓名："+data.getData().getPrescription().getDoctName()+"\n补充说明:"+data.getData().getPrescription().getSupplement()+"\n临床诊断:"+data.getData().getPrescription().getClinicalDiagnosis()
//                            +"\n医院:"+data.getData().getPrescription().getClinicName()+"\n价格:"+data.getData().getPrescription().getCharge();
////                    tv.setText(new Gson().toJson(data.getData()));
//                    tv.setText(src);
            }

        }
        @Override
        public void onFail(Throwable e) {
        }
    });
}

    private void initView() {
        title.setText("");
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        prescriptionAdapter = new PrescriptionAdapter(context);
        recycleView.setAdapter(prescriptionAdapter);
    }


    @Override
    public void onPause() {
        Log.d("linshi", "on pause");
        super.onPause();
    }



    @Override
    public void onRestart() {
        Log.d("linshi", "on restart");
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.d("linshi", "on resmue");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d("linshi", "on start");
        super.onStart();
    }


}
