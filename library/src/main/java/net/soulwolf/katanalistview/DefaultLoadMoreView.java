package net.soulwolf.katanalistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * author: EwenQin
 * since : 16/5/14 上午11:27.
 */
public class DefaultLoadMoreView extends FrameLayout implements KatanaLoadMoreView{

    private static final int STATUS_LOADING = 1;
    private static final int STATUS_LOAD_FINISH = 2;
    private static final int STATUS_LOAD_ERROR = 3;

    private LoadView    mLoadView;
    private TextView    mLoadText;
    private View        mLoadMoreView;

    private int mCurrentStatus;

    public DefaultLoadMoreView(Context context) {
        this(context, null);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_default_load_more, this, true);
        this.mLoadMoreView = findViewById(R.id.load_more_view);
        this.mLoadView = (LoadView) findViewById(R.id.load_pro);
        this.mLoadText = (TextView) findViewById(R.id.load_txt);
        this.performLoading();
    }

    @Override
    public void performLoadFinish(boolean hideView) {
        if (mCurrentStatus != STATUS_LOAD_FINISH) {
            mCurrentStatus = STATUS_LOAD_FINISH;
            mLoadText.setText(R.string.list_nodata);
            mLoadView.setVisibility(View.VISIBLE);
            // Hide progress bar and arrow.
            mLoadView.setVisibility(View.GONE);
            if (hideView) {
                mLoadMoreView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void performLoadFinish() {
        this.performLoadFinish(false);
    }

    @Override
    public void performLoadError() {
        mCurrentStatus = STATUS_LOAD_ERROR;
        mLoadText.setText(R.string.load_more_error);
        mLoadText.setVisibility(View.VISIBLE);
        mLoadView.setVisibility(View.GONE);
    }

    @Override
    public void performLoading() {
        mCurrentStatus = STATUS_LOADING;
        mLoadView.setVisibility(View.VISIBLE);
        // Set refresh view text to the refreshing label
        mLoadText.setVisibility(View.VISIBLE);
        mLoadText.setText(R.string.loading);
        mLoadMoreView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean hasLoadError() {
        return mCurrentStatus == STATUS_LOAD_ERROR;
    }

    @Override
    public boolean hasLoading() {
        return mCurrentStatus == STATUS_LOADING;
    }
}
