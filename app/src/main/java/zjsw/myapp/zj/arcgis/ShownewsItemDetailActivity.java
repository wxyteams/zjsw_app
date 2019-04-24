package zjsw.myapp.zj.arcgis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

//import org.codehaus.jackson.map.ser.StdSerializers;


public class ShownewsItemDetailActivity extends AppCompatActivity {


    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_newsitem);
        TextView text = findViewById(R.id.newsTitle);

        content = getIntent().getStringExtra("name");

        text.setText(content);

    }


}














