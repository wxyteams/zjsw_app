package zjsw.myapp.zj.arcgis;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TabContentFragment extends Fragment {
    private static final String EXTRA_CONTENT = "content";

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView=null;
        switch (getArguments().getString(EXTRA_CONTENT))
        {

            case "公告":
                contentView= inflater.inflate(R.layout.activity_homeviewpager, null);
//                ((TextView)contentView.findViewById(R.id.textView1)).setText(getArguments().getString(EXTRA_CONTENT));
                break;
            case "新闻":
                contentView= inflater.inflate(R.layout.fragment_tab_content, null);
                ((TextView)contentView.findViewById(R.id.tv_content)).setText(getArguments().getString(EXTRA_CONTENT));
                break;
            case "预警":
                contentView= inflater.inflate(R.layout.fragment_tab_content, null);
                ((TextView)contentView.findViewById(R.id.tv_content)).setText(getArguments().getString(EXTRA_CONTENT));
                break;
        }
        return contentView;
    }
}
