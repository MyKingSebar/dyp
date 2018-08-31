package v1.cn.unionc_pad.ui;

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

import com.bumptech.glide.Glide;
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
import recycler.coverflow.RecyclerCoverFlow;
import v1.cn.demo.activity.NEVideoPlayerActivity;
import v1.cn.demo.receiver.NELivePlayerObserver;
import v1.cn.demo.receiver.Observer;
import v1.cn.demo.util.HttpPostUtils;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.adapter.LiveListAdapter;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class LiveListActivity extends BaseActivity {
    private String decodeType = "software";  //解码类型，默认软件解码
    private String mediaType = "livestream"; //媒体类型，默认网络直播

    private NESDKConfig config;

    private Unbinder unbinder;
    private LiveListAdapter doctorListAdapter;
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
    @OnClick(R.id.toplayout)
    void toplayout(){
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorlist2);
        unbinder= ButterKnife.bind(this);
        /**   6.0权限申请     **/
        bPermission = checkPublishPermission();
        initView();
        initlive();
        initlivelist();
    }


    private void initView() {
        title.setText("直播");

        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        doctorListAdapter = new LiveListAdapter(context);
        doctorListAdapter.setOnClickMyTextView(new LiveListAdapter.onClickMyTextView() {

            @Override
            public void myTextViewClick(String url) {
                Intent intent = new Intent(LiveListActivity.this, NEVideoPlayerActivity.class);


                Log.d("linshi0", "url:" + url);

                if (!bPermission) {
                    Toast.makeText(getApplication(), "请先允许app所需要的权限", Toast.LENGTH_LONG).show();
                    bPermission = checkPublishPermission();
                    return;
                }
                if (config != null && config.dynamicLoadingConfig != null && config.dynamicLoadingConfig.isDynamicLoading && !NELivePlayer.isDynamicLoadReady()) {
                    Toast.makeText(getApplication(), "请等待加载so文件", Toast.LENGTH_LONG).show();
                    return;
                }

                //把多个参数传给NEVideoPlayerActivity
                intent.putExtra("media_type", mediaType);
                intent.putExtra("decode_type", decodeType);
                intent.putExtra("videoPath", url);
                startActivity(intent);


            }
        });
        recyclerview.setAdapter(doctorListAdapter);

    }
    void initlive() {
        config = new NESDKConfig();
        //动态加载
        config.dynamicLoadingConfig = new NEDynamicLoadingConfig();
        //是否开启动态加载功能，默认关闭
//		config.dynamicLoadingConfig.isDynamicLoading = true;
        config.dynamicLoadingConfig.isArmeabi = true;
        config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;
        config.supportDecodeListener = mOnSupportDecodeListener;
        //SDK将内部的网络请求以回调的方式开给上层，如果需要上层自己进行网络请求请实现config.dataUploadListener，如果上层不需要自己进行网络请求而是让SDK进行网络请求，这里就不需要操作config.dataUploadListener
        config.dataUploadListener = mOnDataUploadListener;
        NELivePlayer.init(this, config);
        NELivePlayerObserver.getInstance().observeNELivePlayerObserver(observer, true);
    }
    private void initlivelist() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        Log.d("linshi","token:"+token);
        ConnectHttp.connect(UnionAPIPackage.getlivelist(token, "1", "20"), new BaseObserver<NetCouldPullData>(context) {
            @Override
            public void onResponse(NetCouldPullData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    if(data.getData().getLives().size()>0){

                        doctorListAdapter.setData(data.getData().getLives());
                    }else{
                        recyclerview.setVisibility(View.GONE);
                    }
                } else {
                    showTost(data.getMessage() + "");
                }

            }

            @Override
            public void onFail(Throwable e) {
                showTost("暂无直播");
            }
        });

    }


    /**
     * 6.0权限处理
     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        Log.d("linshi", "on pause");
        super.onPause();
    }


    @Override
    public void onRestart() {
        Log.d("linshi", "on restart");
        super.onRestart();
    }

    @Override
    public void onResume() {
        Log.d("linshi", "on resmue");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.d("linshi", "on start");
        super.onStart();
    }

    private Observer<Void> observer = new Observer<Void>() {
        @Override
        public void onEvent(Void aVoid) {
            //接收到播放器资源释放结束通知
            Log.i("linshi", "onEvent -> NELivePlayer RELEASE SUCCESS!");
        }
    };

    private NELivePlayer.OnDynamicLoadingListener mOnDynamicLoadingListener = new NELivePlayer.OnDynamicLoadingListener() {
        @Override
        public void onDynamicLoading(NEDynamicLoadingConfig.ArchitectureType type, boolean isCompleted) {
            Log.d("linshi", "type:" + type + "，isCompleted:" + isCompleted);
        }
    };

    private NELivePlayer.OnSupportDecodeListener mOnSupportDecodeListener = new NELivePlayer.OnSupportDecodeListener() {
        @Override
        public void onSupportDecode(boolean isSupport) {
            Log.d("linshi", "是否支持H265硬件解码 onSupportDecode isSupport:" + isSupport);
            //如果支持H265硬件解码，那么可以使用H265的视频源进行播放
        }
    };


    private NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {
        @Override
        public boolean onDataUpload(String url, String data) {
            Log.d("linshi", "onDataUpload url:" + url + ", data:" + data);
            sendData(url, data);
            return true;
        }

        @Override
        public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
            Log.d("linshi", "onDataUpload url:" + url + ", params:" + params + ",filepaths:" + filepaths);
            return (new HttpPostUtils(url, params, filepaths).connPost());
        }
    };

    private boolean sendData(final String urlStr, final String content) {
        int response = 0;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());

            response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.i("linshi", " sendData finished,data:" + content);

            } else {
                Log.i("linshi", " sendData, response: " + response);

            }
        } catch (IOException e) {
            Log.e("linshi", "sendData, recv code is error: " + e.getMessage());

        } catch (Exception e) {
            Log.e("linshi", "sendData, recv code is error2: " + e.getMessage());

        }
        return (response == HttpURLConnection.HTTP_OK);
    }
}
