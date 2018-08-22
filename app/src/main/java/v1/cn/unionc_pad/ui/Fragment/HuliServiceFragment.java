package v1.cn.unionc_pad.ui.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.NurseServiceAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HuliServiceFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    private String type;
    private NurseServiceAdapter activityAdapter;
    private List<visitnurseservicesData.DataData.ServiceData> datas = new ArrayList<>();

    public HuliServiceFragment() {
        // Required empty public constructor
        Log.d("linshi", "WatchingHospitalFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        if (getArguments() != null) {
            //取出保存的值
            type = getArguments().getString("type");
            Log.d("linshi", "type：" + type);
        }
    }

    public HuliServiceFragment newInstance(String text) {
        HuliServiceFragment myActivity1Fragment = new HuliServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", text);
        //fragment保存参数，传入一个Bundle对象
        myActivity1Fragment.setArguments(bundle);
        return myActivity1Fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getList();
    }


    private void initView() {
        activityAdapter = new NurseServiceAdapter(context,gv);
gv.setAdapter(activityAdapter);

    }

    private void getList() {
//        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        initcollect(token);
//        if (TextUtils.equals(type, Common.APPLYACTIVITY)) {
//            initapply(token);
//        } else if (TextUtils.equals(type, Common.COLLECTACTIVITY)) {
//            initcollect(token);
//        } else if (TextUtils.equals(type, Common.AROUNDACTIVITY)) {
//            initaround(token);
//        }


    }

    private void initcollect(String token) {
        ConnectHttp.connect(UnionAPIPackage.visitnurseservices(token,""), new BaseObserver<visitnurseservicesData>(context) {
            @Override
            public void onResponse(visitnurseservicesData data) {
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
