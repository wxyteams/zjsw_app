package zjsw.myapp.zj.arcgis.bean;

import java.util.List;

/**
 * News
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-21 20:51:10
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class Monitor {
    public int type = 0; // 0:一张图片；1:三张图片；2:多张图片；3:视频类型
    public String icon = null;//设备类型图标
    public String id = "";//设备编号
    public String position = "";//安装位置

    public String readtime = "";//读取时间
    public String data1 = "";//数据
    public String data2 = "";//数据
    public String data3 = "";//数据
    public String unit = "";//单位
}