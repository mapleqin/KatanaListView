package net.soulwolf.katanalistview.sample;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import net.soulwolf.katanalistview.KatanaListView;
import net.soulwolf.katanalistview.KatanaView;
import net.soulwolf.katanalistview.OnLoadMoreListener;
import net.soulwolf.observable.Observable;
import net.soulwolf.observable.OnSubscribeImpl;
import net.soulwolf.observable.SubscriberHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ListActivity extends AppCompatActivity implements OnLoadMoreListener {

    private final AtomicInteger mAtomicInteger = new AtomicInteger(0);

    private KatanaListView mKatanaListView;
    private List<String> mListData = new ArrayList<>();
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.mKatanaListView = (KatanaListView) findViewById(R.id.list);
        this.mKatanaListView.setOnLoadMoreListener(this);

        for (int i = 0; i < 20; i++) {
            this.mListData.add(String.format("KatanaList-item-%s", mAtomicInteger.incrementAndGet()));
        }
        this.mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, mListData);
        this.mKatanaListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMore(KatanaView view) {
        loadMoreData();
    }

    private void loadMoreData() {
        Observable.create(new OnSubscribeImpl<List<String>>() {
            @Override
            public List<String> execute() throws Exception {
                if(mAtomicInteger.get() > 50){
                    throw new RuntimeException();
                }
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 8; i++) {
                    list.add(String.format("KatanaList-new-item-%s", mAtomicInteger.incrementAndGet()));
                }
                SystemClock.sleep(2000);
                return list;
            }
        }).subscribe(new SubscriberHandler<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) throws Exception {
                mListData.addAll(strings);
                mAdapter.notifyDataSetChanged();
                mKatanaListView.performLoading();
            }

            @Override
            public void onFailure(Throwable error) {
                super.onFailure(error);
                mKatanaListView.performLoadError();
            }
        });
    }
}
