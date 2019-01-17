package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author lizhen
 */
public class NestedScrollingChildLayout extends RelativeLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper nestedScrollingChildHelper = new NestedScrollingChildHelper(this);

    public NestedScrollingChildLayout(Context context) {
        super(context);
        init();
    }

    public NestedScrollingChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollingChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    /**
     * 开启嵌套滚动流程(实际上是进行了一些嵌套滚动前准备工作)。
     * 当找到了能够配合当前 子view 进行嵌套滚动的 父view 时，返回值为 true
     * （Returns：true if a cooperative parent was found
     * and nested scrolling has been enabled for the current gesture）。
     *
     * @param axes
     * @return
     */
    @Override
    public boolean startNestedScroll(int axes) {
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    /**
     * 在子view 自己进行滚动之前调用此方法
     * 询问 父view 是否要在 子view 之前进行滚动。
     * <p>
     * 此方法的前两个参数用于告诉 父View 此次要滚动的距离；
     * 而 第三 第四 个参数用于 子view 获取 父view 消费掉的距离和 父view 位置的偏移量。
     * 第一 第二 个参数为输入参数，即常规的函数参数，调用函数的时候我们需要为其传递确切的值。
     * 而 第三 第四个 参数为输出参数，调用函数时我们只需要传递容器（在这里就是两个数组），
     * 在调用结束后，我们就可以从容器中获取函数输出的值。
     * <p>
     * 如果 parent 消费了一部分或全部距离，则此方法返回 true。
     *
     * @param dx
     * @param dy
     * @param consumed
     * @param offsetInWindow
     * @return
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    /**
     * 在 子view 自己进行滚动之后调用此方法。
     * 询问 父view 是否还要进行余下(unconsumed)的滚动。
     * <p>
     * 前四个参数为输入参数，用于告诉 父view 已经消费 和 尚未消费 的距离，
     * 最后一个参数为输出参数，用于 子view 获取 父view 位置的偏移量。
     * <p>
     * 返回值：(翻译出来可能有歧义，直接放原文)
     * true if the event was dispatched, false if it could not be dispatched.
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param offsetInWindow
     * @return
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return nestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return nestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}
