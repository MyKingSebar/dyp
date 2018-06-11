package v1.cn.unionc_pad.Rong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import v1.cn.unionc_pad.R;

public class ConversationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        String sName = getIntent().getData().getQueryParameter("title");//获取昵称
        setTitle("与" + sName + "聊天中");
    }
}