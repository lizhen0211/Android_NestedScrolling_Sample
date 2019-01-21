package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class HalfToFullLayout extends RelativeLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    private RecyclerView halfToFullRecycleView;
    private int screenHeight;
    private int screenWidth;
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
        /*if (!halfToFullRecycleView.canScrollVertically(-1)) {
            if (dy > 0) {//向上
                setY(getY() - dy);
                consumed[1] = dy;
            } else {//向下
                setY(getY() - dy);
                consumed[1] = dy;
//            Log.e("++", getY() + " " + dy + " " + layoutParams.height);
            }
        }*/

    }

    private float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float detalY = lastY - event.getRawY();
                lastY = (int) event.getRawY();

                ViewGroup.LayoutParams layoutParams = halfToFullRecycleView.getLayoutParams();
                if (detalY > 0) {//上拉
                    if (layoutParams.height < screenHeight) {
                        layoutParams.height += detalY;
                    } else {
                    }
                } else {//下拉
                    if (layoutParams.height > screenHeight / 2) {//小于屏幕1/2高度将不能下拉
                        layoutParams.height += detalY;
                    } else {
                    }
                }
                halfToFullRecycleView.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isInterceptTouchEvent;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        halfToFullRecycleView = findViewById(R.id.half_to_full_recycle_view);
    }
}
