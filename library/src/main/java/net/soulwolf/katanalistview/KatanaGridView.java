package net.soulwolf.katanalistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * author: EwenQin
 * since : 16/5/14 上午11:27.
 */
public class KatanaGridView extends GridViewWithHeaderAndFooter implements OnScrollListener,KatanaView, View.OnClickListener {

    private KatanaLoadMoreView mKatanaLoadMore;
    private View mHockLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnScrollListener mOnScrollListener;

    private long mLastLoadMoreTime;

    public KatanaGridView(Context context) {
        this(context, null);
    }

    public KatanaGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KatanaGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setLoadMoreView(new DefaultLoadMoreView(context));
        super.setOnScrollListener(this);
    }

    @Override
    public <T extends View & KatanaLoadMoreView> void setLoadMoreView(T loadMoreView) {
        if(mHockLoadMoreView != null){
            this.removeFooterView(mHockLoadMoreView);
        }
        this.mHockLoadMoreView = loadMoreView;
        this.mKatanaLoadMore = loadMoreView;
        if(mHockLoadMoreView != null){
            this.mHockLoadMoreView.setOnClickListener(this);
            this.addFooterView(mHockLoadMoreView,null,false);
        }
    }

    @Override
    public View getLoadMoreView() {
        return mHockLoadMoreView;
    }

    @Override
    public void performLoading() {
        if (mKatanaLoadMore != null)
            mKatanaLoadMore.performLoading();
    }

    @Override
    public void performLoadError() {
        if (mKatanaLoadMore != null)
            mKatanaLoadMore.performLoadError();
    }

    @Override
    public void performLoadFinish() {
        if (mKatanaLoadMore != null)
            mKatanaLoadMore.performLoadFinish();
    }

    @Override
    public void performLoadFinish(boolean hideView) {
        if (mKatanaLoadMore != null)
            mKatanaLoadMore.performLoadFinish(hideView);
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    @Override
    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null){
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int firstPos = view.getFirstVisiblePosition();
        int count = view.getChildCount();
        if (view.getAdapter() != null && firstPos + count >= view.getAdapter().getCount()) {
            if (mOnLoadMoreListener != null) {
                long currentTimeMillis = System.currentTimeMillis();
                long temp = currentTimeMillis - mLastLoadMoreTime;
                if (temp > 1500) {
                    mOnLoadMoreListener.onLoadMore(this);
                    mLastLoadMoreTime = currentTimeMillis;
                }
            }
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    protected void layoutChildren() {
        try {
            super.layoutChildren();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mHockLoadMoreView){
            if (mKatanaLoadMore.hasLoadError()) {
                mKatanaLoadMore.performLoading();
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore(this);
                    setSelection(getAdapter().getCount() - 1);
                }
            }
        }
    }
}
