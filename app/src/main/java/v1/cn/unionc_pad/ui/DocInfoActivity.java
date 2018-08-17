package v1.cn.unionc_pad.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.DrmInitData;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netease.neliveplayer.sdk.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.NESDKConfig;

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
import butterknife.Unbinder;
import io.rong.callkit.RongCallKit;
import v1.cn.demo.activity.NEVideoPlayerActivity;
import v1.cn.demo.receiver.NELivePlayerObserver;
import v1.cn.demo.receiver.Observer;
import v1.cn.demo.util.HttpPostUtils;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.LiveListAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetLiveDoctorListData;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.model.saveinterrogationrecordsData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class DocInfoActivity extends BaseActivity {
    private GetLiveDoctorListData.DataData.DataDataData doctorData;
    private Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView title;



    @BindView(R.id.img_message_avator)
    ImageView imgMessageAvator;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_role)
    TextView tv_role;
    @BindView(R.id.tv_identity)
    TextView tv_identity;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_like)
    TextView tv_like;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.tv_allow)
    TextView tv_allow;
    @BindView(R.id.tv_isonline)
    TextView tv_isonline;
    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_price2)
    TextView tv_price2;
    @BindView(R.id.tv_skilled)
    TextView tv_skilled;
    @BindView(R.id.tv_intro)
    TextView tv_intro;





    @OnClick(R.id.toplayout)
    void back() {
        finish();
    }
    @OnClick(R.id.call)
    void call() {
        if(doctorData!=null){

            if(TextUtils.equals(doctorData.getOnlineState(),"2")){
                saverecord(doctorData.getDoctId());

            }else  if(TextUtils.equals(doctorData.getOnlineState(),"0")){

                Toast.makeText(context,"医生不在线",Toast.LENGTH_SHORT).show();
            }else  if(TextUtils.equals(doctorData.getOnlineState(),"1")){
                Toast.makeText(context,"医生忙碌",Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.d("linshi","doctorData=null");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.docvideoid="";
    }

    private void saverecord(String id) {

        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.saveinterrogation(Token, id),
                new BaseObserver<saveinterrogationrecordsData>(context) {
                    @Override
                    public void onResponse(saveinterrogationrecordsData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            Common.docvideoid=data.getData().getRecordId();
                            if(!TextUtils.isEmpty(Common.docvideoid)){

                                RongCallKit.startSingleCall(context, doctorData.getIdentifier(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                            }else{
                                showTost(data.getData().getRecordId() + "");
                            }
                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                    }
                });

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docinfo);
        unbinder= ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if(getIntent().hasExtra("docdata")){
            doctorData=(GetLiveDoctorListData.DataData.DataDataData) getIntent().getSerializableExtra("docdata");
        }
    }


    private void initView() {
        title.setText("医生详情");
if(doctorData!=null){
    if(TextUtils.isEmpty(doctorData.getDoctImagePath())){

        imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
    }else{
        Glide.with(context)
                .load(doctorData.getDoctImagePath())
                .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                .error(R.drawable.icon_doctor_default)
                .into(imgMessageAvator);

    }
    Common.DoctName=doctorData.getDoctName();
    Common.DoctImagePath=doctorData.getDoctImagePath();
    Common.ClinicName=doctorData.getClinicName();
    Common.Major=doctorData.getMajor();
    Common.ProfessName=doctorData.getProfessName();
    tv_name.setText(doctorData.getDoctName());
    tv_price.setText(doctorData.getServicePrice()+"元");
    tv_price2.setText(doctorData.getServicePrice()+"元");
    tv_role.setText(doctorData.getDepartName());
    tv_identity.setText(doctorData.getProfessName());
    tv_address.setText(doctorData.getClinicName());
    tv_like.setText("推荐"+doctorData.getAtteCount());
    tv_comment.setText("评论"+doctorData.getEvaluateCount());
    if(TextUtils.equals(doctorData.getOnlineState(),"2")){
        tv_allow.setText("可接入视频问诊");
        tv_allow.setBackgroundResource(R.color.green_00d154);
        tv_isonline.setText("在线");
        tv_isonline.setBackgroundResource(R.drawable.bg_button_online_green);
    }else  if(TextUtils.equals(doctorData.getOnlineState(),"0")){
        tv_allow.setText("未接诊");
        tv_allow.setBackgroundResource(R.color.gray_999996);
        tv_isonline.setText("离线");
        tv_isonline.setBackgroundResource(R.drawable.bg_button_online_gray);
    }else  if(TextUtils.equals(doctorData.getOnlineState(),"1")){
        tv_allow.setText("排队中");
        tv_allow.setBackgroundResource(R.color.yellow_ffcd0c);
        tv_isonline.setText("忙碌");
        tv_isonline.setBackgroundResource(R.drawable.bg_button_online_yellow);
    }
    tv_skilled.setText(doctorData.getMajor());
    tv_intro.setText(doctorData.getRemarks());
}


    }




}
