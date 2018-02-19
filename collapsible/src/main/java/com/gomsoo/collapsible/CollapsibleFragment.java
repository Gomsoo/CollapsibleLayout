package com.gomsoo.collapsible;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.gomsoo.collapsible.CollapsibleHeaderView.BOLD;
import static com.gomsoo.collapsible.CollapsibleHeaderView.ITALIC;

/**
 *
 * Created by Moon on 2018-02-18.
 */
public class CollapsibleFragment extends Fragment {

    private CollapsibleHeaderView mHeaderView;

    private View mCustomHeaderView;
    @LayoutRes private int mCustomHeaderId = -1;

    private View mCustomTitleView;
    @LayoutRes private int mCustomTitleId = -1;

    private CollapsibleHeaderView.Title mTitle;
    private boolean mIsShowColorBand;

    private CollapsibleLayout mContentContainer;
    private View mContentView;
    private long mAnimationDurationInMillis;

    private boolean mIsMarkPositionEnd;
    private Drawable mMark;

    public CollapsibleFragment() {
        mTitle = new CollapsibleHeaderView.Title();
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CollapsibleHeaderView, 0, 0);
        try {
            mIsMarkPositionEnd = a.getInteger(R.styleable.CollapsibleHeaderView_collapsible_markPosition, 0) == 1;
            mIsShowColorBand = a.getBoolean(R.styleable.CollapsibleHeaderView_collapsible_showColorBand, true);
            mTitle.text = a.getText(R.styleable.CollapsibleHeaderView_collapsible_title);
            int styleFlag = a.getInteger(R.styleable.CollapsibleHeaderView_collapsible_titleStyle, 0);
            mTitle.bold = (styleFlag & BOLD) != 0;
            mTitle.italic = (styleFlag & ITALIC) != 0;
            mTitle.size = a.getDimensionPixelSize(R.styleable.CollapsibleHeaderView_collapsible_titleSize, -1);
            mTitle.unit = TypedValue.COMPLEX_UNIT_PX;
            if (mTitle.size == -1) {
                mTitle.size = 15;
                mTitle.unit = TypedValue.COMPLEX_UNIT_SP;
            }
            mTitle.color = a.getColor(R.styleable.CollapsibleHeaderView_collapsible_titleColor, mTitle.color);
            mMark = a.getDrawable(R.styleable.CollapsibleHeaderView_collapsible_mark);

            mCustomHeaderId = a.getResourceId(R.styleable.CollapsibleHeaderView_collapsible_customHeader, -1);
            mCustomTitleId = a.getResourceId(R.styleable.CollapsibleHeaderView_collapsible_customTitle, -1);
        } finally {
            a.recycle();
        }

        a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CollapsibleLayout, 0, 0);
        try {
            mAnimationDurationInMillis = a.getInt(R.styleable.CollapsibleLayout_collapsible_animationDuration, 300);
        } finally {
            a.recycle();
        }

        a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CollapsibleFragment, 0, 0);
        try {
            int contentId = a.getResourceId(R.styleable.CollapsibleFragment_collapsible_content, -1);
            if (contentId != -1)
                setContentView(contentId);
        } finally {
            a.recycle();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collapsible, container, false);
        mHeaderView = rootView.findViewById(R.id.collapsibleFragmentHeader);
        mContentContainer = rootView.findViewById(R.id.collapsibleFragmentContent);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentContainer.setHandler(mHeaderView);

        attachContentView();
        applyAnimationDuration();
        applyMarkPosition();
        applyMark();

        if (mCustomHeaderView != null)
            setCustomHeaderView();
        else if (mCustomTitleView != null)
            setCustomTitleView();
        else
            applyTitle();
    }

    private void attachContentView() {
        if (mContentContainer == null || mContentView == null) return;
        mContentContainer.removeAllViews();
        mContentContainer.addView(mContentView);
    }

    private void setCustomHeaderView() {
        if (mHeaderView == null) return;

        if (mCustomHeaderId != -1)
            mHeaderView.setHeaderView(mCustomHeaderId);
        else if (mCustomHeaderView != null)
            mHeaderView.setHeaderView(mCustomHeaderView);
    }

    private void setCustomTitleView() {
        if (mHeaderView == null) return;

        if (mCustomTitleId != -1)
            mHeaderView.setTitleView(mCustomTitleId);
        else if (mCustomTitleView != null)
            mHeaderView.setTitleView(mCustomTitleView);
    }

    private void applyTitle() {
        if (mHeaderView == null) return;
        mHeaderView.setTitleObject(mTitle);
    }

    private void applyAnimationDuration() {
        if (mContentContainer == null) return;
        mContentContainer.setAnimationDuration(mAnimationDurationInMillis);
    }

    private void applyMarkPosition() {
        if (mHeaderView == null) return;
        mHeaderView.setMarkPositionToEnd(mIsMarkPositionEnd);
    }

    private void applyMark() {
        if (mHeaderView == null || mMark == null) return;
        mHeaderView.setMark(mMark);
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
        mTitle.text = getString(titleResId);
        applyTitle();
    }

    public void setTitle(CharSequence title) {
        mTitle.text = title;
        applyTitle();
    }

    public void setTitleBold(boolean bold) {
        mTitle.bold = bold;
        applyTitle();
    }

    public void setTitleItalic(boolean italic) {
        mTitle.italic = italic;
        applyTitle();
    }

    public void setTitle(CharSequence title, boolean bold, boolean italic) {
        mTitle.text = title;
        mTitle.bold = bold;
        mTitle.italic = italic;
        applyTitle();
    }

    public void setTitle(@StringRes int titleResId, boolean bold, boolean italic) {
        mTitle.text = getString(titleResId);
        mTitle.bold = bold;
        mTitle.italic = italic;
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

    public void setShowColorBand(boolean show) {
        mIsShowColorBand = show;
        applyColorBandVisibility();
    }

    private void applyColorBandVisibility() {
        if (mHeaderView == null) return;
        mHeaderView.setShowColorBand(mIsShowColorBand);
    }

    public void setHeaderView(@LayoutRes int layoutResId) {
        mCustomHeaderId = layoutResId;
        mCustomHeaderView = null;
        setCustomHeaderView();
    }

    public void setHeaderView(View view) {
        mCustomHeaderId = -1;
        mCustomHeaderView = view;
        setCustomHeaderView();
    }

    public void setTitleView(@LayoutRes int layoutResId) {
        mCustomTitleId = layoutResId;
        mCustomTitleView = null;
        setCustomTitleView();
    }

    public void setTitleView(View view) {
        mCustomTitleId = -1;
        mCustomTitleView = view;
        setCustomTitleView();
    }

    public void setAnimationDuration(long durationInMillis) {
        mAnimationDurationInMillis = durationInMillis;
        applyAnimationDuration();
    }

    public boolean isCollapsed() {
        return mContentContainer != null && mContentContainer.isCollapsed();
    }

    public boolean isExpanded() {
        return mContentContainer == null || mContentContainer.isExpanded();
    }

    public void setMarkPositionToEnd(boolean end) {
        mIsMarkPositionEnd = end;
        applyMarkPosition();
    }

    public void setMark(Drawable mark) {
        mMark = mark;
        applyMark();
    }

    public void setMark(@DrawableRes int markResId) {
        mMark = getResources().getDrawable(markResId, null);
        applyMark();
    }
}
