package com.shop.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/4/15/015.
 */

public class SearchActivity extends Activity {

    @ViewInject(R.id.list_view)
    ListView listview;
    @ViewInject(R.id.searView)
    SearchView seachview;
    String[] str = {"aaa", "bbaa", "dfaf", "aaaa"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        // listview = (ListView) findViewById(R.id.list_view);
        // seachview=(SearchView) findViewById(R.id.searView);
        x.view().inject(this);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str));
        listview.setTextFilterEnabled(true);
        listview.setVisibility(View.INVISIBLE);
        seachview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    listview.setVisibility(View.VISIBLE);
                    listview.setFilterText(newText);
                }else {
                    listview.clearTextFilter();
                    listview.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

    }
}
