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

public class SuperviseBloodActivity extends BaseActivity {

    @OnClick(R.id.tv_back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervise_blood);
        ButterKnife.bind(this);
init();
    }

    private void init() {
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
