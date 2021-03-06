package com.itech.mybabygrowing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.itech.adapter.BabyPagerAdapter;
import com.itech.tab.PagerSlidingTabStrip;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BabyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BabyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BabyPagerAdapter babyPagerAdapter;
    private Activity activity;
    private int currentPage;
    private FloatingActionButton floatingActionButton;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BabyFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static BabyFragment newInstance(String param1, String param2) {
        BabyFragment fragment = new BabyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BabyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_baby, container, false);
        // Inflate the layout for this fragment
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        babyPagerAdapter = new BabyPagerAdapter(getChildFragmentManager(), getActivity());

        viewPager.setAdapter(babyPagerAdapter);

        pagerSlidingTabStrip.setViewPager(viewPager);

        pagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!pagerSlidingTabStrip.isOrientedRight() && position != 0)
                    positionOffset = 1 - positionOffset;
                if (positionOffset > 0.5) positionOffset = 1 - positionOffset;

                floatingActionButton.setScaleX(1 - positionOffset * 2);

                floatingActionButton.setScaleY(1 - positionOffset * 2);
                //  if (positionOffset >0.5) positionOffset=1-positionOffset ;
                //    floatingActionButton.setAlpha(1 - positionOffset*2 );
            }

            @Override
            public void onPageSelected(int position) {
                ((ActionBarActivity) activity).getSupportActionBar().setSubtitle(babyPagerAdapter.changeSubtitle(position));

                currentPage = position;

                switch (position) {
                    case 0:
                        if (((BabyNamesFragment) babyPagerAdapter.getItem(currentPage)).isBoysList()) {
                            floatingActionButton.setIconDrawable(getResources().getDrawable(R.drawable.male));
                        } else {
                            floatingActionButton.setIconDrawable(getResources().getDrawable(R.drawable.female));
                        }
                        break;

                    case 1:
                        floatingActionButton.setIcon(android.R.color.transparent);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((ActionBarActivity) activity).getSupportActionBar().setSubtitle(babyPagerAdapter.changeSubtitle(0));

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        floatingActionButton.setAnimation(scaleAnimation);
        scaleAnimation.setDuration(300);
        scaleAnimation.start();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentPage) {
                    case 0:
                        if (((BabyNamesFragment) babyPagerAdapter.getItem(currentPage)).isBoysList())
                            ((BabyNamesFragment) babyPagerAdapter.getItem(currentPage)).showGirlsList(floatingActionButton);
                        else
                            ((BabyNamesFragment) babyPagerAdapter.getItem(currentPage)).showBoysList(floatingActionButton);

                        break;
                    case 1:

                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {

        this.activity = activity;
        ((ActionBarActivity) activity).getSupportActionBar().setTitle("Bébé");

        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public interface TabChangeTitleListener {

        public String changeSubtitle(int position);
    }
}
