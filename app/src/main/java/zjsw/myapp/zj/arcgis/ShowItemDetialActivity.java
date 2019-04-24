package zjsw.myapp.zj.arcgis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

//import org.codehaus.jackson.map.ser.StdSerializers;

import zjsw.myapp.zj.arcgis.bean.mission;


public class ShowItemDetialActivity extends AppCompatActivity {

    private String content;
    private String name;
    private String prog;
    private String progbar;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_item);
        Toolbar toolbar = findViewById(R.id.toolbar11);
        toolbar.setTitle("任务详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView text1 = findViewById(R.id.show1);
        TextView text2 = findViewById(R.id.show2);
        TextView text3 = findViewById(R.id.show3);
        TextView text4 = findViewById(R.id.show4);
        TextView text5 = findViewById(R.id.show5);
        content = getIntent().getStringExtra("content");
        name = getIntent().getStringExtra("name");
        prog = getIntent().getStringExtra("prog");
        progbar = getIntent().getStringExtra("progbar");
        date =  getIntent().getStringExtra("date");
        text1.setText(content);
        text2.setText(name);
        text3.setText(prog);
        text4.setText(progbar+"%");
        text5.setText(date);
    }
    //重新加载toolbar上的布局menu_home


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        //   return super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.over:
                Toast.makeText(this, "详情", Toast.LENGTH_SHORT).show();

                break;
            default:
                // return super.onOptionsItemSelected(item);
        }
        return true;
    }


}














