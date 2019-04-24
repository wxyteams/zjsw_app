package zjsw.myapp.zj.arcgis;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import zjsw.myapp.zj.arcgis.dummy.DummyContent;

public class MainFrameActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        //taskFragment.OnListFragmentInteractionListener,
        TasksFragment.OnListFragmentInteractionListener,
        equipmentFragment.OnListFragmentInteractionListener,
        monitorFragment.OnListFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    //private taskFragment taskFragment;
    private TasksFragment tasksFragment;
    private MapFragment mapFragment;
    private equipmentFragment equipmentFragment;
    private monitorFragment monitorFragment;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);

        //侧滑功能
        ButterKnife.bind(this);
        //设置Toolbar
        setToolbar();

        //设置DrawerToggle 开关
        setDrawerToggle();

        //设置监听器
        setListener();

        initFragment();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    private void setListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        //同步DrawerLayout的状态
        mToggle.syncState();
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        // 显示Home的图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task_add:
                Toast.makeText(this, "新建", Toast.LENGTH_SHORT).show();
                break;
            case R.id.task_filter:
                Toast.makeText(this, "我是筛选", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.menu_exit:
//                Toast.makeText(this, "我是退出系统", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.menu_favorite_article:
//                Toast.makeText(this, "我是修改密码", Toast.LENGTH_SHORT).show();
//                break;
        }
        return true;
    }

    //初始化fragment和fragment数组
    private void initFragment() {
        homeFragment = new HomeFragment();
        //taskFragment = new taskFragment();
        tasksFragment = new TasksFragment();
        mapFragment = new MapFragment();
        monitorFragment = new monitorFragment();
        equipmentFragment = new equipmentFragment();
        fragments = new Fragment[]{homeFragment, tasksFragment, mapFragment, monitorFragment, equipmentFragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, homeFragment).show(homeFragment).commit();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //重置到默认不选中图片
            resetToDefaultIcon();

            switch (item.getItemId()) {
                case R.id.action_home: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    item.setIcon(R.drawable.homefill);
                    return true;
                }
                case R.id.action_task: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    item.setIcon(R.drawable.taskfill);
                    return true;
                }
                case R.id.action_global: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    item.setIcon(R.drawable.globalfill);
                    return true;
                }
                case R.id.action_monitor: {
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;
                    }
                    item.setIcon(R.drawable.monitorfill);
                    return true;
                }
                case R.id.action_equipment: {
                    if (lastfragment != 4) {
                        switchFragment(lastfragment, 4);
                        lastfragment = 4;
                    }
                    item.setIcon(R.drawable.equipmentfill);
                    return true;
                }
            }
            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        //隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void resetToDefaultIcon() {
        MenuItem home = bottomNavigationView.getMenu().findItem(R.id.action_home);
        MenuItem task = bottomNavigationView.getMenu().findItem(R.id.action_task);
        MenuItem global = bottomNavigationView.getMenu().findItem(R.id.action_global);
        MenuItem monitor = bottomNavigationView.getMenu().findItem(R.id.action_monitor);
        MenuItem equipment = bottomNavigationView.getMenu().findItem(R.id.action_equipment);
        home.setIcon(R.drawable.home);
        task.setIcon(R.drawable.task);
        global.setIcon(R.drawable.global);
        monitor.setIcon(R.drawable.monitor);
        equipment.setIcon(R.drawable.equipment);
    }

}




