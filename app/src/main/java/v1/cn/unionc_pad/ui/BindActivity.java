package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class BindActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_linshi);
        ButterKnife.bind(this);
init();
    }

    private void init() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("亲情监护绑定");
    }
}
