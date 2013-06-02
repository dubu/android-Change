package com.change.dubu.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import butterknife.InjectView;
import com.change.dubu.BootstrapApplication;
import com.change.dubu.BootstrapServiceProvider;
import com.change.dubu.R;
import com.change.dubu.authenticator.LogoutService;
import com.change.dubu.core.News;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.change.dubu.core.Constants.Extra.NEWS_ITEM;

/**
 * User: kingkingdubu
 * Date: 13. 6. 2
 * Time: 오전 9:46
 */
public class BookListFragment extends ItemListFragment<News> {

    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    //@InjectView(R.id.btn_write) protected Button btnWrite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.getInstance().inject(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_news);

        /*
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BookActivity.class));
            }
        });
        */
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter()
                .addHeader(activity.getLayoutInflater()
                        .inflate(R.layout.news_list_item_labels, null));

        /*
        Button  btnWrite  = (Button)activity.getLayoutInflater()
                .inflate(R.layout.book_list_item_labels, null).findViewById(R.id.btn_write);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BookActivity.class));
            }
        });
        */


    }

    @Override
    LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);

        super.onDestroyView();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        final List<News> initialItems = items;
        return new ThrowableLoader<List<News>>(getActivity(), items) {

            @Override
            public List<News> loadData() throws Exception {
                try {
                    if(getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getBooks();
                    } else {
                        return Collections.emptyList();
                    }

                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<News> createAdapter(List<News> items) {
        return new BookListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        News news = ((News) l.getItemAtPosition(position));
        startActivity(new Intent(getActivity(), BookActivity.class).putExtra(NEWS_ITEM, news));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_news;
    }
}
