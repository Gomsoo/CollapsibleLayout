package com.gomsoo.collapsible;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.gomsoo.accordion.R;

/**
 *
 * Created by Moon on 2018-02-14.
 */
public class CollapsibleHeaderView extends CardView implements CollapsibleLayout.MarkView {

    private boolean mIsEndMarkStyle;

    public CollapsibleHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public CollapsibleHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        removeAllViews();
        inflate(context, R.layout.collapsible_header_view, this);
    }

    @Override
    public View getExpandedMarkView() {
        if (mIsEndMarkStyle)
            return findViewById(R.id.collapsibleHeaderEndMarkView);
        return findViewById(R.id.collapsibleHeaderStartMarkView);
    }
}
