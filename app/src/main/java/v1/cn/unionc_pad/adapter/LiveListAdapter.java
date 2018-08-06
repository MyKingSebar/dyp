package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import v1.cn.unionc_pad.model.NetCouldPullData;
import v1.cn.unionc_pad.view.CircleImageView;

/**
 * Created by qy on 2018/2/7.
 */

public class LiveListAdapter extends RecyclerView.Adapter<LiveListAdapter.ViewHolder> {
    private onClickMyTextView onClickMyTextView;
    private Context context;
    private List<NetCouldPullData.DataData.datadatadata> datas = new ArrayList<>();

    public LiveListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NetCouldPullData.DataData.datadatadata> datas) {
        this.datas = datas;
        Log.d("linshi","datas:"+new Gson().toJson(this.datas));
        notifyDataSetChanged();
    }

    @Override
    public LiveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_doclist, null));
    }


    @Override
    public void onBindViewHolder(final LiveListAdapter.ViewHolder holder, final int position) {
        final NetCouldPullData.DataData.datadatadata doctorData = datas.get(position);
        Log.d("linshi","datas:"+new Gson().toJson(doctorData));
            if(TextUtils.isEmpty(doctorData.getBanner())){

                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
            }else{
                Glide.with(context)
                        .load(doctorData.getBanner())
                        .placeholder(R.drawable.icon_doctor_default).dontAnimate()
                        .error(R.drawable.icon_doctor_default)
                        .into(holder.imgMessageAvator);

            }
            holder.tv_name.setText(doctorData.getLiveTitle());
            holder.tv_address.setText(doctorData.getClinicName());
            holder.tv_major.setText(doctorData.getStartTime());
            holder.l_like.setVisibility(View.INVISIBLE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(doctorData.getHttpPullUrl())){

                    onClickMyTextView.myTextViewClick(doctorData.getHttpPullUrl());
                }

//                if (isLogin()) {
//
//                    goIm(livesData);
//                } else {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
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
        @BindView(R.id.l_like)
        LinearLayout l_like;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickMyTextView{
        public void myTextViewClick(String url);
    }
    public void setOnClickMyTextView(onClickMyTextView onClickMyTextView) {
        this.onClickMyTextView = onClickMyTextView;
    }

}
