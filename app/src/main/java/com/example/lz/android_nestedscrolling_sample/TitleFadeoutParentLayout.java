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

    private RelativeLayout fadeoutTitleLayout;

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

        /*//dy>0 向上滑动，dy<0向下滑动
        boolean hiddenTop = dy > 0 && getScrollY() < fadeoutTitleLayout.getHeight();
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            Log.e("onNestedPreScroll", "dy: " + dy + " getScrollY():" + getScrollY() + " fadeoutTitleLayout.getHeight():" + fadeoutTitleLayout.getHeight());
            scrollBy(0, dy);
            consumed[1] = dy;
        }*/

        if (dy > 0) {//dy>0 向上滑动
            if (getScrollY() < fadeoutTitleLayout.getHeight()) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        } else {//dy<0向下滑动
            if (getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1)) {
                Log.e("onNestedPreScroll", "dy: " + dy + " before getScrollY():" + getScrollY() + " fadeoutTitleLayout.getHeight():" + fadeoutTitleLayout.getHeight());
                scrollBy(0, dy);
                consumed[1] = dy;
                Log.e("onNestedPreScroll", "dy: " + dy + " after getScrollY():" + getScrollY() + " fadeoutTitleLayout.getHeight():" + fadeoutTitleLayout.getHeight());
            }
        }


    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        final int currentOffset = getScrollY();
        final int topHeight = fadeoutTitleLayout.getHeight();
        Log.e("++", consumed + "");
       /* if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > 3;
        }*/

        if (velocityY > 0) {//向上
            if (getScrollY() < fadeoutTitleLayout.getHeight()) {
                scrollTo(0, topHeight);
            }
        } else {//向下
            if (getScrollY() <= fadeoutTitleLayout.getHeight()) {
                scrollTo(0, 0);
            }
        }
        return true;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fadeoutTitleLayout = findViewById(R.id.fadeout_title_layout);
    }

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
