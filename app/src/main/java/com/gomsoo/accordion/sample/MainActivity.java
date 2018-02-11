package com.gomsoo.accordion.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gomsoo.accordion.AccordionView;
import com.gomsoo.accordion.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AccordionView accordion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accordion = findViewById(R.id.accordion);
        accordion.setContentView(R.layout.accordion_content);

        findViewById(R.id.testButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("GomsooAccordion", "TESTETSETSETTEST");
    }
}
