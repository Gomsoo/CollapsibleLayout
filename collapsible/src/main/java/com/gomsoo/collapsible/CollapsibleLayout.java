package com.gomsoo.collapsible;

import android.content.Context;
import android.content.res.TypedArray;
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
 * Created by Gomsoo on 2018-02-14.
 */
public class CollapsibleLayout extends FrameLayout {

    private boolean mIsCollapsed;
    private long mAnimationDurationInMillis = 300L;

    private int mExpandedHeight;
    private int mExpandedParamsHeight;

    private View mMarkView;

    private OnExpandListener mOnExpandListener;
    private OnCollapseListener mOnCollapseListener;

    public interface OnExpandListener {
        void onExpandStarted(CollapsibleLayout layout);
        void onExpandEnded(CollapsibleLayout layout);
    }

    public interface OnCollapseListener {
        void onCollapseStarted(CollapsibleLayout layout);
        void onCollapseEnded(CollapsibleLayout layout);
    }

    public interface HandlerWithMarkView {
        View getExpandedMarkView();
    }

    public CollapsibleLayout(@NonNull Context context) {
        this(context, null);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CollapsibleLayout, defStyleAttr, defStyleRes);
        mAnimationDurationInMillis = a.getInt(R.styleable.CollapsibleLayout_collapsible_animationDuration, 300);
        a.recycle();
    }

    public void setHandler(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        if (view instanceof HandlerWithMarkView)
            setMarkViewForAnimation(((HandlerWithMarkView) view).getExpandedMarkView());
    }

    public void setMarkViewForAnimation(View view) {
        mMarkView = view;
    }

    public void setAnimationDuration(long durationInMillis) {
        mAnimationDurationInMillis = durationInMillis;
    }

    public void toggle() {
        toggle(mAnimationDurationInMillis);
    }

    public void toggle(long durationInMillis) {
        if (mIsCollapsed) expand(durationInMillis);
        else collapse(durationInMillis);
    }

    public void collapse() {
        collapse(mAnimationDurationInMillis);
    }

    public void collapse(long durationInMillis) {
        if (mExpandedHeight == 0 || mExpandedParamsHeight == 0) {
            mExpandedHeight = getHeight();
            mExpandedParamsHeight = getLayoutParams().height;
        }
        mIsCollapsed = true;
        startAnimation(new CollapseAnimation(
                this, mMarkView, durationInMillis, mOnCollapseListener));
    }

    public void expand() {
        expand(mAnimationDurationInMillis);
    }

    public void expand(long durationInMillis) {
        mIsCollapsed = false;
        startAnimation(new ExpandAnimation(this, mExpandedHeight,
                mExpandedParamsHeight, mMarkView, durationInMillis, mOnExpandListener));
    }

    public boolean isCollapsed() {
        return mIsCollapsed;
    }

    public boolean isExpanded() {
        return !mIsCollapsed;
    }

    public void setOnExpandListener(OnExpandListener listener) {
        mOnExpandListener = listener;
    }

    public void setOnCollapseListener(OnCollapseListener listener) {
        mOnCollapseListener = listener;
    }

    private static class CollapseAnimation extends Animation {

        private final CollapsibleLayout mTargetView;
        private final ViewGroup.LayoutParams mTargetParams;
        private final int mInitialHeight;

        private final View mMarkView;
        private final float mInitialRotation;

        private final OnCollapseListener mListener;
        private boolean mIsNotifiedStart;
        private boolean mIsNotifiedEnd;

        private CollapseAnimation(CollapsibleLayout targetView, View markView,
                                  long durationInMillis, OnCollapseListener listener) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mMarkView = markView;
            mInitialRotation = markView == null ? 0 : markView.getRotation();
            setDuration(durationInMillis);
            mListener = listener;
            mIsNotifiedStart = false;
            mIsNotifiedEnd = false;

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

            if (interpolatedTime == 0f) {
                if (mListener != null && !mIsNotifiedStart) {
                    mListener.onCollapseStarted(mTargetView);
                    mIsNotifiedStart = true;
                }
            }

            if (interpolatedTime == 1f) {
                mTargetView.setVisibility(View.GONE);
                if (mListener != null && !mIsNotifiedEnd) {
                    mListener.onCollapseEnded(mTargetView);
                    mIsNotifiedEnd = true;
                }
            }
        }
    }

    private static class ExpandAnimation extends Animation {

        private final CollapsibleLayout mTargetView;
        private final ViewGroup.LayoutParams mTargetParams;
        private final int mInitialHeight;

        private final int mExpandedHeight;
        private final int mExpandedParamsHeight;

        private final View mMarkView;
        private final float mInitialRotation;

        private final OnExpandListener mListener;
        private boolean mIsNotifiedStart;
        private boolean mIsNotifiedEnd;

        private ExpandAnimation(CollapsibleLayout targetView, int expandedHeight, int expandedParamsHeight,
                                View markView, long durationInMillis, OnExpandListener listener) {
            mTargetView = targetView;
            mTargetParams = targetView.getLayoutParams();
            mInitialHeight = targetView.getHeight();
            mExpandedHeight = expandedHeight;
            mExpandedParamsHeight = expandedParamsHeight;
            mMarkView = markView;
            mInitialRotation = markView == null ? 0 : markView.getRotation();
            setDuration(durationInMillis);
            mListener = listener;
            mIsNotifiedStart = false;
            mIsNotifiedEnd = false;

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

            if (interpolatedTime == 0f) {
                if (mListener != null && !mIsNotifiedStart) {
                    mListener.onExpandStarted(mTargetView);
                    mIsNotifiedStart = true;
                }
            }

            if (interpolatedTime == 1f) {
                mTargetParams.height = mExpandedParamsHeight;
                mTargetView.requestLayout();
                if (mListener != null && !mIsNotifiedEnd) {
                    mListener.onExpandEnded(mTargetView);
                    mIsNotifiedEnd = true;
                }
            }
        }
    }
}
