package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.view.X5WebView;

public class WebViewActivity extends BaseActivity {

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
    }

    private void initView() {

        webviewSearch.setWebChromeClient(new WebChromeClient(){
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
            case 4:

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



void initonDoor(){
    tvTitle.setText("医护上门");
    String url = "http://192.168.21.93:8081/ipadH5/index.html" ;
    Log.d("linshi", "url" + url);
    webviewSearch.loadUrl(url);
}

}
