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
import v1.cn.unionc_pad.adapter.DoorNurseAdapter;
import v1.cn.unionc_pad.adapter.NurseServiceAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorNueseFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.gv)
    GridView gv;
    private String type;
    private DoorNurseAdapter activityAdapter;
    private List<GetNurseListData.DataData.DataDataData> datas = new ArrayList<>();

    public DoorNueseFragment() {
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

    public DoorNueseFragment newInstance(String text) {
        DoorNueseFragment myActivity1Fragment = new DoorNueseFragment();
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
        View view = inflater.inflate(R.layout.fragment_gridview2, container, false);
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
        activityAdapter = new DoorNurseAdapter(context,gv);
        gv.setAdapter(activityAdapter);

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
        String la = (String) SPUtil.get(context, Common.LATITUDE, "");
        String lo = (String) SPUtil.get(context, Common.LONGITUDE, "");
        Log.d("linshi","la2:"+la + "," + lo);
        ConnectHttp.connect(UnionAPIPackage.getnurses("4", "1", "50",lo,la), new BaseObserver<GetNurseListData>(context) {
            @Override
            public void onResponse(GetNurseListData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getNursers().size() != 0) {
                        datas = data.getData().getNursers();
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
