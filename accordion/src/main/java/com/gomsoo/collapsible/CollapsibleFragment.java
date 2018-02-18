package com.gomsoo.collapsible;

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

import com.gomsoo.accordion.R;

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
    private long mAnimationDurationInMillis = 300L;

    public CollapsibleFragment() {
        mTitle = new CollapsibleHeaderView.Title();
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
}
