package com.example.lz.android_nestedscrolling_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author lizhen
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSimpleDemoClick(View view) {
        Intent intent = new Intent(MainActivity.this, SimpleDemoActivity.class);
        startActivity(intent);
    }

    public void onTitleFadeoutClick(View view) {
        Intent intent = new Intent(MainActivity.this, TitleFadeoutActivity.class);
        startActivity(intent);
    }

    public void onHalfToFullRecycleViewClick(View view) {
        Intent intent = new Intent(MainActivity.this, HalfToFullRecycleViewActivity.class);
        startActivity(intent);
    }
}
