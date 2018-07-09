package v1.cn.unionc_pad.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.IsBindJianhurenData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.utils.ZXingUtils;

public class SuperviseActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.tv_blood)
    void blood(){
        goNewActivity(SuperviseBloodActivity.class);
    }
    @OnClick(R.id.tv_heart)
    void heart(){
        goNewActivity(SuperviseHeartActivity.class);
    }
    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise);
        ButterKnife.bind(this);
init();
    }

    private void init() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("亲情监护绑定");
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}