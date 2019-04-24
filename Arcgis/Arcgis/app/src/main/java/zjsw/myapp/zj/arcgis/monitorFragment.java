package zjsw.myapp.zj.arcgis;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.delegationadapter.DelegationAdapter;

import zjsw.myapp.zj.arcgis.bean.Monitor;
import zjsw.myapp.zj.arcgis.delegationadapter.MonitorAdapterDelegate;
import zjsw.myapp.zj.arcgis.dummy.DummyContent;
import zjsw.myapp.zj.arcgis.dummy.DummyContent.DummyItem;
import zjsw.myapp.zj.arcgis.util.LocalFileUtils;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class monitorFragment extends Fragment {

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
    public monitorFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static monitorFragment newInstance(int columnCount) {
        monitorFragment fragment = new monitorFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor_list, container, false);

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
        delegationAdapter.addDelegate(new MonitorAdapterDelegate());
        recyclerView.setAdapter(delegationAdapter);
    }

    private void initData() {
        String monitorListStr = LocalFileUtils.getStringFormAsset(getContext(), "monitor.json");
        List<Monitor> monitorList = new Gson().fromJson(monitorListStr, new TypeToken<List<Monitor>>() {
        }.getType());
        delegationAdapter.setDataItems(monitorList);
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
     * <p/>-++-+
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
