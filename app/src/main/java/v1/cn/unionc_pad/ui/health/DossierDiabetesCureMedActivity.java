package v1.cn.unionc_pad.ui.health;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

/**
 * Created by dell on 2015/9/9.
 */
public class DossierDiabetesCureMedActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;
    @OnClick(R.id.img_back)
    public void back(){
        finish();
    }
    @BindView(R.id.dossierdiabetes_medicine_edit)
    EditText mContentEt;
    @OnClick(R.id.dossierdiabetes_medicine_commit_btn)


    void review() {
        Intent intent = new Intent();
        intent.putExtra("content",mContentEt.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curemedicine);
        ButterKnife.bind(this);
        mContentEt.setText(getIntent().getStringExtra("content"));
        tvTitle.setText("治疗药物");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
