package com.gomsoo.accordion;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *
 * Created by Moon on 2018-02-10.
 */
public class Accordion extends ViewGroup {

    private FrameLayout mContentLayout;

    public Accordion(Context context) {
        this(context, null);
    }

    public Accordion(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Accordion(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Accordion(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initAttributes(context, attrs);
        initChildren(context);
    }

    private void init(Context context) {
//        mTextPaint = new Paint();
//        mTextPaint.setColor(ContextCompat.getColor(context, R.color.black));
//        mTextPaint.setTextSize(getResources().getDimension(R.dimen.double_chart_view_default_text_size));
//        mTextPaint.setTextAlign(Paint.Align.CENTER);
//
//        mFirstBarPaint = new Paint();
//        mFirstBarPaint.setColor(ContextCompat.getColor(context, R.color.incomeBlue));
//
//        mSecondBarPaint = new Paint();
//        mSecondBarPaint.setColor(ContextCompat.getColor(context, R.color.expenditureRed));
//
//        mBarWidth = getResources().getDimensionPixelSize(R.dimen.double_chart_view_default_bar_width);
//        mPaddingBetweenItems = getResources().getDimensionPixelSize(R.dimen.double_chart_view_default_padding_between_item);
//        mPaddingBetweenBars = getResources().getDimensionPixelSize(R.dimen.double_chart_view_default_padding_in_item);
//        mPaddingBetweenBarAndText = getResources().getDimensionPixelSize(R.dimen.double_chart_view_default_padding_between_bar_and_text);
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
        View rootView = inflate(context, R.layout.accordion, null);
        super.addView(rootView);
        mContentLayout = rootView.findViewById(R.id.accordionContentLayout);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int minimumWidth = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
//        if (mItemList != null) {
//            for (int i = mItemList.size() - 1; i >= 0; i--) {
//                Item item = mItemList.get(i);
//                minimumWidth += Math.max(mTextPaint.measureText(item.name), (mBarWidth * 2) + mPaddingBetweenBars);
//                if (i != mItemList.size() - 1) minimumWidth += mPaddingBetweenItems;
//
//                if (i == mItemList.size() - 1 || minimumWidth < (widthSpecSize - getPaddingLeft() - getPaddingRight())) {
//                    mIndexToDraw = i;
//                    mWidthOfDataToDraw = minimumWidth;
//                    mMaximumValue = Math.max(mMaximumValue, Math.max(item.firstValue, item.secondValue));
//                }
//            }
//        }
//        int width = resolveSize(minimumWidth, widthMeasureSpec);
//
//        int minimumHeight = getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight() + (int) mTextPaint.getTextSize();
//        int height = resolveSize(minimumHeight, heightMeasureSpec);
//
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
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
}
