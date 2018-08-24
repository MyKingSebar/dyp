package v1.cn.unionc_pad.ui.kangyang;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
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
//    @BindView(R.id.gv)
//    GridView gv;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.pull_to_refresh_listview)
    PullToRefreshGridView pullToRefreshView;
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
        activityAdapter = new KangYangAdapter(context,pullToRefreshView);
            pullToRefreshView.setAdapter(activityAdapter);
            //给上拉加载下拉刷新控件设置监听对象
            pullToRefreshView.setOnRefreshListener(refresh);
            //设置可以上拉和下拉，否则默认支持的是下拉
            pullToRefreshView.setMode(PullToRefreshBase.Mode.BOTH);

//// 设置下拉刷新监听器
//        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//            @Override
//            public void onRefresh() {
////showTost("");
////                Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
//
//                myRefreshListView.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // 更新数据
//                        datas.clear();
//                        if(datas.size()>0){
//                            getDoctorList("1",datas.get(0).getInserttime());
//                        }else{
//                            getDoctorList("0","");
//                        }
//                        // 更新完后调用该方法结束刷新
////                        myRefreshListView.setRefreshing(false);
//                    }
//                }, 0);
//            }
//        });
//
//        // 加载监听器
//        myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {
//
//            @Override
//            public void onLoad() {
//
//
//                myRefreshListView.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if(datas.size()>0){
//                            getDoctorList("0",datas.get(datas.size()-1).getInserttime());
//                        }
////                        datas.add(new Date().toGMTString());
////                        adapter.notifyDataSetChanged();
//                        // 加载完后调用该方法
//                        myRefreshListView.setLoading(false);
//                    }
//                }, 0);
//
//            }
//        });
    }

    private void getList() {
//        showDialog("加载中...");
        datas.clear();
        getDoctorList("0","");
//        if (TextUtils.equals(type, Common.APPLYACTIVITY)) {
//            initapply(token);
//        } else if (TextUtils.equals(type, Common.COLLECTACTIVITY)) {
//            initcollect(token);
//        } else if (TextUtils.equals(type, Common.AROUNDACTIVITY)) {
//            initaround(token);
//        }


    }



    private void getDoctorList(String direction, String lasttime) {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getyiyangVideo(direction, lasttime), new BaseObserver<YiYangData>(context) {
            @Override
            public void onResponse(YiYangData data) {
                Log.d("linshi", "datas:" + new Gson().toJson(datas));

                if (TextUtils.equals("200", data.getCode())) {
//                    datas.clear();
                    if (data.getData().size() != 0) {
                        datas.addAll (data.getData());
                    }
                    activityAdapter.setData(datas);
                    closeDialog();
                    Logger.json(new Gson().toJson(datas));
                    pullToRefreshView.onRefreshComplete();
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
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getDoctorList("1",(String)msg.obj);
                    break;
                case 0:
                    getDoctorList("0",(String)msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 刷新的监听对象
     */
    PullToRefreshBase.OnRefreshListener2<GridView> refresh = new PullToRefreshBase.OnRefreshListener2<GridView>() {
        //在顶部时，。。。下拉刷新
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    datas.clear();
                    if(datas.size()>0){
                        Message msg=Message.obtain();
                        msg.what=1;
                        msg.obj=datas.get(0).getInserttime();
                        myHandler.sendMessage(msg);
//                        getDoctorList("1",datas.get(0).getInserttime());
                    }else{
                        Message msg=Message.obtain();
                        msg.what=0;
                        msg.obj="";
                        myHandler.sendMessage(msg);
//                        getDoctorList("0","");
                    }
                }
            }).start();

        }

        //在底部时，。。。上拉刷新
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if(datas.size()>0){
                        Message msg=Message.obtain();
                        msg.what=0;
                        msg.obj=datas.get(datas.size()-1).getInserttime();
                        myHandler.sendMessage(msg);
//                        getDoctorList("0",datas.get(datas.size()-1).getInserttime());
                    }
                }
            }).start();

        }
    };

}
