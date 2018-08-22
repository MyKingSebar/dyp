package v1.cn.unionc_pad.ui.door;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.shihao.library.XRadioGroup;
import v1.cn.unionc_pad.BusProvider;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.YuYue7DayData;
import v1.cn.unionc_pad.model.YuYueBingData;
import v1.cn.unionc_pad.model.YuYueTimeData;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class YuyueTimeActivity extends BaseActivity {
    private List<YuYue7DayData> daylist = new ArrayList<>();
    private String timeday;
    private String timehour;
    private Intent intent;
    private Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView title;

    @BindViews({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7})
    TextView[] tv_1s;
    @BindViews({R.id.tv2_1, R.id.tv2_2, R.id.tv2_3, R.id.tv2_4, R.id.tv2_5, R.id.tv2_6, R.id.tv2_7})
    TextView[] tv_2s;
    @BindViews({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5, R.id.ll6, R.id.ll7})
    LinearLayout[] lls;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv2_1)
    TextView tv2_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv2_2)
    TextView tv2_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv2_3)
    TextView tv2_3;
    @BindView(R.id.tv_4)
    TextView tv_4;
    @BindView(R.id.tv2_4)
    TextView tv2_4;
    @BindView(R.id.tv_5)
    TextView tv_5;
    @BindView(R.id.tv2_5)
    TextView tv2_5;
    @BindView(R.id.tv_6)
    TextView tv_6;
    @BindView(R.id.tv2_6)
    TextView tv2_6;
    @BindView(R.id.tv_7)
    TextView tv_7;
    @BindView(R.id.tv2_7)
    TextView tv2_7;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.ll5)
    LinearLayout ll5;
    @BindView(R.id.ll6)
    LinearLayout ll6;
    @BindView(R.id.ll7)
    LinearLayout ll7;

    @BindView(R.id.rp)
    me.shihao.library.XRadioGroup rp;


    @OnClick(R.id.img_back)
    void back() {
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangmen_yuyue_time);

        unbinder = ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        title.setText("选择服务时间");
        getDate();
        for (int i = 0; i < daylist.size(); i++) {
            final int ii=i;
            if (i == 0) {
                tv_1s[i].setText("今天");
            } else if (i == 1) {
                tv_1s[i].setText("明天");
            } else {

                tv_1s[i].setText(daylist.get(i).getTop());

            }
            tv_2s[i].setText(daylist.get(i).getButtom());
            lls[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeday=daylist.get(ii).getAlldate();
                    lls[ii].setBackgroundResource(R.drawable.bg_yellow_line_right);
                    tv_1s[ii].setTextColor(Color.parseColor("#FFFFFF"));
                    tv_2s[ii].setTextColor(getResources().getColor(R.color.white));
                    for(int j=0;j<daylist.size();j++){
                        if(j!=ii){
                            lls[j].setBackgroundResource(R.drawable.bg_yellow_line_left);
                            tv_1s[j].setTextColor(getResources().getColor(R.color.yellowf78126));
                            tv_2s[j].setTextColor(getResources().getColor(R.color.yellowf78126));
                        }
                    }
                }
            });
        }


        rp.setOnCheckedChangeListener(new XRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(XRadioGroup xRadioGroup, int i) {
                RadioButton rb = (RadioButton) YuyueTimeActivity.this.findViewById(rp.getCheckedRadioButtonId());
                timehour = rb.getText().toString() + ":00";
            }
        });

    }

    private void getDate() {
        daylist.clear();
        for (int i = 0; i < 7; i++) {

            Date date = new Date();//取时间
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime(); //这个时间就是日期往后推一天的结果
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd");
            String dateString2 = formatter2.format(date);
            YuYue7DayData da = new YuYue7DayData();
            da.setAlldate(dateString);
            da.setButtom(dateString2);
            String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            if ("1".equals(mWay)) {
                mWay = "周日";
            } else if ("2".equals(mWay)) {
                mWay = "周一";
            } else if ("3".equals(mWay)) {
                mWay = "周二";
            } else if ("4".equals(mWay)) {
                mWay = "周三";
            } else if ("5".equals(mWay)) {
                mWay = "周四";
            } else if ("6".equals(mWay)) {
                mWay = "周五";
            } else if ("7".equals(mWay)) {
                mWay = "周六";
            }
            da.setTop(mWay);
            daylist.add(da);
        }
    }


    @OnClick({R.id.bt_yy})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_yy:
                if (TextUtils.isEmpty(timehour)) {
                    showTost("请选择时间");
                    return;
                }
                if (TextUtils.isEmpty(timeday)) {
                    showTost("请选择日期");
                    return;
                }
                YuYueTimeData data=new YuYueTimeData();
                data.setAlltime(timeday+" "+timehour);
                BusProvider.getInstance().post(data);
                finish();
                break;

        }
    }


}
