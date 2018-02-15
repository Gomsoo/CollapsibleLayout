package com.gomsoo.collapsible;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gomsoo.accordion.R;

/**
 *
 * Created by Moon on 2018-02-14.
 */
public class CollapsibleHeaderView extends CardView implements CollapsibleLayout.HandlerWithMarkView,
        View.OnClickListener {

    private boolean mIsEndMarkStyle;

    private View mHeaderDefaultContainer;
    private FrameLayout mHeaderCustomContainer;

    private Title mTitle;
    private TextView mTitleView;
    private FrameLayout mTitleCustomContainer;

    private View mColorBandView;

    private static class Title {
        private String text;
        private boolean titleBold;
        private boolean titleItalic;
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
        View rootView = inflate(context, R.layout.collapsible_header_view, this);
        rootView.setOnClickListener(this);

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
            mTitle.text = a.getString(R.styleable.CollapsibleHeaderView_title);
        } finally {
            a.recycle();
        }
    }

    private void initializeChildProperties() {
        // TODO
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getExpandedMarkView() {
        if (mIsEndMarkStyle)
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

    private void applyTitle() {
        mHeaderCustomContainer.setVisibility(View.INVISIBLE);
        mTitleCustomContainer.setVisibility(View.INVISIBLE);
        findViewById(R.id.collapsibleHeaderDefaultContainer).setVisibility(View.VISIBLE);
        mTitleView.setVisibility(View.VISIBLE);

        mTitleView.setText(mTitle.text);

        mTitleView.setTypeface(mTitleView.getTypeface(), mTitle.titleBold ?
                (mTitle.titleItalic ? Typeface.BOLD_ITALIC : Typeface.BOLD) :
                (mTitle.titleItalic ? Typeface.ITALIC : Typeface.NORMAL));

        mTitleView.setTextSize(mTitle.unit, mTitle.size);
        mTitleView.setTextColor(mTitle.color);
    }
}
