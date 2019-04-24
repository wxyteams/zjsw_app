package zjsw.myapp.zj.arcgis;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.delegationadapter.DelegationAdapter;

import java.util.List;

import butterknife.Bind;
import zjsw.myapp.zj.arcgis.bean.mission;
import zjsw.myapp.zj.arcgis.MainFrameActivity;
import zjsw.myapp.zj.arcgis.delegationadapter.missionAdapterDelegate;
import zjsw.myapp.zj.arcgis.dummy.DummyContent.DummyItem;
import zjsw.myapp.zj.arcgis.util.LocalFileUtils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class missionFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    List<mission> newsList;

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public missionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static missionFragment newInstance(int columnCount) {
        missionFragment fragment = new missionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mission_list, container, false);

        // Set the adapter
        initRecyclerView(view);
        initData();
        return view;
    }

    //重新加载toolbar上的布局menu_home
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.tasks_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    // Button btn =getActivity().findViewById(R.id.tasks_filter);


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tasks_all:
//                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
        return true;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecyclerView(View view) {
        recyclerView =view.findViewById(R.id.recycler_view);
        // 设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // 添加分割线
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        // 设置Adapter
        delegationAdapter = new DelegationAdapter();
        // 添加委托Adapter
        missionAdapterDelegate adapter = new missionAdapterDelegate();
        adapter.setOnItemClickListener(new missionAdapterDelegate.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mission mMission = newsList.get(position);
                String content = mMission.content;
                String name = mMission.name;
                String date = mMission.date;
                String prog = mMission.prog;
                int progbar = mMission.progbar;


                Intent intent = new Intent();
                intent.setClass(getActivity(),ShowItemDetialActivity.class);
                intent.putExtra("content",content);
                intent.putExtra("name",name);
                intent.putExtra("date",date);
                intent.putExtra("prog",prog);
                intent.putExtra("progbar",progbar);

                getActivity().startActivity(intent);
            }
        });
        delegationAdapter.addDelegate(adapter);
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String newsListStr = LocalFileUtils.getStringFormAsset(getContext(), "mission.json");
        newsList = new Gson().fromJson(newsListStr, new TypeToken<List<mission>>() {
        }.getType());
        delegationAdapter.setDataItems(newsList);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
