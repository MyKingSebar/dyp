package v1.cn.unionc_pad.ui.kangyang;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
public class kangYangvidoeActivity extends BaseActivity {
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.videoView)
    VideoView videoView;
    private String url;

    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kangyang_vidoe);
        ButterKnife.bind(this);
        BusProvider.getInstance().register(this);
        initData();
        initView();
    }

    private void initData() {
        if(getIntent().hasExtra("url")){
            url= getIntent().getStringExtra("url");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


    private void initView() {
        title.setVisibility(View.INVISIBLE);
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());


        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            //设置视频路径
            videoView.setVideoURI(uri);

            //开始播放视频
            videoView.start();
        }
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            showTost("播放完成了");
        }
    }


}
