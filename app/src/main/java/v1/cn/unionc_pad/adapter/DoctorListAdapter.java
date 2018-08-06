package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import v1.cn.unionc_pad.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private Context context;
    private List<GetLiveDoctorListData.DataData.DataDataData> datas = new ArrayList<>();

    public DoctorListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GetLiveDoctorListData.DataData.DataDataData> datas) {
        this.datas = datas;
        Log.d("linshi","datas:"+new Gson().toJson(this.datas));
        notifyDataSetChanged();
    }

    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_doclist, null));
    }


    @Override
    public void onBindViewHolder(final DoctorListAdapter.ViewHolder holder, final int position) {
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
            holder.tv_role.setText(doctorData.getDepartName());
            holder.tv_identity.setText(doctorData.getProfessName());
            holder.tv_address.setText(doctorData.getClinicName());
            holder.tv_major.setText(doctorData.getMajor());
            holder.tv_like.setText(doctorData.getAtteCount());
            holder.tv_comment.setText(doctorData.getEvaluateCount());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.equals(doctorData.getOnlineState(),"1")){

                    RongCallKit.startSingleCall(context, doctorData.getIdentifier(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                }else {
                    Toast.makeText(context,"医生不在线",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_message_avator)
        CircleImageView imgMessageAvator;

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_role)
        TextView tv_role;
        @BindView(R.id.tv_identity)
        TextView tv_identity;
        @BindView(R.id.tv_address)
        TextView tv_address;
        @BindView(R.id.tv_major)
        TextView tv_major;
        @BindView(R.id.tv_like)
        TextView tv_like;
        @BindView(R.id.tv_comment)
        TextView tv_comment;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
