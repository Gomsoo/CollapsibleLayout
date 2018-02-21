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
        return inflater.inflate(R.layout.fragment_collapsible_fragment, container, false);
    }
}
