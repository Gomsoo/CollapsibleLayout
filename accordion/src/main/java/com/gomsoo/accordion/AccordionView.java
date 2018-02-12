package com.gomsoo.accordion;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *
 * Created by Moon on 2018-02-10.
 */
public class AccordionView extends ViewGroup {

    private FrameLayout mContentLayout;

    public AccordionView(Context context) {
        this(context, null);
    }

    public AccordionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccordionView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AccordionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initAttributes(context, attrs);
        initChildren(context);
    }

    private void init(Context context) {
    }

    private void initAttributes(Context context, @Nullable AttributeSet attrs) {
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DoubleChartView, 0, 0);
//        try {
//            mBarWidth = a.getDimensionPixelSize(R.styleable.DoubleChartView_barWidth, mBarWidth);
//            mPaddingBetweenItems = a.getDimensionPixelSize(R.styleable.DoubleChartView_paddingBetweenItems, mPaddingBetweenItems);
//            mPaddingBetweenBars = a.getDimensionPixelSize(R.styleable.DoubleChartView_paddingBetweenBars, mPaddingBetweenBars);
//            mPaddingBetweenBarAndText = a.getDimensionPixelSize(R.styleable.DoubleChartView_paddingBetweenBarAndText, mPaddingBetweenBarAndText);
//            mTextPaint.setTextSize(a.getDimension(R.styleable.DoubleChartView_textSize, mTextPaint.getTextSize()));
//            mFirstBarPaint.setColor(a.getColor(R.styleable.DoubleChartView_firstValueColor, mFirstBarPaint.getColor()));
//            mSecondBarPaint.setColor(a.getColor(R.styleable.DoubleChartView_secondValueColor, mSecondBarPaint.getColor()));
//        } finally {
//            a.recycle();
//        }
    }

    private void initChildren(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.accordion_layout, this, false);
        LayoutParams params = rootView.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
            if (params == null) {
                throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
            }
        }
        super.addView(rootView, -1, params);
        mContentLayout = rootView.findViewById(R.id.accordionContentLayout);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        View child = getChildAt(0);
        int minimumWidth = child.getMeasuredWidth();
        int width = resolveSize(minimumWidth, widthMeasureSpec);

        int minimumHeight = child.getMeasuredHeight();
        int height = resolveSize(minimumHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) return;
        if (changed) {
            getChildAt(0).layout(l, t, r, b);
        }
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param child Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void addView(View child) {
        throw new UnsupportedOperationException("addView(View) is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param child Ignored.
     * @param index Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void addView(View child, int index) {
        throw new UnsupportedOperationException("addView(View, int) is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param child Ignored.
     * @param params Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void addView(View child, LayoutParams params) {
        throw new UnsupportedOperationException("addView(View, LayoutParams) "
                + "is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param child Ignored.
     * @param index Ignored.
     * @param params Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void addView(View child, int index, LayoutParams params) {
        throw new UnsupportedOperationException("addView(View, int, LayoutParams) "
                + "is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param child Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void removeView(View child) {
        throw new UnsupportedOperationException("removeView(View) is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @param index Ignored.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void removeViewAt(int index) {
        throw new UnsupportedOperationException("removeViewAt(int) is not supported in Accordion");
    }

    /**
     * This method is not supported and throws an UnsupportedOperationException when called.
     *
     * @throws UnsupportedOperationException Every time this method is invoked.
     */
    @Override
    public void removeAllViews() {
        throw new UnsupportedOperationException("removeAllViews() is not supported in Accordion");
    }

    public void setContentView(@LayoutRes int layoutResID) {
        mContentLayout.removeAllViews();
        inflate(getContext(), layoutResID, mContentLayout);
        requestLayout();
        invalidate();
    }

    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        requestLayout();
        invalidate();
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        requestLayout();
        invalidate();
    }
}
