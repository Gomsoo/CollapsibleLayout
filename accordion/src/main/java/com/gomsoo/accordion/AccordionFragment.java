package com.gomsoo.accordion;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *
 * Created by Moon on 2018-02-12.
 */
public class AccordionFragment extends Fragment {

    private FrameLayout mContentLayout;
    private View mContentView;
    private ViewGroup.LayoutParams mContentViewParams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accordion_layout, container, false);
        mContentLayout = rootView.findViewById(R.id.accordionContentLayout);
        attachContentView();
        return rootView;
    }

    public void setContentView(@LayoutRes int layoutResID) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResID, mContentLayout, false);
        mContentViewParams = null;
        attachContentView();
    }

    public void setContentView(View view) {
        mContentView = view;
        mContentViewParams = null;
        attachContentView();
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentView = view;
        mContentViewParams = params;
        attachContentView();
    }

    private void attachContentView() {
        if (mContentLayout == null || mContentView == null) return;

        mContentLayout.removeAllViews();

        if (mContentViewParams != null)
            mContentLayout.addView(mContentView, mContentViewParams);
        else
            mContentLayout.addView(mContentView);

        mContentLayout.requestLayout();
        mContentLayout.invalidate();
    }
}
