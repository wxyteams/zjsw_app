package zjsw.myapp.zj.arcgis.delegationadapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.delegationadapter.AdapterDelegate;

import zjsw.myapp.zj.arcgis.R;
import zjsw.myapp.zj.arcgis.bean.Monitor;
import zjsw.myapp.zj.arcgis.monitorActivity;

/**
 * VideoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class MonitorAdapterDelegate extends AdapterDelegate<Monitor, MonitorAdapterDelegate.MonitorViewHolder> {

    @Override
    public boolean isForViewType(Monitor monitor, int position) {
        // 我能处理
        return monitor.type == 0;
    }

    @Override
    public MonitorViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_monitor, parent, false);
        MonitorViewHolder holder = new MonitorViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MonitorViewHolder holder, int position, Monitor monitor) {
//        String str="编号："+equipment.id+"  安装位置："+equipment.position+"  责任人："+equipment.people;
//        holder.tv_Content.setText(str);
//        holder.tv_nextdate.setText(equipment.nextdate);
//        holder.tv_online.setText("在线");
//        holder.tv_type.setText("流量计");
//        Glide.with(holder.itemView.getContext()).load(equipment.imgUrls.get(0)).into(holder.iv_type);
        //设置条目中的点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建一个地图
                Intent intent = new Intent(view.getContext(), monitorActivity.class);

//                TextView friendView=view.findViewById(R.id.tv_friend);
//                Intent intent=new Intent();
//                Intent intent=new Intent(mContext,ChatActivity.class);
//                intent.putExtra("friend",msg.getFriend().toString());
                view.getContext().startActivity(intent);


            }
        });
        //设置条目中具体控件的点击监听
//        viewHolder.iv_head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "你点击了"+msg.getFriend()+"头像", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    static class MonitorViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_type;
        ImageView iv_online;
        ImageView iv_attention;
        TextView tv_Content;
        TextView tv_type;
        TextView tv_online;
        TextView tv_nextdate;

        public MonitorViewHolder(View view) {
            super(view);
//            iv_type = view.findViewById(R.id.iv_type);
//            iv_online = view.findViewById(R.id.iv_online);
//            iv_attention = view.findViewById(R.id.iv_attention);
//            tv_Content = view.findViewById(R.id.tv_content);
//            tv_type = view.findViewById(R.id.tv_type);
//            tv_online = view.findViewById(R.id.tv_online);
//            tv_nextdate = view.findViewById(R.id.tv_nextdate);
        }
    }

}
