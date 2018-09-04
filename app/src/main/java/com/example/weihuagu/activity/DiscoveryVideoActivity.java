package com.example.weihuagu.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weihuagu.adapter.FindPatListAdapter;
import com.example.weihuagu.websocket.R;

public class DiscoveryVideoActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private FrameLayout relative;
    private SwipeRefreshLayout swiperefreshLayout;
    private ImageView imgSearch;
    private ImageView ivShare;
    private FrameLayout flBottomHide;
    private TextView tvCount;
    private ImageView imgClose;
    private ListView myListyview;
    private TextView tvContentTo;
    private TextView tvSendTo;
    private FrameLayout laySend;
    private LinearLayout hihed;
    private EditText editContent;
    private TextView tvSend;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_video);

        initView();

        FindPatListAdapter findadapter = new FindPatListAdapter(this);
        myListyview.setAdapter(findadapter);

    }

    private void initView() {


        mRecyclerView = findViewById(R.id.recycler);
        relative = findViewById(R.id.relative);
        swiperefreshLayout = findViewById(R.id.swiperefreshlayout);
        imgSearch = findViewById(R.id.img_search);
        ivShare = findViewById(R.id.iv_share);


        /*----------底部评论按钮2---------*/
        flBottomHide = findViewById(R.id.fl_bottom_hide);
        tvCount = findViewById(R.id.tv_count);
        imgClose = findViewById(R.id.img_close);
        myListyview = findViewById(R.id.my_listyview);
        tvContentTo = findViewById(R.id.tv_content_to);
        tvSendTo = findViewById(R.id.tv_send_to);


        /*----------底部说点什么吧---------*/
        laySend = findViewById(R.id.lay_send);
        hihed = findViewById(R.id.hihed);
        editContent = findViewById(R.id.edit_content);
        tvSend = findViewById(R.id.tv_send);


    }




}
