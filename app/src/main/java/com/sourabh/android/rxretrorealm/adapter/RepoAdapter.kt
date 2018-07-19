package com.sourabh.android.rxretrorealm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sourabh.android.rxretrorealm.R
import com.sourabh.android.rxretrorealm.modal.Repo

public class RepoAdapter(mContext: Context, private val mReposList: List<Repo>) :
        RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val v = mInflater.inflate(R.layout.recycler_view_item, parent, false)
        return RepoViewHolder(v)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = mReposList[position]
        holder.setItemContent(repo)
    }

    override fun getItemCount(): Int {
        return mReposList.size
    }

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvRepoName: TextView = itemView.findViewById(R.id.repo_name)
        var tvRepoId: TextView = itemView.findViewById(R.id.repo_id)

        fun setItemContent(repo: Repo) {
            tvRepoName.text = repo.name
            tvRepoId.text = repo.id
        }
    }
}
