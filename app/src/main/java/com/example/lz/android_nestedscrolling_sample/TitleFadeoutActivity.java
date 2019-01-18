package com.example.lz.android_nestedscrolling_sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhen
 */
public class TitleFadeoutActivity extends Activity {

    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_fadeout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleRecyclerViewAdapter(generateData());
        recyclerView.setAdapter(mAdapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private List<String> generateData() {
        List<String> datas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            datas.add("" + (char) i);
        }
        return datas;
    }
}
