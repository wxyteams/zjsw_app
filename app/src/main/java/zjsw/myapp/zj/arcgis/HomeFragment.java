package zjsw.myapp.zj.arcgis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SliderLayout sliderShow;
    private TabLayout mTabTl;
    private ViewPager mContentVp;
    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("公告");
        tabIndicators.add("新闻");
        tabIndicators.add("预警");

        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getFragmentManager());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderShow = (SliderLayout) view.findViewById(R.id.slider);
        TextSliderView textSliderView1 = new TextSliderView(getContext());
        textSliderView1
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50032/7048.jpg_wh860.jpg");

        TextSliderView textSliderView2 = new TextSliderView(getContext());
        textSliderView2
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50036/2085.jpg_wh860.jpg");

        TextSliderView textSliderView3 = new TextSliderView(getContext());
        textSliderView3
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50037/3102.jpg_wh860.jpg");

        TextSliderView textSliderView4 = new TextSliderView(getContext());
        textSliderView4
                .description("山东中基地理信息科技有限公司")
                .image("http://img95.699pic.com/photo/50049/7759.jpg_wh860.jpg");


        sliderShow.addSlider(textSliderView1);
        sliderShow.addSlider(textSliderView2);
        sliderShow.addSlider(textSliderView3);
        sliderShow.addSlider(textSliderView4);


        mTabTl = (TabLayout) view.findViewById(R.id.home_tl_tab);
        mContentVp = (ViewPager) view.findViewById(R.id.home_vp_content);
        initContent();
        initTab();

        int[] array = new int[]{R.id.tv_h_1, R.id.tv_h_2, R.id.tv_h_3, R.id.tv_h_4, R.id.tv_h_5, R.id.tv_h_6};
        for (int i : array) {
            TextView tv = (TextView) view.findViewById(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //调到拨号界面
//                Uri uri = Uri.parse("tel:15169008955");
//                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    Intent intent = new Intent(arg0.getContext(), monitorActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
