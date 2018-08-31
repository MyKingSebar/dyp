package v1.cn.unionc_pad.ui.door;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.neliveplayer.sdk.NEDynamicLoadingConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.netease.neliveplayer.sdk.NESDKConfig;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import v1.cn.demo.activity.NEVideoPlayerActivity;
import v1.cn.demo.receiver.NELivePlayerObserver;
import v1.cn.demo.receiver.Observer;
import v1.cn.demo.util.HttpPostUtils;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.LiveListAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class ChooseHuliServiceActivity extends BaseActivity {
    private String docid;
private Intent intent;
    private Unbinder unbinder;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_huliservice);
        unbinder= ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if(getIntent().hasExtra("docid")){
            docid=getIntent().getStringExtra("docid");
        }
    }


    private void initView() {
        title.setText("选择护理服务");



    }




    @OnClick({R.id.caixue, R.id.zhushe, R.id.shuye, R.id.daoniao, R.id.wuhua,R.id.guanchang})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.caixue:
                intent =new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","3");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
            case R.id.shuye:
                 intent=new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","2");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
            case R.id.zhushe:
                 intent=new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","4");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
            case R.id.daoniao:
                 intent=new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","7");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
            case R.id.wuhua:
                 intent=new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","14");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
            case R.id.guanchang:
                 intent=new Intent(ChooseHuliServiceActivity.this,ServiceInfoActivity.class);
                intent.putExtra("id","12");
                intent.putExtra("docid",docid);
                startActivity(intent);
                break;
        }
    }


}
