package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
//        Log.d("linshi", "todoor.type:" + type);
//        Log.d("linshi", "todoor.hasExtra:" + getIntent().hasExtra("activityid"));
//        Log.d("linshi", "todoor.hasExtra2:" + getIntent().getStringExtra("activityid"));
        if (getIntent().hasExtra("activityid")) {
            activityId = getIntent().getStringExtra("activityid");
        }
    }

    private void initView() {


    }

    private void iniactivity() {
//        showDialog("加载中...");
        toplayout.setVisibility(View.GONE);
    }


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }





}
