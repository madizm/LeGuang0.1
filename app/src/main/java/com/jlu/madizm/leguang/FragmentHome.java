package com.jlu.madizm.leguang;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

public class FragmentHome extends Fragment {
    private EditText mSearchText;
    private Button mBtnCart;
    private Button mBtnMsg;
    //private AHBottomNavigationViewPager mViewPager;
    private AHBottomNavigation mBottomNavigation;

    public FragmentHome(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_home,container,false);
        mSearchText = view.findViewById(R.id.edText_search);
        mBtnCart = view.findViewById(R.id.cart);
        mBtnMsg = view.findViewById(R.id.msg);

        //mViewPager = getActivity().findViewById(R.id.view_pager);
        mBottomNavigation = getActivity().findViewById(R.id.bottom_navigation);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(getActivity(), v.getText(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomNavigation.setCurrentItem(2);
            }
        });

        mBtnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomNavigation.setCurrentItem(3);
            }
        });

        return view;
    }

    public static Fragment newInstance() {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
}
