package com.gomsoo.accordion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

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

    private TextView mTitleView;
    private Title mTitle;

    private static class Title {
        private String text;
        private boolean titleBold;
        private boolean titleItalic;
        private int unit = TypedValue.COMPLEX_UNIT_SP;
        private float size = 15;
        private int color = Color.BLACK;
    }

    public AccordionFragment() {
        mTitle = new Title();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accordion_layout, container, false);

        rootView.findViewById(R.id.accordionHeader).setOnClickListener(this);
        mMarkView = rootView.findViewById(R.id.accordionHeaderMarkView);

        mTitleView = rootView.findViewById(R.id.accordionHeaderTitleView);
        applyTitle();

        mContentLayout = rootView.findViewById(R.id.accordionContentLayout);
        attachContentView();
        return rootView;
    }

    public void setContentView(@LayoutRes int layoutResId) {
        mContentView = LayoutInflater.from(getContext()).inflate(layoutResId, mContentLayout, false);
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

    public void setTitle(String title) {
        mTitle.text = title;
        applyTitle();
    }

    public void setTitle(@StringRes int titleResId) {
        mTitle.text = getString(titleResId);
        applyTitle();
    }

    public void setTitleBold(boolean bold) {
        mTitle.titleBold = bold;
        applyTitle();
    }

    public void setTitleItalic(boolean italic) {
        mTitle.titleItalic = italic;
        applyTitle();
    }

    public void setTitle(String title, boolean bold, boolean italic) {
        mTitle.text = title;
        mTitle.titleBold = bold;
        mTitle.titleItalic = italic;
        applyTitle();
    }

    public void setTitle(@StringRes int titleResId, boolean bold, boolean italic) {
        mTitle.text = getString(titleResId);
        mTitle.titleBold = bold;
        mTitle.titleItalic = italic;
        applyTitle();
    }

    public void setTitleSize(float size) {
        mTitle.unit = TypedValue.COMPLEX_UNIT_SP;
        mTitle.size = size;
        applyTitle();
    }

    public void setTitleSize(int unit, float size) {
        mTitle.unit = unit;
        mTitle.size = size;
        applyTitle();
    }

    public void setTitleColor(int color) {
        mTitle.color = color;
        applyTitle();
    }

    public void setTitleColorByResource(@ColorRes int colorResId) {
        if (getContext() == null)
            throw new IllegalArgumentException("context is null");
        mTitle.color = ContextCompat.getColor(getContext(), colorResId);
        applyTitle();
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

    private void applyTitle() {
        if (mTitleView == null) return;
        mTitleView.setText(mTitle.text);

        mTitleView.setTypeface(mTitleView.getTypeface(), mTitle.titleBold ?
                (mTitle.titleItalic ? Typeface.BOLD_ITALIC : Typeface.BOLD) :
                (mTitle.titleItalic ? Typeface.ITALIC : Typeface.NORMAL));

        mTitleView.setTextSize(mTitle.unit, mTitle.size);
        mTitleView.setTextColor(mTitle.color);
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

        private View mExpandedMark;
        private float mInitialRotation;

        private CollapseAnimation(View targetView, View expandedMark, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandedMark = expandedMark;
            mInitialRotation = expandedMark.getRotation();
            setDuration(durationInMillis);

            // targetView의 parent가 invalidate 되지 않아 최초 한 번 이후 applyTransformation이
            // 콜 되지 않는 현상이 있음
            targetView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mTargetParams.height = (int) (mInitialHeight * (1 - interpolatedTime));
            mTargetView.requestLayout();

            mExpandedMark.setRotation(
                    mInitialRotation * (1 - interpolatedTime) + -90f * interpolatedTime);

            if (interpolatedTime == 1f) {
                mTargetView.setVisibility(View.GONE);
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
        private float mInitialRotation;

        private ExpandAnimation(View targetView, int expandedHeight, int expandedParamsHeight,
                                View expandedMark, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandedHeight = expandedHeight;
            mExpandedParamsHeight = expandedParamsHeight;
            mExpandedMark = expandedMark;
            mInitialRotation = expandedMark.getRotation();
            setDuration(durationInMillis);

            // targetView의 parent가 invalidate 되지 않아 최초 한 번 이후 applyTransformation이
            // 콜 되지 않는 현상이 있음
            targetView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mTargetParams.height = (int)
                    (mExpandedHeight * interpolatedTime + mInitialHeight * (1 - interpolatedTime));
            mTargetView.requestLayout();

            mExpandedMark.setRotation(
                    mInitialRotation * (1 - interpolatedTime) + 0 * interpolatedTime);

            if (interpolatedTime == 1f) {
                mTargetParams.height = mExpandedParamsHeight;
                mTargetView.requestLayout();
            }
        }
    }
}
