package com.example.lz.android_nestedscrolling_sample;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author lizhen
 */
public class TitleFadeoutParentLayout extends RelativeLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper nestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    public TitleFadeoutParentLayout(Context context) {
        super(context);
        init();
    }

    public TitleFadeoutParentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleFadeoutParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

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
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {


        int consumedY = 0;

        float curViewBottomY = getY() + getChildTitleLayout(this);
        Log.e("++", "-------------start-------------");
        Log.e("++", "Y:" + getY() + " getHeight():" + getHeight() + " scrollY():" + getScrollY());
        Log.e("++", " targetY:" + target.getY() + "  targetHeight():" + target.getHeight() + " targetScrollY:" + target.getScrollY());
        Log.e("++", " curViewBottomY:" + curViewBottomY);
        Log.e("++", "dy:" + dy);
        /*if (dy > 0) {
            //上滑
            if (curViewBottomY <= 0) {
                consumedY = 0;
            } else {
                consumedY = -dy;
            }
        } else {
            //下滑
            if (target.getScrollY() < 0) {
                consumedY = 0;
            } else {
                consumedY = -dy;
            }
        }

        // 对父View进行移动
        setY(getY() + consumedY);

        // 将父View消费掉的放入consumed数组中
        consumed[0] = -dx;
        consumed[1] = consumedY;*/
        boolean hiddenTop = dy > 0 && getScrollY() < getChildTitleLayout(this);
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }

        Log.e("NestedScroll Parent", "onNestedPreScroll " + "dx:" + dx + " dy:" + dy
                + " consumedX:" + consumed[0] + " consumedY:" + consumed[1]);
    }

    //是否存在这个子viewA,返回true就是有，false就是没有
    private int getChildTitleLayout(View view) {
        int height = 0;
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (viewchild.getTag() != null && String.valueOf(viewchild.getTag()).equals("title_layout")) {
                    height = viewchild.getHeight();
                    break;
                }
            }
        }
        return height;
    }
}
