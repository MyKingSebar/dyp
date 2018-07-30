package v1.cn.unionc_pad.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.ui.health.HtmlFontUtil;

/**
 * Created by qy on 2018/2/6.
 */

public class BaseFragment extends Fragment {
    float density;
    public DisplayMetrics dm;
    HtmlFontUtil mFontUtil;

    protected Context context;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initDensity();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    /**
     * tusi
     *
     * @param message
     */
    protected void showTost(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 是否登录
     */
    protected boolean isLogin() {
        return SPUtil.contains(context, Common.USER_TOKEN);
    }


    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }


    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Common.DATA, bundle);
        startActivity(intent);
    }

    /**
     * 展示加载框
     *
     * @param message 需要提示的信息
     */
    protected void showDialog(String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void closeDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public int dip2px(float dipValue) {
        return (int) (dipValue * density  + 0.5f);
    }
    private void initDensity() {
        dm = getResources().getDisplayMetrics();
        mFontUtil = new HtmlFontUtil();
        density = dm.density;
    }
}
