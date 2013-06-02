package com.change.dubu.ui;

import android.view.LayoutInflater;
import com.change.dubu.R;
import com.change.dubu.core.News;

import java.util.List;

/**
 * User: kingkingdubu
 * Date: 13. 6. 2
 * Time: 오후 9:35
 */
public class BookListAdapter  extends AlternatingColorListAdapter<News> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public BookListAdapter(LayoutInflater inflater, List<News> items,
                           boolean selectable) {
        super(R.layout.news_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public BookListAdapter(LayoutInflater inflater, List<News> items) {
        super(R.layout.news_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_summary,
                R.id.tv_date };
    }

    @Override
    protected void update(int position, News item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getContent());
        setText(2, item.getUpdatedAt().substring(8,16));
    }
}
