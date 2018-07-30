package v1.cn.unionc_pad.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.UnioncURL;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.view.X5WebView;

public class WebViewActivity extends BaseActivity {
    public TimePickerView pvTime;
    public String nurseId;
    public  String serverId3;
    @BindView(R.id.webview_search)
    X5WebView webviewSearch;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.myProgressBar)
    ProgressBar bar;
    int type = 0;
    String activityId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.activity_search_x5_web_view);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        if (getIntent().hasExtra("type")) {
            type = getIntent().getIntExtra("type", 0);
        }
        Log.d("linshi", "todoor.type:" + type);
//        Log.d("linshi", "todoor.hasExtra:" + getIntent().hasExtra("activityid"));
//        Log.d("linshi", "todoor.hasExtra2:" + getIntent().getStringExtra("activityid"));
        if (getIntent().hasExtra("activityid")) {
            activityId = getIntent().getStringExtra("activityid");
        }
        if (getIntent().hasExtra("nurseId")) {
            nurseId = getIntent().getStringExtra("nurseId");
            Log.d("linshi", "nurseId:" + nurseId);
        }
    }

    private void initView() {
        webviewSearch.addJavascriptInterface(new JsInteration(), "android");
        webviewSearch.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(i);
                }
                super.onProgressChanged(webView, i);
            }
        });
        webviewSearch.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {

            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                closeDialog();
            }
        });
        switch (type) {
            case 1:
                //送药上门
                initonDoor();
                break;
            case 2:
                break;
            case 3:

                break;
            case Common.WEB_HELP:
//心电图使用说明
                initHelp();
                break;
            case 5:

                break;
        }
    }

    private void iniactivity() {
//        showDialog("加载中...");
        toplayout.setVisibility(View.GONE);
    }


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    void initonDoor() {
        tvTitle.setText("医护上门");
        String url = "http://192.168.21.93:8081/ipadH5/index.html";
        Log.d("linshi", "url" + url);
        webviewSearch.loadUrl(url);
    }

    private void initHelp() {
        toplayout.setVisibility(View.GONE);
        String url = UnioncURL.Unionc_WEB_Host + "pages/index.html#/help" ;
        Log.d("linshi", "url" + url);
        webviewSearch.loadUrl(url);

    }

    public class JsInteration {

        @JavascriptInterface
        public void buyserver(String serverId) {
            Log.d("pvTime", "JavascriptInterface.serverId:" + serverId);
            serverId3 = serverId;
            initTimePicker();
            //时间选择器
//            TimePickerView pvTime = new TimePickerBuilder(WebViewActivity.this, new OnTimeSelectListener() {
//                @Override
//                public void onTimeSelect(Date date, View v) {
//                    pickeduptime(getTime(date));
//                }
//            }).build();
//            pvTime.show();
        }


    }

    private void pickeduptime(String time) {
        if (TextUtils.isEmpty(nurseId)) {
            showTost("请先绑定护士");
            return;
        }
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.appointnurse(token, nurseId, serverId3, time), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("预约成功");
                    finish();

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


    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Log.i("pvTime", "onTimeSelect");
                pickeduptime(getTime(date));
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
//        Dialog mDialog = pvTime.getDialog();
//        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            pvTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//            }
//        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
