package com.example.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.bbk.example.demo.R;
import com.example.views.ZoomButton;

/**
 * Created by dingjun on 17-12-25.
 */
public class ButtonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_button_layout);
        ZoomButton zoomButton = (ZoomButton)findViewById(R.id.zoom_button);
        zoomButton.init(this);
    }
}
