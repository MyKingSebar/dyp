package v1.cn.unionc_pad.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dinuscxj.progressbar.CircleProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class PrepareCallActivity extends BaseActivity {
    @BindView(R.id.myProgress)
    CircleProgressBar myProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_call);
        ButterKnife.bind(this);

    }
}
