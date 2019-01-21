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

        Log.e("++", "-------------start-------------");
        Log.e("++", "dy:" + dy + " getScrollY():" + getScrollY() + " fadeoutTitleLayout.getHeight():" + fadeoutTitleLayout.getHeight());
        boolean hiddenTop = dy > 0 && getScrollY() < fadeoutTitleLayout.getHeight();
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }

        Log.e("NestedScroll Parent", "onNestedPreScroll " + "dx:" + dx + " dy:" + dy
                + " consumedX:" + consumed[0] + " consumedY:" + consumed[1]);
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        fadeoutTitleLayout = findViewById(R.id.fadeout_title_layout);
    }
}
