package com.sourabh.android.rxretrorealm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourabh.android.rxretrorealm.R;
import com.sourabh.android.rxretrorealm.modal.Repo;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    LayoutInflater mInflater;
    Context mContext;
    List<Repo> mReposList;

    public RepoAdapter(Context context, List<Repo> reposList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mReposList = reposList;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.recycler_view_item,parent,false);
        return new RepoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repo repo = mReposList.get(position);
        holder.setItemContent(repo);
    }

    @Override
    public int getItemCount() {
        return mReposList.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{
        TextView tvRepoName, tvRepoId;
        public RepoViewHolder(View itemView) {
            super(itemView);
            tvRepoId = itemView.findViewById(R.id.repo_id);
            tvRepoName = itemView.findViewById(R.id.repo_name);
        }
        void setItemContent(Repo repo){
            tvRepoName.setText(repo.getName());
            tvRepoId.setText(repo.getId());
        }
    }
}
