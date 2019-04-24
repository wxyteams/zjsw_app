package zjsw.myapp.zj.arcgis.delegationadapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;

import zjsw.myapp.zj.arcgis.R;
import zjsw.myapp.zj.arcgis.bean.Equipment;
import zjsw.myapp.zj.arcgis.bean.News;

/**
 * VideoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 * Major Function：<b></b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class EquipmentAdapterDelegate extends AdapterDelegate<Equipment, EquipmentAdapterDelegate.EquipmentViewHolder> {

    @Override
    public boolean isForViewType(Equipment equipment, int position) {
        // 我能处理
        return equipment.type == 0;
    }

    @Override
    public EquipmentViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_equipment, parent, false);
        EquipmentViewHolder holder = new EquipmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EquipmentViewHolder holder, int position, Equipment equipment) {

        holder.tv_Content.setText("编号：" + equipment.id);
        holder.tv_position.setText("安装位置：" + equipment.position);
        holder.tv_people.setText("责任人：" + equipment.people);
        holder.tv_nextdate.setText(equipment.nextdate);


        switch (equipment.eType)
        {
            case 0:
                holder.tv_type.setText("流量计");
                holder.iv_type.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.liuliangji));
                break;
            case 1:
                holder.tv_type.setText("电表");
                holder.iv_type.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.dianbiao));
                break;
            case 2:
                holder.tv_type.setText("压力计");
                holder.iv_type.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.yali));
                break;
            case 3:
                holder.tv_type.setText("视频监控");
                holder.iv_type.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.jiankong));
                break;
        }
        if (equipment.isOnline.equals("在线")) {
            holder.tv_online.setText("在线");
            holder.iv_online.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.online));
        } else {
            holder.tv_online.setText("离线");
            holder.iv_online.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.offline));
        }
        if (equipment.guanzhu == 0) {
            holder.iv_attention.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.shoucang_fill));
        } else {
            holder.iv_attention.setImageDrawable(holder.myView.getResources().getDrawable(R.drawable.shoucang));
        }
//        Glide.with(holder.itemView.getContext()).load(equipment.imgUrls.get(0)).into(holder.iv_type);
    }

    static class EquipmentViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_type;
        ImageView iv_online;
        ImageView iv_attention;
        TextView tv_Content;
        TextView tv_type;
        TextView tv_online;
        TextView tv_nextdate;
        TextView tv_position;
        TextView tv_people;
        View myView;

        public EquipmentViewHolder(View view) {
            super(view);
            iv_type = view.findViewById(R.id.iv_type);
            iv_online = view.findViewById(R.id.iv_online);
            iv_attention = view.findViewById(R.id.iv_attention);
            tv_Content = view.findViewById(R.id.tv_content);
            tv_type = view.findViewById(R.id.tv_type);
            tv_online = view.findViewById(R.id.tv_online);
            tv_nextdate = view.findViewById(R.id.tv_nextdate);
            tv_position = view.findViewById(R.id.tv_position);
            tv_people = view.findViewById(R.id.tv_people);
            myView = view;
        }
    }

}
