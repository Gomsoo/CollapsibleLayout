package com.gomsoo.collapsible.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsoo.collapsible.CollapsibleLayout;
import com.gomsoo.collapsible.R;

/**
 *
 * Created by Gomsoo on 2018-02-20.
 */
public class CustomizingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customizing, container, false);

        CollapsibleLayout layoutCustomizingTitle = rootView.findViewById(R.id.customizingTitleLayout);
        layoutCustomizingTitle.setHandler(rootView.findViewById(R.id.customizingTitleHeader));

        CollapsibleLayout layoutCustomizingHeader = rootView.findViewById(R.id.customizingHeaderLayout);
        layoutCustomizingHeader.setHandler(rootView.findViewById(R.id.customizingHeaderHeader));

        return rootView;
    }
}
