package com.example.lz.android_nestedscrolling_sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class HalfToFullRecycleViewActivity extends Activity {

    private RecyclerView.Adapter mAdapter;
    private int screenHeight;
    private int screenWidth;
    //刨除 状态栏、标题栏 的剩余高度，即view描画高度
    private int drawViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_half_to_full_recycle_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.half_to_full_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleRecyclerViewAdapter(generateData());
        recyclerView.setAdapter(mAdapter);

        DisplayMetrics dm1 = getResources().getDisplayMetrics();
        screenHeight = dm1.heightPixels;
        screenWidth = dm1.widthPixels;

        int statusBarHeight = getStatusBarHeight(this);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.topMargin = (screenHeight - statusBarHeight) / 2;
        //recyclerView.setNestedScrollingEnabled(false);


    }

    private List<String> generateData() {
        List<String> datas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            datas.add("" + (char) i);
        }
        return datas;
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
