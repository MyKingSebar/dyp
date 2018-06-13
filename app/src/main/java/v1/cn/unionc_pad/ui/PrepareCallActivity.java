package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
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
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class PrepareCallActivity extends BaseActivity {
    private int progresstime;
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
        progresstime=60;

        myProgress.setProgressTextFormatPattern("60\n秒后自动呼叫" );
        handler.sendEmptyMessageDelayed(5000, 1000);

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


                    myProgress.setProgress(60-progresstime);
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
        RongCallKit.startSingleCall(PrepareCallActivity.this,IMUserId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
        finish();
    }
}
