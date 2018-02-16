package com.gomsoo.collapsible;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gomsoo.accordion.R;

/**
 *
 * Created by Moon on 2018-02-14.
 */
public class CollapsibleHeaderView extends CardView implements CollapsibleLayout.HandlerWithMarkView {

    private boolean mIsMarkPositionEnd;

    private View mHeaderDefaultContainer;
    private FrameLayout mHeaderCustomContainer;
    private View mCustomHeaderView;

    private Title mTitle;
    private TextView mTitleView;
    private FrameLayout mTitleCustomContainer;
    private View mCustomTitleView;

    private View mColorBandView;
    private boolean mIsShowColorBand;

    private static class Title {
        private String text;
        private boolean bold;
        private boolean italic;
        private int unit = TypedValue.COMPLEX_UNIT_SP;
        private float size = 15;
        private int color = Color.BLACK;
    }

    public CollapsibleHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public CollapsibleHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleHeaderView(@NonNull Context context,
                                 @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initializeChildren(context);
        initializeProperties(context, attrs, defStyleAttr);
        initializeChildProperties();
    }

    private void initializeChildren(@NonNull Context context) {
        removeAllViews();
        inflate(context, R.layout.collapsible_header_view, this);

        mHeaderDefaultContainer = findViewById(R.id.collapsibleHeaderDefaultContainer);
        mHeaderCustomContainer = findViewById(R.id.collapsibleHeaderCustomContainer);
        mTitleView = findViewById(R.id.collapsibleHeaderTitleView);
        mTitleCustomContainer = findViewById(R.id.collapsibleHeaderTitleCustomContainer);
        mColorBandView = findViewById(R.id.collapsibleHeaderColorBand);
    }

    private void initializeProperties(@NonNull Context context,
                                      @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CollapsibleHeaderView, defStyleAttr, 0);
        try {
            mIsMarkPositionEnd = a.getInteger(R.styleable.CollapsibleHeaderView_markPosition, 0) == 1;
            mTitle.text = a.getString(R.styleable.CollapsibleHeaderView_title);
        } finally {
            a.recycle();
        }
    }

    private void initializeChildProperties() {
        if (mCustomHeaderView != null)
            attachCustomHeaderView();
        else if (mCustomTitleView != null)
            attachCustomTitleView();
        else
            applyTitle();

        applyColorBandVisibility();
        applyMarkPosition();
    }

    @Override
    public View getExpandedMarkView() {
        if (mIsMarkPositionEnd)
            return findViewById(R.id.collapsibleHeaderEndMarkView);
        return findViewById(R.id.collapsibleHeaderStartMarkView);
    }

    public void setTitle(String title) {
        mTitle.text = title;
        applyTitle();
    }

    public void setTitle(@StringRes int stringResId) {
        mTitle.text = getContext().getString(stringResId);
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

    public void setTitle(String title, boolean bold, boolean italic) {
        mTitle.text = title;
        mTitle.bold = bold;
        mTitle.italic = italic;
        applyTitle();
    }

    public void setTitle(@StringRes int titleResId, boolean bold, boolean italic) {
        mTitle.text = getContext().getString(titleResId);
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
        mTitle.color = ContextCompat.getColor(getContext(), colorResId);
        applyTitle();
    }

    public void setShowColorBand(boolean show) {
        mIsShowColorBand = show;
        applyColorBandVisibility();
    }

    public void setHeaderView(@LayoutRes int layoutResId) {
        mCustomHeaderView = LayoutInflater.from(getContext())
                .inflate(layoutResId, mHeaderCustomContainer, false);
        attachCustomHeaderView();
    }

    public void setHeaderView(View view) {
        mCustomHeaderView = view;
        attachCustomHeaderView();
    }

    public void setTitleView(@LayoutRes int layoutResId) {
        mCustomTitleView = LayoutInflater.from(getContext())
                .inflate(layoutResId, mTitleCustomContainer, false);
        attachCustomTitleView();
    }

    public void setTitleView(View view) {
        mCustomTitleView = view;
        attachCustomTitleView();
    }

    public void setMarkPositionEnd(boolean end) {
        mIsMarkPositionEnd = end;
        applyMarkPosition();
    }

    private void attachCustomHeaderView() {
        if (mHeaderCustomContainer == null || mCustomHeaderView == null) return;

        mHeaderCustomContainer.addView(mCustomHeaderView);
        mHeaderDefaultContainer.setVisibility(View.INVISIBLE);
        mHeaderCustomContainer.setVisibility(View.VISIBLE);
    }

    private void attachCustomTitleView() {
        if (mTitleCustomContainer == null || mCustomTitleView == null) return;

        mTitleCustomContainer.addView(mCustomTitleView);
        mHeaderDefaultContainer.setVisibility(View.VISIBLE);
        mHeaderCustomContainer.setVisibility(View.INVISIBLE);
        mTitleView.setVisibility(View.INVISIBLE);
        mTitleCustomContainer.setVisibility(View.VISIBLE);
    }

    private void applyTitle() {
        mHeaderCustomContainer.setVisibility(View.INVISIBLE);
        mTitleCustomContainer.setVisibility(View.INVISIBLE);
        findViewById(R.id.collapsibleHeaderDefaultContainer).setVisibility(View.VISIBLE);
        mTitleView.setVisibility(View.VISIBLE);

        mTitleView.setText(mTitle.text);

        mTitleView.setTypeface(mTitleView.getTypeface(), mTitle.bold ?
                (mTitle.italic ? Typeface.BOLD_ITALIC : Typeface.BOLD) :
                (mTitle.italic ? Typeface.ITALIC : Typeface.NORMAL));

        mTitleView.setTextSize(mTitle.unit, mTitle.size);
        mTitleView.setTextColor(mTitle.color);
    }

    private void applyColorBandVisibility() {
        if (mColorBandView == null) return;
        mColorBandView.setVisibility(mIsShowColorBand ? View.VISIBLE : View.GONE);
    }

    private void applyMarkPosition() {
        findViewById(R.id.collapsibleHeaderStartMarkView)
                .setVisibility(mIsMarkPositionEnd ? GONE : VISIBLE);
        findViewById(R.id.collapsibleHeaderEndMarkView)
                .setVisibility(mIsMarkPositionEnd ? VISIBLE : GONE);
    }
}
