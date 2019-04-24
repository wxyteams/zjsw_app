package zjsw.myapp.zj.arcgis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class homeviewpagerActivity extends AppCompatActivity {

    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeviewpager);
        textview.setClickable(true);
        textview =findViewById(R.id.textView5);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(homeviewpagerActivity.this, ShownewsItemDetailActivity.class);
                intent.putExtra("name",textview.getText());


                startActivity(intent);
            }
        });
    }
}
