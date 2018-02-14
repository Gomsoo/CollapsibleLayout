package com.gomsoo.accordion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gomsoo.accordion.AccordionFragment;
import com.gomsoo.accordion.R;
import com.gomsoo.collapsible.CollapsibleLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CollapsibleLayout collapsibleLayout = findViewById(R.id.collapsibleLayout);
        collapsibleLayout.setHandler(findViewById(R.id.collapsibleLayoutButton));
        collapsibleLayout.setOnCollapseListener(new CollapsibleLayout.OnCollapseListener() {
            @Override
            public void onCollapseStarted(CollapsibleLayout layout) {
                Log.i("GomsooCollapsible", "onCollapsibleStarted");
            }

            @Override
            public void onCollapseEnded(CollapsibleLayout layout) {
                Log.i("GomsooCollapsible", "onCollapsibleEnded");
            }
        });

        collapsibleLayout.setOnExpandListener(new CollapsibleLayout.OnExpandListener() {
            @Override
            public void onExpandStarted(CollapsibleLayout layout) {
                Log.i("GomsooCollapsible", "onExpandStarted");
            }

            @Override
            public void onExpandEnded(CollapsibleLayout layout) {
                Log.i("GomsooCollapsible", "onExpandEnded");
            }
        });

        AccordionFragment fragment1 = (AccordionFragment)
                getSupportFragmentManager().findFragmentById(R.id.accordionFragment1);
        fragment1.setContentView(R.layout.accordion_content);
        fragment1.setTitle(R.string.title, true, false);
        fragment1.setTitleSize(20);
        fragment1.setTitleColorByResource(R.color.colorAccent);
        fragment1.setShowColorBand(false);

        AccordionFragment fragment2 = (AccordionFragment)
                getSupportFragmentManager().findFragmentById(R.id.accordionFragment2);
        fragment2.setContentView(R.layout.accordion_content);
        fragment2.setTitleView(R.layout.custom_title);

        AccordionFragment fragment3 = (AccordionFragment)
                getSupportFragmentManager().findFragmentById(R.id.accordionFragment3);
        fragment3.setContentView(R.layout.accordion_content);
        fragment3.setHeaderView(R.layout.custom_header);
        fragment3.setAnimationDuration(600);
    }
}
