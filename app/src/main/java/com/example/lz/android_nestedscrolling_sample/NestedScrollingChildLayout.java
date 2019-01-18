package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author lizhen
 */
public class NestedScrollingChildLayout extends RelativeLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper nestedScrollingChildHelper = new NestedScrollingChildHelper(this);

    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    private float lastX;
    private float lastY;

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
        Log.e("NestedScroll Child", "startNestedScroll");
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
        Log.e("NestedScroll Child", "dispatchNestedPreScroll " + "dx:" + dx + " dy:" + dy
                + " consumedX:" + consumed[0] + " consumedY:" + consumed[1]
                + " offsetInWindowX:" + offsetInWindow[0] + " offsetInWindowY:" + offsetInWindow[1]);
        return nestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        Log.e("NestedScroll Child", "stopNestedScroll");
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
        Log.e("NestedScroll Child", "dispatchNestedScroll " + "dxConsumed:" + dxConsumed + " dyConsumed:" + dyConsumed
                + " dxUnconsumed:" + dxUnconsumed + " dyUnconsumed:" + dyUnconsumed
                + " offsetInWindowX:" + offsetInWindow[0] + " offsetInWindowY:" + offsetInWindow[1]);
        //parent 以消费的X距离
        consumed[0] = dxConsumed;
        //parent 以消费的Y距离
        consumed[1] = dyConsumed;
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.e("NestedScroll Child", "dispatchNestedFling " + "velocityX:" + velocityX + " velocityY:" + velocityY
                + " consumed:" + consumed);
        return nestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.e("NestedScroll Child", "dispatchNestedPreFling " + "velocityX:" + velocityX + " velocityY:" + velocityY);
        return nestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getRawX()、getRawY()返回的是触摸点相对于屏幕的位置，
        //而getX()、getY()返回的则是触摸点相对于View的位置
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - lastY;
                if (dispatchNestedPreScroll(0, (int) dy, consumed, offsetInWindow)) {
                    //dy 要减掉parent 消耗的距离
                    dy -= consumed[1];
                }
                // 上面的 (int)deltaY 会造成精度丢失，这里把精度给舍弃掉
                if (Math.floor(Math.abs(dy)) == 0) {
                    dy = 0;
                }
                //当前view top 不超过父view的上边界
                float parentTopEdge = Math.max(getY() + dy, 0);
                //当前view与父view的总间距
                int totalMargin = ((View) getParent()).getHeight() - getHeight();
                // 这里移动子View，下面的min,max是为了控制边界，避免子View越界
                setY(Math.min(parentTopEdge, totalMargin));
                //setY(getY() + dy);
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;
            case MotionEvent.ACTION_CANCEL:
                cancelTouch();
            default:
                break;
        }
        return true;
    }

    private void cancelTouch() {
        resetTouch();
    }

    private void resetTouch() {
        /*if (mVelocityTracker != null) {
            mVelocityTracker.clear();
        }*/
        stopNestedScroll();
        /*releaseGlows();*/
    }
}
