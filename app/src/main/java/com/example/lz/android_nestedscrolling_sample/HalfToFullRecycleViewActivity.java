package com.example.lz.android_nestedscrolling_sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class HalfToFullRecycleViewActivity extends Activity {

    private RecyclerView.Adapter mAdapter;
    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_to_full_recycle_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.half_to_full_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleRecyclerViewAdapter(generateData());
        recyclerView.setAdapter(mAdapter);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        recyclerView.getLayoutParams().height = screenHeight / 2;
        //recyclerView.setNestedScrollingEnabled(false);
    }

    private List<String> generateData() {
        List<String> datas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            datas.add("" + (char) i);
        }
        return datas;
    }
}
