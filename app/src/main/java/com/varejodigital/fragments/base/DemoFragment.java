package com.varejodigital.fragments.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.varejodigital.R;


public class DemoFragment extends BaseFragment {
    private static final String KEY_TITLE = "title";

    public static DemoFragment newInstance(String title) {
        DemoFragment f = new DemoFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }


}
