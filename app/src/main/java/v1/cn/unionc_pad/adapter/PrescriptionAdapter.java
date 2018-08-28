package v1.cn.unionc_pad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.model.PrescriptionInfoData;

/**
 * Created by qy on 2018/2/7.
 */

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private Context context;
    private List<PrescriptionInfoData.DataData.PrescriptionData.MedicinesData> datas = new ArrayList<>();

    public PrescriptionAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PrescriptionInfoData.DataData.PrescriptionData.MedicinesData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public PrescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_prescription_recycle, null));
    }


    @Override
    public void onBindViewHolder(final PrescriptionAdapter.ViewHolder holder, final int position) {
        final PrescriptionInfoData.DataData.PrescriptionData.MedicinesData livesData = datas.get(position);
        Log.d("linshi", "doctorsdata:" + livesData.toString());

        holder.tv_yongfa.setText(livesData.getPackaging() + "    "+livesData.getUsages());
        holder.tv_yaoming.setText(livesData.getTitle()+"*"+livesData.getNum());
        holder.tv_yaojiaoge.setText(livesData.getSalePrice() + "");

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_yongfa)
        TextView tv_yongfa;
        @BindView(R.id.tv_yaoming)
        TextView tv_yaoming;
        @BindView(R.id.tv_yaojiaoge)
        TextView tv_yaojiaoge;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
