package zjsw.myapp.zj.arcgis;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SliderLayout sliderShow ;
    private TabLayout mTabTl;
    private ViewPager mContentVp;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sliderShow = (SliderLayout) this.<View>findViewById(R.id.slider);
        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView1
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50032/7048.jpg_wh860.jpg");

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50036/2085.jpg_wh860.jpg");

        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50037/3102.jpg_wh860.jpg");

        TextSliderView textSliderView4 = new TextSliderView(this);
        textSliderView4
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50049/7759.jpg_wh860.jpg");


        sliderShow.addSlider(textSliderView1);
        sliderShow.addSlider(textSliderView2);
        sliderShow.addSlider(textSliderView3);
        sliderShow.addSlider(textSliderView4);


        mTabTl = (TabLayout) findViewById(R.id.home_tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.home_vp_content);
        initContent();
        initTab();
    }
    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }
    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
        tabIndicators.add("公告");
        tabIndicators.add("新闻");
        tabIndicators.add("预警");

        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {
        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }
        @Override
        public int getCount() {
            return tabIndicators.size();
        }
        @Override

        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

    }
}
