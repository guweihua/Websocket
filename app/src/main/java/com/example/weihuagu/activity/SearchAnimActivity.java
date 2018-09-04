package com.example.weihuagu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.websocket.R;

public class SearchAnimActivity extends BaseActivity {

    private TextView mSearchBGTxt;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search_anim;
    }


    @Override
    protected void initListeners() {


        mSearchBGTxt.setOnClickListener(v -> {

            Intent intent = new Intent(SearchAnimActivity.this,SearchSecondActivity.class);
            int location[] = new int[2];
            mSearchBGTxt.getLocationOnScreen(location);
            intent.putExtra("x",location[0]);
            intent.putExtra("y",location[1]);
            startActivity(intent);
            overridePendingTransition(0,0);


        });

    }

    @Override
    protected void initThing(Bundle savedInstanceState) {

        mSearchBGTxt = findViewById(R.id.tv_search_bg);
    }

    @Override
    public boolean getisUseAutoLayout() {
        return false;
    }
}
