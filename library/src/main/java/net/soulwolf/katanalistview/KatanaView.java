package net.soulwolf.katanalistview;

import android.view.View;

/**
 * author: Ewen
 * since : 16/1/4 下午3:02.
 */
public interface KatanaView {

    <T extends View & KatanaLoadMoreView> void setLoadMoreView(T loadMoreView);

    View getLoadMoreView();

    void performLoading();

    void performLoadError();

    void performLoadFinish();

    void performLoadFinish(boolean hideView);

    void setOnLoadMoreListener(OnLoadMoreListener listener);

    OnLoadMoreListener getOnLoadMoreListener();
}
