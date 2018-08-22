package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.GetNurseListData;
import v1.cn.unionc_pad.model.visitnurserdiseaseData;
import v1.cn.unionc_pad.view.CircleImageView;

public class DoorDiseaseAdapter extends BaseAdapter {

    private Context context;
    private GridView mGv;
    private List<visitnurserdiseaseData.DataData.DiseaseData> serviceDatas;
    private static int ROW_NUMBER = 3;
    private int num;

    private MyClickListener mListener;

    public void setmListener(MyClickListener mListener) {
        this.mListener = mListener;
    }

    //自定义接口，用于回调按钮点击事件到Activity
    public interface MyClickListener{
        public void clickListener(String id,String name);
    }



    public DoorDiseaseAdapter(Context context, GridView gv) {
        this.context = context;
        this.mGv = gv;
        serviceDatas = new ArrayList<>();


    }
    public void setData(List<visitnurserdiseaseData.DataData.DiseaseData> datas) {
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
        final visitnurserdiseaseData.DataData.DiseaseData data = serviceDatas.get(position);
        final int po=position;
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_nurselist, null);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diseaselist,parent,false);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            holder.cb.setText(data.getDiseaseName());
            convertView.setTag(holder);

            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if(mListener!=null){
                            mListener.clickListener( data.getDiseaseId(),data.getDiseaseName());
                        }
                        num=po;
                        notifyDataSetChanged();
                    }
                }
            });
            if(position==num){
            }else{
                holder.cb.setChecked(false);
            }

        } else {
            holder = (Holder) convertView.getTag();
            if(position==num){
            }else{
                holder.cb.setChecked(false);
            }
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
        CheckBox cb;
    }
}
