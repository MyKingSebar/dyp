package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.visitnurseservicesData;
import v1.cn.unionc_pad.view.CircleImageView;

public class DoorNurseAdapter extends BaseAdapter {

    private Context context;
    private GridView mGv;
    private List<GetNurseListData.DataData.DataDataData> serviceDatas;
    private static int ROW_NUMBER = 3;


    public DoorNurseAdapter(Context context, GridView gv) {
        this.context = context;
        this.mGv = gv;
        serviceDatas = new ArrayList<>();


    }
    public void setData(List<GetNurseListData.DataData.DataDataData> datas) {
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
        final GetNurseListData.DataData.DataDataData data = serviceDatas.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_nurselist, null);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nurselist,parent,false);
            holder.img = (CircleImageView) convertView.findViewById(R.id.img);
            if (TextUtils.isEmpty(data.getDoctImagePath())) {
                holder.img.setImageResource(R.drawable.icon_doctor_default);
            } else {
                Glide.with(context)
                        .load(data.getDoctImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.img);

            }
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
            holder.tv_add.setText(data.getClinicName()+"");
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setText(data.getDoctName()+"");
            holder.tv_marjor = (TextView) convertView.findViewById(R.id.tv_marjor);
            holder.tv_marjor.setText(data.getMajor()+"");
            holder.tv_like = (TextView) convertView.findViewById(R.id.tv_like);
            holder.tv_like.setText(data.getEvaluateCount()+"");
            holder.tv_fans = (TextView) convertView.findViewById(R.id.tv_fans);
            holder.tv_fans.setText("粉丝"+data.getAtteCount()+"");
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.tv_comment.setText("评论"+data.getEvaluateCount()+"");
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
        CircleImageView img;
        TextView tv_name;
        TextView tv_add;
        TextView tv_marjor;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_fans;
    }
}
