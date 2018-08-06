package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.DoctorListAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetLiveDoctorListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class DoctorListActivity extends BaseActivity {
    private Unbinder unbinder;
    private DoctorListAdapter doctorListAdapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.im_back)
    ImageView im_back;

    @OnClick(R.id.im_back)
    void back() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlist);
        unbinder= ButterKnife.bind(this);
        initView();
        initdoclist();
    }

    private void initdoclist() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getvideodoctors(token,"1","50"), new BaseObserver<GetLiveDoctorListData>(context) {
            @Override
            public void onResponse(GetLiveDoctorListData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    if(data.getData().getVideoDoctors().size()>0){

                        doctorListAdapter.setData(data.getData().getVideoDoctors());
                    }else{
                        recyclerview.setVisibility(View.GONE);
                    }

                }else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                recyclerview.setVisibility(View.GONE);
            }
        });
    }
    private void initView() {
        title.setText("视频复诊");

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        doctorListAdapter = new DoctorListAdapter(context);
        recyclerview.setAdapter(doctorListAdapter);

    }


}
