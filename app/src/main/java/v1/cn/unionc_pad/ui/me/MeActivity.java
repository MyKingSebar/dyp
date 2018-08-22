package v1.cn.unionc_pad.ui.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.DoorNurseAdapter;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.view.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.jhr)
    TextView jhr;
    @BindView(R.id.re_dd)
    RelativeLayout re_dd;
    @BindView(R.id.re_jd)
    RelativeLayout re_jd;
    @BindView(R.id.re_qr)
    RelativeLayout re_qr;
    @BindView(R.id.re_pj)
    RelativeLayout re_pj;
    @BindView(R.id.jkda)
    TextView jkda;
    @BindView(R.id.wzjl)
    TextView wzjl;
    @BindView(R.id.hljl)
    TextView hljl;
    @BindView(R.id.dcjl)
    TextView dcjl;
    @BindView(R.id.jkjc)
    TextView jkjc;
    @BindView(R.id.kfzx)
    TextView kfzx;
    @BindView(R.id.xtsz)
    TextView xtsz;
    @BindView(R.id.gyyj)
    TextView gyyj;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }


    private String type;
    private List<GetNurseListData.DataData.DataDataData> datas = new ArrayList<>();

    public MeActivity() {
        // Required empty public constructor
        Log.d("linshi", "WatchingHospitalFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        BusProvider.getInstance().register(this);
        initView();
        getList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }



    private void initView() {
        title.setText("个人中心");

    }

    private void getList() {
//        showDialog("加载中...");
        getDoctorList();
//        if (TextUtils.equals(type, Common.APPLYACTIVITY)) {
//            initapply(token);
//        } else if (TextUtils.equals(type, Common.COLLECTACTIVITY)) {
//            initcollect(token);
//        } else if (TextUtils.equals(type, Common.AROUNDACTIVITY)) {
//            initaround(token);
//        }


    }



    private void getDoctorList() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getnurses("4", "1", "50"), new BaseObserver<GetNurseListData>(context) {
            @Override
            public void onResponse(GetNurseListData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getNursers().size() != 0) {
                        datas = data.getData().getNursers();
                    }
                    closeDialog();
                    Logger.json(new Gson().toJson(datas));
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
