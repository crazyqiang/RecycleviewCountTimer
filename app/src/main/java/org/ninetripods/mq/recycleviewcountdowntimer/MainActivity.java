package org.ninetripods.mq.recycleviewcountdowntimer;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipe_refresh_layout;
    private RecyclerView rv_timer;
    private MyAdapter adapter;
    private List<Long> mDatas;
    private Long getDataTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
        initEvents();
        init();
    }


    private void initDatas() {
        mDatas = new ArrayList<>();
        getDataTime = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            mDatas.add((long) (20 * 1000 + i * 1000));
        }
    }


    private void initView() {
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        rv_timer = (RecyclerView) findViewById(R.id.rv_timer);
    }

    private void initEvents() {
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataTime = System.currentTimeMillis();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipe_refresh_layout.isRefreshing()) {
                            swipe_refresh_layout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void init() {
        rv_timer.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter();
        rv_timer.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timer, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Long miles = mDatas.get(position);
            Long timestamp = System.currentTimeMillis() - getDataTime;
            holder.countTimer.setTypeMills(miles - timestamp);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            CountDownView countTimer;

            public MyViewHolder(View itemView) {
                super(itemView);
                countTimer = (CountDownView) itemView.findViewById(R.id.downtimer);
            }
        }
    }

}
