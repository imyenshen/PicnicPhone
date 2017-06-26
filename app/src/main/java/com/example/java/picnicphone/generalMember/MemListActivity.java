package com.example.java.picnicphone.generalMember;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.picnicphone.MemberActivity;
import com.example.java.picnicphone.R;
import com.example.java.picnicphone.main.Common;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;
import java.util.concurrent.ExecutionException;



public class MemListActivity extends AppCompatActivity {

    private final static String TAG = "MemListActivity";
    private RecyclerView memListRecyclerView;
    private GeneralMember generalMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_list);


        // 測試 資料庫連線{
        List<GeneralMember> generalMemberList = null;

        String url = Common.URL +"General_memberServlet";
        try {
            generalMemberList = new GeneralMemberAllTask().execute(url).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        memListRecyclerView = (RecyclerView) findViewById(R.id.memListRecyclerView);
        memListRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        1, StaggeredGridLayoutManager.VERTICAL));
        memListRecyclerView.setAdapter(new GeneralMemberAdapter(this, generalMemberList));

    }

    private class GeneralMemberAdapter extends RecyclerView.Adapter<GeneralMemberAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private Context context;
        private List<GeneralMember> generalMemberList;
        private GeneralMember generalMember;
        private boolean[] newsExpanded;
        private View view;

        GeneralMemberAdapter(Context context, List<GeneralMember> generalMemberList) {
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
            this.generalMemberList = generalMemberList;
            newsExpanded = new boolean[generalMemberList.size()];
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvId, tvName, tvGen;

            public MyViewHolder(View memlistView) {
                super(memlistView);

                imageView = (ImageView) itemView.findViewById(R.id.ivImage);
                tvId = (TextView) memlistView.findViewById(R.id.tvId);
                tvName = (TextView) memlistView.findViewById(R.id.tvName);
                tvGen = (TextView) memlistView.findViewById(R.id.tvGen);
            }
        }

        @Override
        public int getItemCount() {
            return generalMemberList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View memlistView = layoutInflater.inflate(R.layout.mem_list_view, viewGroup, false);
            return new MyViewHolder(memlistView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            final GeneralMember generalMember = generalMemberList.get(position);

            // 上傳通訊錄照片 {

            String url = Common.URL + "General_memberServlet";
            String MEM_NO = generalMember.getMEM_NO();
            int imageSize = 250;
            new GeneralMemberImageTask(viewHolder.imageView).execute(url, MEM_NO, imageSize);
            viewHolder.tvId.setText(generalMember.getMEM_NO());
            viewHolder.tvName.setText(generalMember.getMEM_NAME());
            viewHolder.tvGen.setText(generalMember.getMEM_GEN());

            // } 上面在設定監聽器

        }
    }

    public void backMember(View view) {
        Intent intent = new Intent(this, MemberActivity.class);
        startActivity(intent);
    }


}
