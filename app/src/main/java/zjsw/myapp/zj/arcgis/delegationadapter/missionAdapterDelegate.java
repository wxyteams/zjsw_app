package zjsw.myapp.zj.arcgis.delegationadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;

import zjsw.myapp.zj.arcgis.R;
import zjsw.myapp.zj.arcgis.ShowItemDetialActivity;
import zjsw.myapp.zj.arcgis.bean.mission;

/**
 * VideoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 * Major Function：<b></b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class missionAdapterDelegate extends AdapterDelegate<mission, missionAdapterDelegate.missionViewHolder> {

    private OnItemClickListener itemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }
    @Override
    public boolean isForViewType(mission mission, int position) {
        // 我能处理
        return mission.type == 0;
    }
    @Override
    public missionViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_mission, parent, false);
        missionViewHolder holder = new missionViewHolder(view,itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(missionViewHolder holder, int position, mission mission) {
        holder.tvContent.setText(mission.content);
        holder.tvName.setText(mission.name);
        holder.tvprog.setText(mission.prog);
        holder.pbprogbar.setProgress(mission.progbar);
        holder.tvDate.setText(mission.date);
        Glide.with(holder.itemView.getContext()).load(mission.imgUrls.get(0)).into(holder.ivPic);
        if (mission.progbar>0) {
            holder.tving.setText("处理中");
            holder.iving.setImageDrawable(holder.iving.getResources().getDrawable(R.drawable.ing));
        } else {
            holder.tving.setText("未处理");
            holder.iving.setImageDrawable(holder.iving.getResources().getDrawable(R.drawable.uning));
        }
//        Glide.with(holder.itemView.getContext()).load(equipment.imgUrls.get(0)).into(holder.iv_type);
    }

    static class missionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private OnItemClickListener mitemClickListener;
        ImageView ivPic;
        ImageView iving;
        TextView tvContent;
        TextView tvName;
        TextView tving;
        TextView tvDate;
        TextView tvprog;
        ProgressBar pbprogbar;

        public missionViewHolder(View view ,OnItemClickListener itemClickListener) {
            super(view);
            this.mitemClickListener = itemClickListener;
            view.setOnClickListener(this);
            ivPic = view.findViewById(R.id.imageView);
            iving = view.findViewById(R.id.iving);
            tvContent = view.findViewById(R.id.content);
            tvName = view.findViewById(R.id.name);
            tving = view.findViewById(R.id.tving);
            tvDate = view.findViewById(R.id.date);
            tvprog = view.findViewById(R.id.prog);
            pbprogbar=view.findViewById(R.id.progbar);
        }
        @Override
        public void onClick(View view){
            if (mitemClickListener != null){
                mitemClickListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

}
