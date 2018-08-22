package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.ui.door.ServiceInfoActivity;

public class NurseServiceAdapter extends BaseAdapter {

    private Context context;
    private GridView mGv;
    private List<visitnurseservicesData.DataData.ServiceData> serviceDatas;
    private static int ROW_NUMBER = 3;


    public NurseServiceAdapter(Context context, GridView gv) {
        this.context = context;
        this.mGv = gv;
        serviceDatas = new ArrayList<>();


    }
    public void setData(List<visitnurseservicesData.DataData.ServiceData> datas) {
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
        final visitnurseservicesData.DataData.ServiceData data = serviceDatas.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nurseservicelist,parent,false);
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_nurseservicelist, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.img_message_avator);
            if (TextUtils.isEmpty(data.getServiceImage())) {
                holder.iv.setImageResource(R.drawable.icon_push);
            } else {
                Glide.with(context)
                        .load(data.getServiceImage())
                        .placeholder(R.drawable.icon_push).dontAnimate()
                        .error(R.drawable.icon_push)
                        .into(holder.iv);

            }
            holder.tv_isonline = (TextView) convertView.findViewById(R.id.tv_isonline);
            holder.tv_isonline.setText("已有"+data.getBuyCount()+"人购买");
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setText(data.getServiceName()+"");
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_price.setText("￥"+data.getServicePrice()+"/次 ");
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_address.setText(data.getServiceDesc()+"");
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, ServiceInfoActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("data", data);
//                    intent.putExtras(bundle);
                    intent.putExtra("id",data.getServiceId());
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

//        convertView.setLayoutParams(param);
        return convertView;
    }

    class Holder {
        ImageView iv;
        TextView tv_isonline;
        TextView tv_name;
        TextView tv_price;
        TextView tv_address;
        LinearLayout ll;
    }
}
