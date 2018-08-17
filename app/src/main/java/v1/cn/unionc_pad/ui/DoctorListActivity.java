package v1.cn.unionc_pad.ui;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.DoctorListAdapter;
import v1.cn.unionc_pad.adapter.DoctorListAdapter2;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetLiveDoctorListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.utils.GPSUtils;

import static v1.cn.unionc_pad.utils.GPSUtils.locationListener;

public class DoctorListActivity extends BaseActivity {
    private Unbinder unbinder;
    private DoctorListAdapter2 doctorListAdapter;
    @BindView(R.id.recyclerview)
    RecyclerCoverFlow recyclerview;
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

    @Override
    protected void onResume() {
        super.onResume();
        initdoclist();
    }
    //从网络获取经纬度
    public String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
                            SPUtil.put(context, Common.USER_LATITUDE, ""+latitude);
                SPUtil.put(context, Common.USER_LONGITUDE,""+longitude);
        }
        Log.d("linshi","la:"+longitude + "," + latitude);
        return longitude + "," + latitude;
    }
    private void initdoclist() {
//            Map map= GPSUtils.getInstance(context).getLocation();
//            if(map!=null){
//                SPUtil.put(context, Common.USER_LATITUDE, (String)map.get("latitude"));
//                SPUtil.put(context, Common.USER_LONGITUDE, (String)map.get("longitude"));
//                Log.d("linshi","la:"+map.get("latitude"));
//            }else{
//                Log.d("linshi","map==null");
//            }
        getLngAndLatWithNetwork();
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        String la = (String) SPUtil.get(context, Common.USER_LATITUDE, "");
        String lo = (String) SPUtil.get(context, Common.USER_LONGITUDE, "");
        Log.d("linshi","la2:"+la + "," + lo);
        ConnectHttp.connect(UnionAPIPackage.getvideodoctors(token,"1","50",lo,la), new BaseObserver<GetLiveDoctorListData>(context) {
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

//        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        doctorListAdapter = new DoctorListAdapter2(context);
        recyclerview.setAdapter(doctorListAdapter);

    }


}
