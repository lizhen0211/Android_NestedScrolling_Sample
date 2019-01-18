package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
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
        Log.e("NestedScroll Parent", "onStartNestedScroll");
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
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e("NestedScroll Parent", "onNestedScroll " + "dxConsumed:" + dxConsumed + " dyConsumed:" + dyConsumed
                + " dxUnconsumed:" + dxUnconsumed + " dyUnconsumed:" + dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // 应该移动的Y距离
        final float shouldMoveY = getY() + dy;

        // 获取到父View的容器的引用，这里假定父View容器是View
        final View parent = (View) getParent();

        int consumedY;
        //注意：这里 getY()即为当前view到父view的上边距，
        //parent.getHeight() - getHeight() - getY()即为当前view到父view的下边距
        if (shouldMoveY <= 0) {
            // 如果超过了父View的上边界，只消费子View到父View上边的距离
            consumedY = -(int) getY();
        } else if (shouldMoveY >= parent.getHeight() - getHeight()) {
            // 如果超过了父View的下边界，只消费子View到父View的下边距
            consumedY = (int) (parent.getHeight() - getHeight() - getY());
        } else {
            // 其他情况下全部消费
            consumedY = dy;
        }

        // 对父View进行移动
        setY(getY() + consumedY);

        // 将父View消费掉的放入consumed数组中
        consumed[1] = consumedY;

        Log.e("NestedScroll Parent", "onNestedPreScroll " + "dx:" + dx + " dy:" + dy
                + " consumedX:" + consumed[0] + " consumedY:" + consumed[1]);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e("NestedScroll Parent", "onNestedFling " + "velocityX:" + velocityX + " velocityY:" + velocityY
                + " consumed:" + consumed);
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e("NestedScroll Parent", "onNestedPreFling " + "velocityX:" + velocityX + " velocityY:" + velocityY);
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }


}
