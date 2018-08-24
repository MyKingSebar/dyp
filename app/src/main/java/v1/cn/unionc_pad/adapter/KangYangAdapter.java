package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.YiYangData;
import v1.cn.unionc_pad.ui.door.ChooseHuliServiceActivity;
import v1.cn.unionc_pad.ui.kangyang.kangYangvidoeActivity;
import v1.cn.unionc_pad.view.CircleImageView;

public class KangYangAdapter extends BaseAdapter {

    private Context context;
    private PullToRefreshGridView mGv;
    private List<YiYangData.DataData> serviceDatas;
    private static int ROW_NUMBER = 3;


    public KangYangAdapter(Context context, PullToRefreshGridView gv) {
        this.context = context;
        this.mGv = gv;
        serviceDatas = new ArrayList<>();


    }
    public void setData(List<YiYangData.DataData> datas) {
        this.serviceDatas = datas;
        Log.d("linshi","HomeListAdapter.size"+datas.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (null != serviceDatas) {
            return serviceDatas.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return serviceDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final YiYangData.DataData data = serviceDatas.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_nurselist, null);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kangyangvideolist,parent,false);
            holder.img_message_avator = (ImageView) convertView.findViewById(R.id.img_message_avator);
            if (TextUtils.isEmpty(data.getImgUrl())) {
                holder.img_message_avator.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(data.getImgUrl())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.img_message_avator);

            }
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_address.setText(data.getTitle()+"");
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setText(data.getXiaokaName()+"");
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_price.setText(data.getReadNum()+"");
            holder.ll= (LinearLayout) convertView.findViewById(R.id.ll);
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, kangYangvidoeActivity.class);
                    intent.putExtra("url",data.getVideoUrl());
                    context.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        //高度计算
//        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                mGv.getHeight() / ROW_NUMBER);
//
//        convertView.setLayoutParams(param);
        return convertView;
    }

    class Holder {
        ImageView img_message_avator;
        TextView tv_address;
        TextView tv_name;
        TextView tv_price;
        LinearLayout ll;
    }
}
