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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class monitorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout mTabTl;
    private ViewPager mContentVp;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        setToolBar();

        mTabTl = (TabLayout) findViewById(R.id.monitor_tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.monitor_vp_content);
        initContent();
        initTab();
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        tabIndicators.add("实时监测");
        tabIndicators.add("预警信息");
        tabIndicators.add("养护记录");

        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
