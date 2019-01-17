package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 子view(发起者)	                 父view（被回调）
 * startNestedScroll	         onStartNestedScroll、onNestedScrollAccepted
 * dispatchNestedPreScroll	     onNestedPreScroll
 * dispatchNestedScroll	         onNestedScroll
 * stopNestedScroll	             onStopNestedScroll
 *
 * @author lizhen
 */
public class NestedScrollingParentLayout extends RelativeLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper nestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    public NestedScrollingParentLayout(Context context) {
        super(context);
    }

    public NestedScrollingParentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollingParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当调用 target 的 startNestedScroll(int axes) 时，
     * 此方法就会被调用。在此方法中我们要做的就是根据 target
     * 和 nestedScrollAxes 决定 当前view 是否要与 target 配合进行嵌套滚动，
     * 并返回 true(要与target配合进行嵌套滚动)或 false(不与target配合进行嵌套滚动)。
     *
     * @param child            当前view 包含 target 的直接子view
     * @param target           发起嵌套滚动的子view，此子view必须实现 NestedScrollingChild 接口
     *                         此子view并不需要时当前view的直接子view
     * @param nestedScrollAxes 嵌套滚动的方向 可能是 ViewCompat.SCROLL_AXIS_HORIZONTAL | ViewCompat.SCROLL_AXIS_VERTICAL
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
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
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_NONE;
    }


}
