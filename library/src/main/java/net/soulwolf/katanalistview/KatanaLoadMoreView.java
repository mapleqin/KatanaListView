package net.soulwolf.katanalistview;

/**
 * author: EwenQin
 * since : 16/5/14 上午11:16.
 */
public interface KatanaLoadMoreView {

    void performLoadFinish(boolean hideView);

    void performLoadFinish();

    void performLoadError();

    void performLoading();

    boolean hasLoadError();

    boolean hasLoading();
}
