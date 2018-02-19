package com.gomsoo.collapsible.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gomsoo.collapsible.R;
import com.gomsoo.collapsible.CollapsibleFragment;
import com.gomsoo.collapsible.CollapsibleLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CollapsibleLayout collapsibleLayout = findViewById(R.id.collapsibleLayout);
        collapsibleLayout.setHandler(findViewById(R.id.collapsibleHeaderView));

        CollapsibleFragment fragment1 = (CollapsibleFragment)
                getSupportFragmentManager().findFragmentById(R.id.collapsibleFragment1);
        fragment1.setContentView(R.layout.accordion_content);
        fragment1.setTitle(R.string.title, true, false);
        fragment1.setTitleSize(20);
        fragment1.setTitleColorByResource(R.color.colorAccent);
        fragment1.setShowColorBand(false);

        CollapsibleFragment fragment2 = (CollapsibleFragment)
                getSupportFragmentManager().findFragmentById(R.id.collapsibleFragment2);
        fragment2.setContentView(R.layout.accordion_content);
        fragment2.setTitleView(R.layout.custom_title);

        CollapsibleFragment fragment3 = (CollapsibleFragment)
                getSupportFragmentManager().findFragmentById(R.id.collapsibleFragment3);
        fragment3.setContentView(R.layout.accordion_content);
        fragment3.setHeaderView(R.layout.custom_header);
        fragment3.setAnimationDuration(600);
    }
}
