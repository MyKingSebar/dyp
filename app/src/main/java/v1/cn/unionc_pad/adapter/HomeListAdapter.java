package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.data.Common;
import v1.cn.unionc_pad.data.SPUtil;
import v1.cn.unionc_pad.model.BaseData;
import v1.cn.unionc_pad.model.HomeListData;
import v1.cn.unionc_pad.network_frame.ConnectHttp;
import v1.cn.unionc_pad.network_frame.UnionAPIPackage;
import v1.cn.unionc_pad.network_frame.core.BaseObserver;
import v1.cn.unionc_pad.ui.LiveListActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private Context context;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();

    public HomeListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeListData.DataData.HomeData> datas) {
        this.datas = datas;
        Log.d("linshi","HomeListAdapter.size"+datas.size());
        notifyDataSetChanged();
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_list, null));
    }


    @Override
    public void onBindViewHolder(final HomeListAdapter.ViewHolder holder, final int position) {
        final HomeListData.DataData.HomeData homeData = datas.get(position);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = datas.get(position).getType();
                String doctorId = datas.get(position).getDoctId() + "";
                String identifier = datas.get(position).getIdentifier() + "";

                if (TextUtils.equals(type, Common.ACTIVITY_PUSH)) {
                    String PushCategory = homeData.getPushCategory();
                    if (TextUtils.equals(PushCategory, Common.PUSH_ACTICITY)) {
//                        Intent intent = new Intent(context, ToDoorWebViewActivity.class);
//                        intent.putExtra("type", 3);
//                        intent.putExtra("activityid", homeData.getActivityId());
//                        Log.d("linshi", "activityid" + homeData.getActivityId());
//                        context.startActivity(intent);
                    } else if (TextUtils.equals(PushCategory, Common.PUSH_BIND)) {
                    } else if (TextUtils.equals(PushCategory, Common.PUSH_LIVE)) {
                        Intent intent = new Intent(context, LiveListActivity.class);
                        context.startActivity(intent);
                    }else if (TextUtils.equals(PushCategory, Common.PUSH_PRESCRIPTION)) {
//                        Intent intent = new Intent(context, PrescriptionActivity.class);
//                        intent.putExtra("prescriptionId", homeData.getContent().getPrescriptionId());
//                        context.startActivity(intent);
                    }
                }
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = datas.get(position).getType();
                String identifier = datas.get(position).getIdentifier() + "";
                String messageid = datas.get(position).getMessageId() + "";
                 if (TextUtils.equals(type, Common.ACTIVITY_PUSH)) {
                    deletemessage(messageid);
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        if (TextUtils.equals(homeData.getType(), Common.ACTIVITY_PUSH)) {

            String PushCategory = homeData.getPushCategory();
            if (TextUtils.equals(PushCategory, Common.PUSH_ACTICITY)) {

                if (TextUtils.isEmpty(homeData.getImagePath())) {

                    holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
                } else {
                    Glide.with(context)
                            .load(homeData.getImagePath2())
                            .placeholder(R.drawable.icon_push).dontAnimate()
                            .error(R.drawable.icon_push)
                            .into(holder.imgMessageAvator);

                }
                holder.tvDescribe.setVisibility(View.GONE);
                holder.tvRole.setVisibility(View.GONE);
                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_LIVE)) {
                if (TextUtils.isEmpty(homeData.getContent().getBanner())) {

                    holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
                } else {
                    Glide.with(context)
                            .load(homeData.getContent().getBanner())
                            .placeholder(R.drawable.icon_push).dontAnimate()
                            .error(R.drawable.icon_push)
                            .into(holder.imgMessageAvator);

                }
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
////            holder.tvEndTime.setText(DateUtils.getStartandEnd(homeData.getStartTime(), homeData.getEndTime()));
//                holder.tvEndTime.setText(homeData.getStartTime() + "-" + homeData.getEndTime());
////            holder.tvEndTime.setText(homeData.getEndTime());
//                holder.tvAddress.setText(homeData.getContent().getStartTime());
//                holder.tvMessageName.setText(homeData.getContent().getLiveTitle() + "  ");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_DOC)) {
//                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
//                holder.tvAddress.setText("已为您的监护人" + homeData.getContent().getElderlyRealName() + "指派医生" + homeData.getContent().getDoctName());
//                holder.tvMessageName.setText("指派医生");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_NUR)) {
                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
//                holder.tvAddress.setText("已为您的监护人" + homeData.getContent().getElderlyRealName() + "指派护士" + homeData.getContent().getDoctName());
//                holder.tvMessageName.setText("指派护士");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_BIND)) {
//                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
//                holder.tvAddress.setText(homeData.getContent().getElderlyRealName() + "申请绑定您为亲情监护人");
//                holder.tvMessageName.setText("申请监护");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_VIDEO)) {
//                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
//                holder.tvAddress.setText(homeData.getContent().getElderlyRealName() + "正在与医院（" + homeData.getContent().getClinicName() + "）的医生（" + homeData.getContent().getDoctName() + "）进行视频问诊，请留意");
//                holder.tvMessageName.setText("视频问诊");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_CALL)) {
//                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
//                holder.tvEndTime.setVisibility(View.INVISIBLE);
//                holder.tvAddress.setVisibility(View.VISIBLE);
//                holder.tvDescribe.setVisibility(View.GONE);
//                holder.tvRole.setVisibility(View.GONE);
//                holder.tvAddress.setText(homeData.getContent().getElderlyRealName() + "正在紧急呼叫，请留意");
//                holder.tvMessageName.setText("一键呼叫");
//                holder.tvTime.setText(homeData.getCreatedTime());
            } else if (TextUtils.equals(PushCategory, Common.PUSH_CALLNURSE)) {
                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
                holder.tvDescribe.setText(homeData.getContent().getElderlyRealName() + "您已预约  "+homeData.getContent().getEndTime()+" 护理上门服务，详情请点击查看>>");
                holder.tvTime.setText(homeData.getCreatedTime());
            }else if (TextUtils.equals(PushCategory, Common.PUSH_PRESCRIPTION)) {
                holder.imgMessageAvator.setImageResource(R.drawable.icon_push);
                holder.tvDescribe.setText( homeData.getContent().getDoctName()+" 医生医生给您开具了电子处方，点击查看>>");
                holder.tvTime.setText(homeData.getCreatedTime());
            }


        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView imgMessageAvator;
        @BindView(R.id.tv_role)
        TextView tvRole;
        @BindView(R.id.describe)
        TextView tvDescribe;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.btnDelete)
        Button btnDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private void deletemessage(String messageid) {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.deletemessage(token, messageid), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {

                if (TextUtils.equals("4000", data.getCode())) {
                } else {
                    Log.e("linshi", "deletemessage" + data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}