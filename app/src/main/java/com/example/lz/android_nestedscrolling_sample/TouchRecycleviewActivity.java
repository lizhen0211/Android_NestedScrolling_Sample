package com.example.lz.android_nestedscrolling_sample;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhen
 */
public class TouchRecycleviewActivity extends Activity {

    private RecyclerView.Adapter mAdapter;
    private int screenHeight;
    private int screenWidth;
    //刨除 状态栏、标题栏 的剩余高度，即view描画高度
    private int drawViewHeight;
    private RelativeLayout touchTitleLayout;
    private RelativeLayout titleLayout;
    private RelativeLayout touchLayout;
    private RecyclerView recyclerView;
    private GestureDetectorCompat mDetector;
    private float lastY = 0;
    private ValueAnimator flingAnimator = new ValueAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_touch_recycleview);
        recyclerView = (RecyclerView) findViewById(R.id.touch_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new SimpleRecyclerViewAdapter(generateData());
        recyclerView.setAdapter(mAdapter);

        DisplayMetrics dm1 = getResources().getDisplayMetrics();
        screenHeight = dm1.heightPixels;
        screenWidth = dm1.widthPixels;

        int statusBarHeight = getStatusBarHeight(this);
        drawViewHeight = screenHeight - statusBarHeight;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.height = drawViewHeight / 2;

        mDetector = new GestureDetectorCompat(this, onGestureListener);

        touchTitleLayout = (RelativeLayout) findViewById(R.id.touch_title_layout);
        touchTitleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float detaY = lastY - event.getY();
                        Log.e(TouchRecycleviewActivity.class.getSimpleName(), lastY + " " + event.getY() + " " + detaY);

                        float recycleHeightAfterMove = layoutParams.height + detaY;
                        //recycleHeightAfterMove 不能为负数
                        if (recyclerView.getY() >= 0 && recycleHeightAfterMove >= 0 && recycleHeightAfterMove <= drawViewHeight - touchTitleLayout.getHeight()) {
                            layoutParams.height += detaY;
                            recyclerView.setLayoutParams(layoutParams);
                            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "enter");
                        }

                        //--------- title 滑动 start ---------
                        float y = titleLayout.getY();
                        float moveDownEdge = ((RelativeLayout.LayoutParams) titleLayout.getLayoutParams()).topMargin;
                        float moveUpEdge = -(((RelativeLayout.LayoutParams) titleLayout.getLayoutParams()).height);
                        //detaY < 0 向下 detaY > 0 向上
                        if (detaY < 0 && y <= moveDownEdge || detaY > 0 && y >= moveUpEdge) {
                            titleLayout.setY(y -= detaY);
                            //Log.e(TouchRecycleviewActivity.class.getSimpleName(), detaY + " " + moveDownEdge + " " + moveUpEdge + " " + y + "");
                        }
                        //--------- title 滑动 end ---------
                        break;
                    case MotionEvent.ACTION_UP:
                        if (lastY - event.getY() < 0) {//向下滑动
                            if (recyclerView.getLayoutParams().height >= drawViewHeight / 2) {
                                runAnim(layoutParams, layoutParams.height, drawViewHeight / 2);
                            } else {
                                runAnim(layoutParams, layoutParams.height, 0);
                            }
                        } else {//向上滑动
                            if (recyclerView.getLayoutParams().height >= drawViewHeight / 2) {
                                runAnim(layoutParams, layoutParams.height, drawViewHeight);
                            } else {
                                runAnim(layoutParams, layoutParams.height, drawViewHeight / 2);
                            }
                        }
                        break;
                }
                return true;
            }
        });
        touchLayout = (RelativeLayout) findViewById(R.id.touch_layout);
        titleLayout = (RelativeLayout) findViewById(R.id.title_layout);

        flingAnimator.setInterpolator(new DecelerateInterpolator());
    }

    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onDown");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onScroll " + (e2.getY() - e1.getY() + " " + distanceX));
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e(TouchRecycleviewActivity.class.getSimpleName(), "onFling");
            float detaY = e2.getY() - e1.getY();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
            if (detaY < 0) {//向上滑动
                if (recyclerView.getLayoutParams().height >= drawViewHeight / 2) {
                    runAnim(layoutParams, layoutParams.height, drawViewHeight);
                } else {
                    runAnim(layoutParams, layoutParams.height, drawViewHeight / 2);
                }
            } else {//向下滑动
                if (recyclerView.getLayoutParams().height >= drawViewHeight / 2) {
                    runAnim(layoutParams, layoutParams.height, drawViewHeight / 2);
                } else {
                    runAnim(layoutParams, layoutParams.height, 0);
                }
            }
            return true;
        }
    };

    private void runAnim(final RelativeLayout.LayoutParams layoutParams, int startHeight, int endHeight) {
        if (!flingAnimator.isStarted()) {
            flingAnimator.setIntValues(startHeight, endHeight);
            flingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    layoutParams.height = (Integer) animation.getAnimatedValue();
                    recyclerView.setLayoutParams(layoutParams);
                }
            });
            flingAnimator.setDuration(500);
            flingAnimator.start();
        }
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
