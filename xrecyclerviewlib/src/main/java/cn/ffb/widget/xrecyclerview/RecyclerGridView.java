package cn.ffb.widget.xrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by lingfei on 2016/4/12.
 */
public class RecyclerGridView extends BaseRecyclerView {
    private int columnCount;

    public RecyclerGridView(Context context) {
        super(context, null);
    }

    public RecyclerGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RecyclerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getFirstVisiblePosition() {
        int position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        return position;
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // list the attributes we want to fetch
            // columnWidth is what GridView uses, so we use it too
            int[] attrsArray = {
                    columnCount
            };

            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);

            //retrieve the value of the 0 index, which is columnWidth
            columnCount = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }

        setup(columnCount, VERTICAL, false);
    }

    @Override
    public int getLastVisiblePosition() {
        int position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        return position;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        ((GridLayoutManager) getLayoutManager()).setSpanCount(columnCount);
    }

    public void setup(int spanCount, int orientation, boolean reverseLayout) {
        GridLayoutManager layoutManager =
                new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout);
        this.setLayoutManager(layoutManager);
        // 设置分割线
    }
}
