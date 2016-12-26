package com.levup.simpleplayer.views.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.views.MenuInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MenuInteractionListener mListener = null;

    public static final String SOME_VALUE = "SOME_VALUE";

    public static MainFragment newInstance(int value) {
        Bundle args = new Bundle();
        args.putInt(SOME_VALUE, value);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MenuInteractionListener) {
            mListener = (MenuInteractionListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn).setOnClickListener(btnView -> {
            final int value = getArguments().getInt(SOME_VALUE);
            mListener.onMainFragmentEventListener(value);
        });
    }

    public void showText(CharSequence text) {
        final View view = getView();
        if(view == null) return;
        final TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(text);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
