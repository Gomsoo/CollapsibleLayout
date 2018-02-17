package com.gomsoo.collapsible;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsoo.accordion.R;

/**
 *
 * Created by Moon on 2018-02-18.
 */
public class CollapsibleFragment extends Fragment {

    private CollapsibleLayout mContentContainer;
    private View mContentView;

    private CollapsibleHeaderView.Title mTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collapsible, container, false);
        mContentContainer = rootView.findViewById(R.id.collapsibleFragmentContent);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachContentView();
    }

    public void setContentView(@LayoutRes int layoutResId) {
        mContentView = LayoutInflater.from(getContext()).inflate(
                layoutResId, mContentContainer, false);
        attachContentView();
    }

    public void setContentView(View view) {
        mContentView = view;
        attachContentView();
    }

    public void setTitle(@StringRes int titleResId) {
        
    }
}
