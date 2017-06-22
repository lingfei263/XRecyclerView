package cn.ffb.widget.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import cn.ffb.utils.DisplayUtils;


/**
 * Created by lingfei on 2016/4/12.
 */
public class XRecyclerListView extends RecyclerListView {

    public XRecyclerListView(Context context) {
        super(context);
        init();
    }

    public XRecyclerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XRecyclerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private LinearLayout mFooterLayout;
    private XFooterView mFooterView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mLoadMoreEnable;
    private boolean mAutoLoadMoreEnable;
    private boolean isHaveMoreData;
    private OnScrollListener mOnScrollListener = new OnLoadMoreScrollListener() {
        @Override
        public void onLoadMore(RecyclerView recyclerView) {
            if (mAutoLoadMoreEnable && isHaveMoreData) {
                loadMore();
            }
        }
    };
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mAutoLoadMoreEnable && isHaveMoreData) {
                loadMore();
            }
        }
    };

    private void init() {
        mFooterView = new XFooterView(getContext());
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        DisplayUtils.dp2Px(getContext(), 48));
        layoutParams.gravity = Gravity.CENTER;
        mFooterLayout = new LinearLayout(getContext());
        mFooterLayout.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams footerParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        footerParams.gravity = Gravity.CENTER;
        mFooterLayout.addView(mFooterView, footerParams);
        setLoadMoreEnable(true);
        mFooterLayout.setOnClickListener(mOnClickListener);
    }

    private void loadMore() {
        mFooterView.loading();
        mFooterView.setState(XFooterView.STATE_LOADING);
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void stopLoadMore() {
        completeLoad();
    }

    private void completeLoad() {
        mFooterView.normal();
        if (isHaveMoreData) {
            mFooterView.setState(XFooterView.STATE_NORMAL);
        } else {
            mFooterView.setState(XFooterView.STATE_NOMORE);
        }
    }

    public void setAutoLoadMoreEnable(boolean autoLoadMoreEnable) {
        this.mAutoLoadMoreEnable = autoLoadMoreEnable;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    public void setLoadMoreEnable(boolean enable) {
        if (mLoadMoreEnable != enable) {
            this.mLoadMoreEnable = enable;
            if (enable) {
                mFooterView.show();
                addFooterView(mFooterLayout);
                addOnScrollListener(mOnScrollListener);
            } else {
                mFooterView.hide();
                removeFooterView(mFooterLayout);
                removeOnScrollListener(mOnScrollListener);
            }
        }
    }

    public boolean isLoadMoreEnable() {
        return mLoadMoreEnable;
    }

    public void setHaveMoreData(boolean isHaveMoreData) {
        this.isHaveMoreData = isHaveMoreData;
        completeLoad();
    }
}
