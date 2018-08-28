package v1.cn.unionc_pad.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.NurseListAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NurseListFragment extends BaseFragment {
    public TimePickerView pvTime;
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView mainRecycleview;

    private NurseListAdapter nurseListAdapter;
    private List<GetNurseListData.DataData.DataDataData> datas = new ArrayList<>();

    public NurseListFragment() {
        // Required empty public constructor
        Log.d("linshi", "NurseListFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
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
        View view = inflater.inflate(R.layout.layout_recycleview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getDoctorList();
    }


    private void initView() {

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        nurseListAdapter = new NurseListAdapter(context);
        nurseListAdapter.setOnClickMyTextView(new NurseListAdapter.onMyClick() {
            @Override
            public void myTextViewClick(String id) {
                initTimePicker(id);
            }
        });
        mainRecycleview.setAdapter(nurseListAdapter);

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
                    nurseListAdapter.setData(datas);
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

    private void initTimePicker(String id) {//Dialog 模式下，在底部弹出
        final String idd = id;
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
//                pickeduptime(getTime(date), idd);
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true)
                .build();

        pvTime.show();

    }

//    private void pickeduptime(String time, String id) {
//        if (!isLogin()) {
//            showTost("请先登录");
//            return;
//        }
//        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
//        //serviceType：1-护士，2-护工  :
//        //userType
//        //1-手机用户，2-老人
//        ConnectHttp.connect(UnionAPIPackage.subscribenurses(token, id, "1", time, "1", "", "", "", "", "2"), new BaseObserver<BaseData>(context) {
//            @Override
//            public void onResponse(BaseData data) {
//                if (TextUtils.equals("4000", data.getCode())) {
//                    showTost("预约成功");
//
//                } else {
//                    showTost(data.getMessage() + "");
//                }
//
//            }
//
//            @Override
//            public void onFail(Throwable e) {
//            }
//        });
//    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
