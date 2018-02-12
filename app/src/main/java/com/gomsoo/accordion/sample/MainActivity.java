package com.gomsoo.accordion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gomsoo.accordion.AccordionFragment;
import com.gomsoo.accordion.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccordionFragment fragment = (AccordionFragment)
                getSupportFragmentManager().findFragmentById(R.id.accordionFragment);
        fragment.setContentView(R.layout.accordion_content);
        fragment.setTitle(R.string.title, true, false);
        fragment.setTitleSize(20);
        fragment.setTitleColorByResource(R.color.colorAccent);
    }
}
