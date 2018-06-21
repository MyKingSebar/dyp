package v1.cn.unionc_pad.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.ui.base.BaseActivity;
import v1.cn.unionc_pad.utils.ZXingUtils;

public class BindActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_two)
    TextView tv_two;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_linshi2);
        ButterKnife.bind(this);
init();
    }

    private void init() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("亲情监护绑定");
        String userid=(String) SPUtil.get(context, Common.USER_ID, "");
        Bitmap bitmap = ZXingUtils.createQRImage("Pad二维码"+userid, 600, 600);
        BitmapDrawable bd=new BitmapDrawable(bitmap);
        tv_two.setBackgroundDrawable(bd);
    }
}
