package v1.cn.unionc_pad.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.facebook.stetho.common.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.callkit.RongCallKit;
import v1.cn.unionc_pad.PadTest;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.GetGuardianshipInfoData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class PrepareCallActivity extends BaseActivity {
    private int progresstime;
    final static  int calltime=15;
    Spannable sp;
    private String IMUserId = "";
    private String HeadImage = "";
    private String GuardianUserName = "";

    @BindView(R.id.myProgress)
    CircleProgressBar myProgress;
    @BindView(R.id.bt_call)
    Button bt_call;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.toplayout)
    void toplayout(){
        finish();
    }
    @OnClick({R.id.bt_call, R.id.bt_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_call:
                progresstime=-1;
                handler.removeMessages(5000);
                Call();
                break;
            case R.id.bt_cancel:
                progresstime=-1;
                handler.removeMessages(5000);
                finish();
                break;
            case R.id.img_back:
                progresstime=-1;
                handler.removeMessages(5000);
                finish();
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progresstime=-1;
        handler.removeMessages(5000);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_call);
        ButterKnife.bind(this);
        initdata();
        init();
    }

    private void initdata() {
        IMUserId = getIntent().getStringExtra("IMUserId");
        HeadImage = getIntent().getStringExtra("HeadImage");
        GuardianUserName = getIntent().getStringExtra("GuardianUserName");

    }

    private void init() {
        sp = new SpannableString("秒后自动呼叫");
        sp.setSpan(new AbsoluteSizeSpan(24,false),0,5,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        progresstime=calltime;

        myProgress.setProgressTextFormatPattern(calltime+"\n秒后自动呼叫" );
        handler.sendEmptyMessageDelayed(5000, 1000);
        MediaPlayer mMediaPlayer;
        mMediaPlayer=MediaPlayer.create(this, R.raw.call);
        mMediaPlayer.start();
//        SoundPool soundPool=new  SoundPool(100, AudioManager.STREAM_MUSIC,0);//构建对象
//        int soundId=soundPool.load(context,R.raw.call,1);//加载资源，得到soundId
//        int streamId= soundPool.play(soundId, 1,1,1,0,1);//播放，得到StreamId

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            ArcProgress progressBar = (ArcProgress) msg.obj;
//            progressBar.setProgress(msg.what);
            if (msg.what == 5000) {
                handler.removeMessages(5000);

                if (progresstime>0) {
                    progresstime--;


                    myProgress.setProgress(calltime

                            -progresstime);
                    myProgress.setProgressTextFormatPattern("" + progresstime+"\n"+"秒后自动呼叫");
//                    myProgress.setProgressTextFormatPattern(Html.fromHtml("今天" + "<font color='#FF0000'>" + progresstime + "</font>"));
                    handler.sendEmptyMessageDelayed(5000, 1000);
                }else{
                    Call();
                }
            }

        }
    };

    private void Call(){
        getRongInfo();
    }
    private void getRongInfo(){
        String Token=(String) SPUtil.get(context, Common.USER_TOKEN, "");
        if(TextUtils.isEmpty(Token)){
            showTost("请重新登录!");
            return;
        }
        ConnectHttp.connect(UnionAPIPackage.GetGuardianshipInfo(Token),
                new BaseObserver<GetGuardianshipInfoData>(context) {
                    @Override
                    public void onResponse(GetGuardianshipInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(data.getData().getIMUserId())) {
                                RongCallKit.startSingleCall(PrepareCallActivity.this,data.getData().getIMUserId(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                                finish();
                            }
                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });



    }
//    private void Call(){
//        RongCallKit.startSingleCall(PrepareCallActivity.this,IMUserId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//        finish();
//    }
}
