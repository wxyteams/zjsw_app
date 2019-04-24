package zjsw.myapp.zj.arcgis.delegationadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;

import zjsw.myapp.zj.arcgis.R;
import zjsw.myapp.zj.arcgis.bean.Tasks;

/**
 * VideoAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-26 17:06:15
 *         Major Function：<b></b>
 *         <p/>
 *
 * @author mender，Modified Date Modify Content:
 */

public class TasksAdapterDelegate extends AdapterDelegate<Tasks, TasksAdapterDelegate.TasksViewHolder> {

    private OnitemClick onitemClick;   //定义点击事件接口
    private OnLongClick onLongClick;  //定义长按事件接口

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    //定义设置长按事件监听的方法
    public void setOnLongClickListener (OnLongClick onLongClick) {
        this.onLongClick = onLongClick;
    }

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position);
    }
    //定义一个长按事件的接口
    public interface OnLongClick {
        void onLongClick(int position);
    }










    @Override
    public boolean isForViewType(Tasks tasks, int position) {
        // 我能处理
        return tasks.type == 0;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks, parent, false);
        TasksViewHolder holder = new TasksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position, Tasks tasks) {
//        String str="编号："+equipment.id+"  安装位置："+equipment.position+"  责任人："+equipment.people;
//        holder.tv_Content.setText(str);
//        holder.tv_nextdate.setText(equipment.nextdate);
//        holder.tv_online.setText("在线");
//        holder.tv_type.setText("流量计");
     // Glide.with(holder.itemView.getContext()).load(tasks.imgUrls.get(0)).into(holder.iv_type);

        holder.tvContent.setText(tasks.content);
        holder.tvName.setText(tasks.name);
        holder.tvprog.setText(tasks.prog);
        holder.pbprogbar.setProgress(tasks.progbar);
        holder.tvDate.setText(tasks.date);
        Glide.with(holder.itemView.getContext()).load(tasks.imgUrls.get(0)).into(holder.ivPic);
        if (tasks.progbar>0) {
            holder.tving.setText("处理中");
            holder.iving.setImageDrawable(holder.iving.getResources().getDrawable(R.drawable.ing));
        } else {
            holder.tving.setText("未处理");
            holder.iving.setImageDrawable(holder.iving.getResources().getDrawable(R.drawable.uning));
        }
    }

    static class TasksViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        ImageView iving;
        TextView tvContent;
        TextView tvName;
        TextView tving;
        TextView tvDate;
        TextView tvprog;
        ProgressBar pbprogbar;

        public TasksViewHolder(View view) {
            super(view);
            ivPic = view.findViewById(R.id.imageView);
            iving = view.findViewById(R.id.iving);
            tvContent = view.findViewById(R.id.content);
            tvName = view.findViewById(R.id.name);
            tving = view.findViewById(R.id.tving);
            tvDate = view.findViewById(R.id.date);
            tvprog = view.findViewById(R.id.prog);
            pbprogbar=view.findViewById(R.id.progbar);
        }
    }

}
