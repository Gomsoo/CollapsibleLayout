package com.gomsoo.collapsible.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsoo.collapsible.CollapsibleFragment;
import com.gomsoo.collapsible.R;

/**
 *
 * Created by Gomsoo on 2018-02-19.
 */
public class CollapsibleFragmentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collapsible_fragment, container, false);

        CollapsibleFragment fragment1 = (CollapsibleFragment)
                getChildFragmentManager().findFragmentById(R.id.collapsibleFragment1);
        fragment1.setContentView(R.layout.accordion_content);
        fragment1.setTitle(R.string.title, true, false);
        fragment1.setTitleSize(20);
        fragment1.setTitleColorByResource(R.color.colorAccent);
        fragment1.setShowColorBand(false);

        CollapsibleFragment fragment2 = (CollapsibleFragment)
                getChildFragmentManager().findFragmentById(R.id.collapsibleFragment2);
        fragment2.setContentView(R.layout.accordion_content);
        fragment2.setTitleView(R.layout.custom_title);

        CollapsibleFragment fragment3 = (CollapsibleFragment)
                getChildFragmentManager().findFragmentById(R.id.collapsibleFragment3);
        fragment3.setContentView(R.layout.accordion_content);
        fragment3.setHeaderView(R.layout.custom_header);
        fragment3.setAnimationDuration(600);

        return rootView;
    }
}
