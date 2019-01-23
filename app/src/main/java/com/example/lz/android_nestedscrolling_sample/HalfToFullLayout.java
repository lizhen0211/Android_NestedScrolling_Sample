package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class HalfToFullLayout extends RelativeLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    private RecyclerView halfToFullRecycleView;
    private int screenHeight;
    private int screenWidth;
    //刨除 状态栏、标题栏 的剩余高度，即view描画高度
    private int drawViewHeight;
    private boolean isInterceptTouchEvent = true;

    public HalfToFullLayout(Context context) {
        super(context);
        init();
    }

    public HalfToFullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HalfToFullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        Log.e("++", "before:" + getScrollY() + " " + dy + " " + drawViewHeight / 2);
        if (dy > 0) {
            if (getScrollY() + dy <= drawViewHeight / 2) {
                scrollBy(0, dy);
                consumed[1] = dy;
                Log.e("++", "up");
            }
        } else {
            if (!halfToFullRecycleView.canScrollVertically(-1)) {
                if (getScrollY() + dy >= 0 && getScrollY() <= drawViewHeight / 2) {
                    scrollBy(0, dy);
                    consumed[1] = dy;
                    Log.e("++", "down");
                }
            }
        }
        Log.e("++", "after:" + getScrollY() + " " + dy + " " + drawViewHeight / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        halfToFullRecycleView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int totalHeight = size + drawViewHeight / 2;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(totalHeight, mode));
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        halfToFullRecycleView = findViewById(R.id.half_to_full_recycle_view);
        //绘制区域高度
        drawViewHeight = screenHeight - getStatusBarHeight();
    }

    public int getStatusBarHeight() {

        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
