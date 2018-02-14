package com.gomsoo.collapsible;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 *
 * Created by Moon on 2018-02-14.
 */
public class CollapsibleLayout extends FrameLayout {

    private boolean mIsCollapse;
    private long mAnimationDurationInMillis;

    private int mExpandedHeight;
    private int mExpandedParamsHeight;

    private View mMarkView;

    public CollapsibleLayout(@NonNull Context context) {
        super(context);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHandler(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void setExpandedMarkView(View view) {
        mMarkView = view;
    }

    public void setAnimationDuration(long durationInMillis) {
        mAnimationDurationInMillis = durationInMillis;
    }

    public void toggle() {
        toggle(mAnimationDurationInMillis);
    }

    public void toggle(long durationInMillis) {
        if (mIsCollapse) expand(durationInMillis);
        else collapse(durationInMillis);
    }

    public void collapse() {
        collapse(mAnimationDurationInMillis);
    }

    private void collapse(long durationInMillis) {
        if (mExpandedHeight == 0 || mExpandedParamsHeight == 0) {
            mExpandedHeight = getHeight();
            mExpandedParamsHeight = getLayoutParams().height;
        }
        mIsCollapse = true;
        startAnimation(new CollapseAnimation(this, mMarkView, durationInMillis));
    }

    public void expand() {
        expand(mAnimationDurationInMillis);
    }

    private void expand(long durationInMillis) {
        mIsCollapse = false;
        startAnimation(new ExpandAnimation(
                this, mExpandedHeight, mExpandedParamsHeight, mMarkView, durationInMillis));
    }

    private static class CollapseAnimation extends Animation {
        private final View mTargetView;
        private final ViewGroup.LayoutParams mTargetParams;
        private final int mInitialHeight;

        private final View mMarkView;
        private final float mInitialRotation;

        private CollapseAnimation(View targetView, View markView, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mMarkView = markView;
            mInitialRotation = markView.getRotation();
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

            if (mMarkView != null)
                mMarkView.setRotation(
                        mInitialRotation * (1 - interpolatedTime) + -90f * interpolatedTime);

            if (interpolatedTime == 1f) {
                mTargetView.setVisibility(View.GONE);
            }
        }
    }

    private static class ExpandAnimation extends Animation {

        private final View mTargetView;
        private final ViewGroup.LayoutParams mTargetParams;
        private final int mInitialHeight;

        private final int mExpandedHeight;
        private final int mExpandedParamsHeight;

        private final View mMarkView;
        private final float mInitialRotation;

        private ExpandAnimation(View targetView, int expandedHeight, int expandedParamsHeight,
                                View markView, long durationInMillis) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandedHeight = expandedHeight;
            mExpandedParamsHeight = expandedParamsHeight;
            mMarkView = markView;
            mInitialRotation = markView.getRotation();
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

            if (mMarkView != null)
                mMarkView.setRotation(
                        mInitialRotation * (1 - interpolatedTime) + 0 * interpolatedTime);

            if (interpolatedTime == 1f) {
                mTargetParams.height = mExpandedParamsHeight;
                mTargetView.requestLayout();
            }
        }
    }
}
