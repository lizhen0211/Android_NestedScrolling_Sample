package com.example.lz.android_nestedscrolling_sample;

import android.app.Activity;
import android.os.Bundle;

/**
 * NestedScrolling机制 能够让 父view 和 子view 在滚动时进行配合，其基本流程如下：
 * <p>
 * 1. 当 子view 开始滚动之前，可以通知 父view，让其先于自己进行滚动;
 * <p>
 * 2. 子view 自己进行滚动
 * <p>
 * 3. 子view 滚动之后，还可以通知 父view 继续滚动
 * <p>
 * 要实现这样的交互，父View 需要实现 NestedScrollingParent接口，而 子View 需要实现NestedScrollingChild接口。
 * <p>
 * 在这套交互机制中，child 是动作的发起者，parent 只是接受回调并作出响应。
 * <p>
 * 另外：父view 和 子view 并不需要是直接的父子关系，即如果 "parent1 包含 parent2，parent2 包含child”，则 parent1 和child 仍能通过 NestedScrolling机制 进行交互。
 *
 * @author lizhen
 */
public class SimpleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
    }
}
