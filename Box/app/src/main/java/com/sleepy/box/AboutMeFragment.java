package com.sleepy.box;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMeFragment extends Fragment {


    public AboutMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        TextView tvAboutMe = view.findViewById(R.id.id_tv_about_me);
        tvAboutMe.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}
