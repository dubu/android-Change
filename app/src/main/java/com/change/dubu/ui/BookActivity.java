package com.change.dubu.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import com.change.dubu.BootstrapServiceProvider;
import com.change.dubu.R;
import com.change.dubu.core.News;

import javax.inject.Inject;

import java.io.IOException;

import static com.change.dubu.core.Constants.Extra.NEWS_ITEM;

/**
 * User: kingkingdubu
 * Date: 13. 6. 2
 * Time: 오전 10:01
 */
public class BookActivity extends BootstrapActivity   {

    protected static News newsItem;
    protected static Activity activity;

    @InjectView(R.id.tv_title) protected TextView title;
    @InjectView(R.id.tv_content) protected TextView content;
    @InjectView(R.id.btn_save) protected Button btnSave;

    @Inject
    protected BootstrapServiceProvider serviceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book);

        if(getIntent() != null && getIntent().getExtras() != null) {
            newsItem = (News) getIntent().getExtras().getSerializable(NEWS_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(newsItem.getTitle());

        title.setText(newsItem.getTitle());
        content.setText(newsItem.getContent());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newsItem == null){
                    newsItem = new News();
                    newsItem.setTitle(title.getText().toString());
                    newsItem.setContent(content.getText().toString());
                    create(newsItem);
                }else{
                    newsItem.setTitle(title.getText().toString());
                    newsItem.setContent(content.getText().toString());
                    update(newsItem);
                }
            }
        });
    }
    private void create(News newsObject) {
        AsyncTask task = new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    boolean rs = serviceProvider.getService(activity).addBooks(newsItem);
                    if(rs){

                    }
                } catch (Exception e) {

                }
                finish();
                return null;
            }
        }.execute();
    }

    private void update(News newsObjec) {
        AsyncTask task = new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    boolean rs =serviceProvider.getService(activity).editBooks(newsItem);
                    if(rs){

                    }
                } catch (Exception e) {

                }
                finish();
                return null;
            }
        }.execute();
    }
}
