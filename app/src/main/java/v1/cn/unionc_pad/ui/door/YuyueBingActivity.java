package v1.cn.unionc_pad.ui.door;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.DoorDiseaseAdapter;
import v1.cn.unionc_pad.adapter.DoorNurseAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.YuYueBingData;
import v1.cn.unionc_pad.model.visitnurserdiseaseData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class YuyueBingActivity extends BaseActivity {
    private String DiseaseId;
    private String Diseasename;
    private Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.tv_title)
    TextView title;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }
    @OnClick(R.id.toplayout)
    void toplayout(){
        finish();
    }
    @OnClick(R.id.bt_ok)
    void ok() {
        if (TextUtils.isEmpty(DiseaseId)) {
            showTost("请选择疾病");
            return;
        }
        YuYueBingData data = new YuYueBingData();
        data.setId(DiseaseId);
        data.setName(Diseasename);
        BusProvider.getInstance().post(data);
        finish();
    }


    private String type;
    private DoorDiseaseAdapter activityAdapter;
    private List<visitnurserdiseaseData.DataData.DiseaseData> datas = new ArrayList<>();

    public YuyueBingActivity() {
        // Required empty public constructor
        Log.d("linshi", "WatchingHospitalFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_yuyue_bing);
        unbinder = ButterKnife.bind(this);
        initView();
        getList();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


    private void initView() {
        activityAdapter = new DoorDiseaseAdapter(context, gv);
        activityAdapter.setmListener(new DoorDiseaseAdapter.MyClickListener() {
            @Override
            public void clickListener(String id, String name) {
                DiseaseId = id;
                Diseasename = name;
            }
        });
        gv.setAdapter(activityAdapter);

    }

    private void getList() {
        title.setText("疾病选择");
//        showDialog("加载中...");
        getDiseaseList();
//        if (TextUtils.equals(type, Common.APPLYACTIVITY)) {
//            initapply(token);
//        } else if (TextUtils.equals(type, Common.COLLECTACTIVITY)) {
//            initcollect(token);
//        } else if (TextUtils.equals(type, Common.AROUNDACTIVITY)) {
//            initaround(token);
//        }


    }


    private void getDiseaseList() {
        showDialog("加载中...");
        String Token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.visitnurserdisease(Token), new BaseObserver<visitnurserdiseaseData>(context) {
            @Override
            public void onResponse(visitnurserdiseaseData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getData().size() != 0) {
                        datas = data.getData().getData();
                    }
                    activityAdapter.setData(datas);
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
