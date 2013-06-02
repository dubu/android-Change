

package com.change.dubu.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.change.dubu.R;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        //return 4;
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
            NewsListFragment newsFragment = new NewsListFragment();
            newsFragment.setArguments(bundle);
            return newsFragment;
        case 1:
            BookListFragment bookFragment = new BookListFragment();
            bookFragment.setArguments(bundle);
            return bookFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.page_news);
        case 1:
            return resources.getString(R.string.page_book);
        default:
            return null;
        }
    }
}
