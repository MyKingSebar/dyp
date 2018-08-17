package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.callkit.RongCallKit;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.GetLiveDoctorListData;
import v1.cn.unionc_pad.ui.DocInfoActivity;
import v1.cn.unionc_pad.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class DoctorListAdapter2 extends RecyclerView.Adapter<DoctorListAdapter2.ViewHolder> {

    private Context context;
    private List<GetLiveDoctorListData.DataData.DataDataData> datas = new ArrayList<>();

    public DoctorListAdapter2(Context context) {
        this.context = context;
    }

    public void setData(List<GetLiveDoctorListData.DataData.DataDataData> datas) {
        this.datas = datas;
        Log.d("linshi","datas:"+new Gson().toJson(this.datas));
        notifyDataSetChanged();
    }

    @Override


    public DoctorListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_doclist3, null));
        return new ViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doclist3,parent,false));
    }


    @Override
    public void onBindViewHolder(final DoctorListAdapter2.ViewHolder holder, final int position) {
        final GetLiveDoctorListData.DataData.DataDataData doctorData = datas.get(position);
        Log.d("linshi","datas:"+new Gson().toJson(doctorData));
            if(TextUtils.isEmpty(doctorData.getDoctImagePath())){

                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            }else{
                Glide.with(context)
                        .load(doctorData.getDoctImagePath())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);

            }
            holder.tv_name.setText(doctorData.getDoctName());
            holder.tv_price.setText(doctorData.getServicePrice()+"元");
            holder.tv_role.setText(doctorData.getDepartName());
            holder.tv_identity.setText(doctorData.getProfessName());
            holder.tv_address.setText(doctorData.getClinicName());
            holder.tv_like.setText("推荐"+doctorData.getAtteCount());
            holder.tv_comment.setText("评论"+doctorData.getEvaluateCount());
        if(TextUtils.equals(doctorData.getOnlineState(),"2")){
            holder.tv_allow.setText("可接入视频问诊");
            holder.tv_allow.setBackgroundResource(R.color.green_00d154);
            holder.tv_isonline.setText("在线");
            holder.tv_isonline.setBackgroundResource(R.drawable.bg_button_online_green);
        }else  if(TextUtils.equals(doctorData.getOnlineState(),"0")){
            holder.tv_allow.setText("未接诊");
            holder.tv_allow.setBackgroundResource(R.color.gray_999996);
            holder.tv_isonline.setText("离线");
            holder.tv_isonline.setBackgroundResource(R.drawable.bg_button_online_gray);
        }else  if(TextUtils.equals(doctorData.getOnlineState(),"1")){
            holder.tv_allow.setText("排队中");
            holder.tv_allow.setBackgroundResource(R.color.yellow_ffcd0c);
            holder.tv_isonline.setText("忙碌");
            holder.tv_isonline.setBackgroundResource(R.drawable.bg_button_online_yellow);
        }

//0离线 1忙碌 2在线
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DocInfoActivity.class);
                intent.putExtra("docdata",doctorData);
                context.startActivity(intent);
//                if(TextUtils.equals(doctorData.getOnlineState(),"2")){
//
//                    RongCallKit.startSingleCall(context, doctorData.getIdentifier(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//                }else  if(TextUtils.equals(doctorData.getOnlineState(),"0")){
//
//                    Toast.makeText(context,"医生不在线",Toast.LENGTH_SHORT).show();
//                }else  if(TextUtils.equals(doctorData.getOnlineState(),"1")){
//                    Toast.makeText(context,"医生忙碌",Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        ImageView imgMessageAvator;

        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_role)
        TextView tv_role;
        @BindView(R.id.tv_identity)
        TextView tv_identity;

        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_like)
        TextView tv_like;
        @BindView(R.id.tv_comment)
        TextView tv_comment;
        @BindView(R.id.tv_allow)
        TextView tv_allow;
        @BindView(R.id.tv_isonline)
        TextView tv_isonline;
        @BindView(R.id.tv_price)
        TextView tv_price;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
