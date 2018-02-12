package com.gomsoo.accordion;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 *
 * Created by Moon on 2018-02-12.
 */
public class AccordionFragment extends Fragment implements View.OnClickListener {

    private FrameLayout mContentLayout;
    private int mExpandedHeight;
    private int mExpandedParamsHeight;

    private View mMarkView;

    private View mContentView;
    private ViewGroup.LayoutParams mContentViewParams;

    private boolean mIsExpand = true;
    private long mAnimationDurationInMillis = 300L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accordion_layout, container, false);

        rootView.findViewById(R.id.accordionHeader).setOnClickListener(this);
        mMarkView = rootView.findViewById(R.id.accordionHeaderMarkView);

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

        if (mContentViewParams == null)
            mContentLayout.addView(mContentView);
        else
            mContentLayout.addView(mContentView, mContentViewParams);

        mContentLayout.requestLayout();
        mContentLayout.invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.accordionHeader) {
            if (mIsExpand) collapse(mAnimationDurationInMillis);
            else expand(mAnimationDurationInMillis);
        }
    }

    private void expand(long durationInMillis) {
        mIsExpand = true;
        mContentLayout.startAnimation(new ExpandAnimation(mContentLayout,
                mExpandedHeight, mExpandedParamsHeight, mMarkView, durationInMillis));
    }

    private void collapse(long durationInMillis) {
        if (mExpandedHeight == 0 || mExpandedParamsHeight == 0) {
            mExpandedHeight = mContentLayout.getHeight();
            mExpandedParamsHeight = mContentLayout.getLayoutParams().height;
        }
        mIsExpand = false;
        mContentLayout.startAnimation(
                new CollapseAnimation(mContentLayout, mMarkView, durationInMillis));
    }

    private static class CollapseAnimation extends Animation {

        private View mTargetView;
        private ViewGroup.LayoutParams mTargetParams;
        private int mInitialHeight;

        private View mExpandMark;

        private CollapseAnimation(View targetView, View expandMark, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandMark = expandMark;
            setDuration(durationInMillis);

            // targetView의 parent가 invalidate 되지 않아 최초 한 번 이후 applyTransformation이
            // 콜 되지 않는 현상이 있음
            targetView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            if (interpolatedTime == 0f) {
                mTargetView.setVisibility(View.VISIBLE);
            } else if (interpolatedTime == 1f) {
                mTargetView.setVisibility(View.GONE);
            } else {
                mTargetParams.height = (int) (mInitialHeight * (1 - interpolatedTime));
                mTargetView.requestLayout();
            }
        }
    }

    private static class ExpandAnimation extends Animation {

        private View mTargetView;
        private ViewGroup.LayoutParams mTargetParams;
        private int mInitialHeight;

        private int mExpandedHeight;
        private int mExpandedParamsHeight;

        private View mExpandedMark;

        private ExpandAnimation(View targetView, int expandedHeight, int expandedParamsHeight,
                                View expandedMark, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandedHeight = expandedHeight;
            mExpandedParamsHeight = expandedParamsHeight;
            mExpandedMark = expandedMark;
            setDuration(durationInMillis);

            // targetView의 parent가 invalidate 되지 않아 최초 한 번 이후 applyTransformation이
            // 콜 되지 않는 현상이 있음
            targetView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            if (interpolatedTime == 0f) {
                mTargetView.setVisibility(View.VISIBLE);
            } else if (interpolatedTime == 1f) {
                mTargetParams.height = mExpandedParamsHeight;
                mTargetView.requestLayout();
            } else {
                mTargetParams.height = (int)
                        (mExpandedHeight * interpolatedTime + mInitialHeight * (1 - interpolatedTime));
                mTargetView.requestLayout();
            }
        }
    }
}
