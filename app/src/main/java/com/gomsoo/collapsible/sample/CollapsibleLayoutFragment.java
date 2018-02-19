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
 * Created by Gomsoo on 2018-02-19.
 */
public class CollapsibleLayoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collapsible_layout, container, false);

        CollapsibleLayout collapsibleLayout = rootView.findViewById(R.id.collapsibleLayout);
        collapsibleLayout.setHandler(rootView.findViewById(R.id.collapsibleHeaderView));

        return rootView;
    }
}
