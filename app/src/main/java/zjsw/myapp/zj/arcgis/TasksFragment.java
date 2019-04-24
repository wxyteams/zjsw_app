package zjsw.myapp.zj.arcgis;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.delegationadapter.DelegationAdapter;

import zjsw.myapp.zj.arcgis.bean.Tasks;
import zjsw.myapp.zj.arcgis.delegationadapter.TasksAdapterDelegate;
import zjsw.myapp.zj.arcgis.dummy.DummyContent;
import zjsw.myapp.zj.arcgis.dummy.DummyContent.DummyItem;
import zjsw.myapp.zj.arcgis.util.LocalFileUtils;

import java.lang.reflect.Method;
import java.util.List;



/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TasksFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    DelegationAdapter delegationAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TasksFragment newInstance(int columnCount) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);//为支持更改主页的toolbar



        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override////为支持更改主页的toolbar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //这里设置另外的menu
        menu.clear();
        inflater.inflate(R.menu.tasks_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        //通过反射让menu的图标可见
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }

        //这一行不能忘，否则看不到图标
        //拿到ActionBar后，可以进行设置
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }          ////为支持更改主页的toolbar



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_list, container, false);

        // Set the adapter
        initRecyclerView(view);
        initData();
        return view;
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
        delegationAdapter.addDelegate(new TasksAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String tasksListStr = LocalFileUtils.getStringFormAsset(getContext(), "tasks.json");
        List<Tasks> tasksList = new Gson().fromJson(tasksListStr, new TypeToken<List<Tasks>>() {
        }.getType());
        delegationAdapter.setDataItems(tasksList);
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
