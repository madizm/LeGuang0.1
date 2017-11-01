package com.jlu.madizm.leguang;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/10/19.
 */

public class FragmentCommu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_commu,container,false);
        return view;
    }

    public static Fragment newInstance() {
        FragmentCommu fragment = new FragmentCommu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
