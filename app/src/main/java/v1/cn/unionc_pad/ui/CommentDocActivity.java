package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.DoctorListAdapter2;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.GetLiveDoctorListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.view.CircleImageView;

public class CommentDocActivity extends BaseActivity {
    private int tj = 0;//1-不推荐，5-推荐
    private String isAnonymity = "1";//0否，1是
    private String recordId;
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.img_back)
    ImageView im_back;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.keshi)
    TextView keshi;
    @BindView(R.id.zhiwei)
    TextView zhiwei;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.tuijian)
    TextView tuijian;
    @BindView(R.id.butuijian)
    TextView butuijian;
    @BindView(R.id.bt_tijiao)
    Button bt_tijiao;
    @BindView(R.id.bt_quxiao)
    Button bt_quxiao;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.et)
    EditText et;

    @OnClick(R.id.bt_tijiao)
     void tijiao() {
        String pingjia = et.getText().toString();
        if (TextUtils.isEmpty(pingjia) && tj == 0) {
            showTost("请选择是否推荐或评价！");
            return;
        } else {
            cli(recordId, tj + "", pingjia, isAnonymity);
        }
    }

    @OnClick(R.id.bt_quxiao)
     void quxiao() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        unbinder = ButterKnife.bind(this);
        if (TextUtils.isEmpty(Common.docvideoid)) {
            finish();
        } else {
            recordId = Common.docvideoid;
            if (TextUtils.isEmpty(Common.DoctImagePath)) {

                img.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(Common.DoctImagePath)
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(img);

            }
            name.setText(Common.DoctName);
            keshi.setText(Common.Major);
            zhiwei.setText(Common.ProfessName);
            add.setText(Common.ClinicName);
            Log.d("linshi","Common"+ Common.docvideoid+ Common.DoctName+Common.DoctImagePath+Common.ClinicName+Common.Major+Common.ProfessName);

        }

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.docvideoid = "";
        Common.DoctName = "";
        Common.DoctImagePath = "";
        Common.ClinicName = "";
        Common.Major = "";
        Common.ProfessName = "";
        Common.ClinicName = "";
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        title.setText("就诊评价");
        tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuijian.setBackgroundResource(R.drawable.icon_tj_on);
                butuijian.setBackgroundResource(R.drawable.icon_btj_no);
                tj = 5;
            }
        });
        butuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuijian.setBackgroundResource(R.drawable.icon_tj_no);
                butuijian.setBackgroundResource(R.drawable.icon_btj_on);
                tj = 1;
            }
        });
        cb.setChecked(true);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAnonymity = "1";
                } else {
                    isAnonymity = "0";
                }
            }
        });

    }


    private void cli(String recordId, String starCount, String content, String isAnonymity) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.videodoctorevaluate(token, recordId, starCount, content, isAnonymity), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {

                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("评价成功");
                    finish();
                } else {
                    Log.e("linshi", "deletemessage" + data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
