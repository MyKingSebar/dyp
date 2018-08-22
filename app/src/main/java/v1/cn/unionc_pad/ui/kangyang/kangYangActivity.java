package v1.cn.unionc_pad.ui.kangyang;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import v1.cn.unionc_pad.adapter.KangYangAdapter;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.YiYangData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class kangYangActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.tv_title)
    TextView title;
    private String type;
    private KangYangAdapter activityAdapter;
    private List<YiYangData.DataData> datas = new ArrayList<>();
    @OnClick(R.id.img_back)
    void back() {
        finish();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kangyang);
        ButterKnife.bind(this);
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
        title.setText("康养视频");
        activityAdapter = new KangYangAdapter(context,gv);
        gv.setAdapter(activityAdapter);

    }

    private void getList() {
//        showDialog("加载中...");
        getDoctorList("");
//        if (TextUtils.equals(type, Common.APPLYACTIVITY)) {
//            initapply(token);
//        } else if (TextUtils.equals(type, Common.COLLECTACTIVITY)) {
//            initcollect(token);
//        } else if (TextUtils.equals(type, Common.AROUNDACTIVITY)) {
//            initaround(token);
//        }


    }



    private void getDoctorList(String lasttime) {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getyiyangVideo("1", lasttime), new BaseObserver<YiYangData>(context) {
            @Override
            public void onResponse(YiYangData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("200", data.getCode())) {
                    datas.clear();
                    if (data.getData().size() != 0) {
                        datas = data.getData();
                    }
                    activityAdapter.setData(datas);
                    closeDialog();
                    Logger.json(new Gson().toJson(datas));
                } else {
                    showTost(data.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });

    }




}
