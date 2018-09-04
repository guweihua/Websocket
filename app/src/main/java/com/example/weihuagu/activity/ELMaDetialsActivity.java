package com.example.weihuagu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weihuagu.BaseActivity;
import com.example.weihuagu.utils.UiSwitch;
import com.example.weihuagu.websocket.R;

public class ELMaDetialsActivity extends BaseActivity {


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_elma_detials;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initThing(Bundle savedInstanceState) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new ListAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }


    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            return new ViewHolder(inflater.inflate(R.layout.item_home, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(v -> UiSwitch.single(ELMaDetialsActivity.this, ELMaActivity.class));
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View view) {
                super(view);
            }
        }
    }
}
