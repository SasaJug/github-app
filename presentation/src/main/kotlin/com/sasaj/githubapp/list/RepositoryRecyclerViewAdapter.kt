package com.sasaj.githubapp.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_REPOSITORY_HTML_URL
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_REPOSITORY_NAME
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_USER_NAME
import com.sasaj.githubapp.detail.RepositoryDetailActivity
import com.sasaj.githubapp.detail.RepositoryDetailFragment
import kotlinx.android.synthetic.main.repository_list_content.view.*


class RepositoryRecyclerViewAdapter(private val parentActivity: RepositoryListActivity,
                                    private val twoPane: Boolean) :
        RecyclerView.Adapter<RepositoryRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private var repositories = listOf<GithubRepository>()

    init {
        onClickListener = View.OnClickListener { v ->
            val repository = v.tag as GithubRepository
            if (twoPane) {
                val fragment = RepositoryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_USER_NAME, repository.ownerName)
                        putString(ARG_REPOSITORY_NAME, repository.name)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.repository_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, RepositoryDetailActivity::class.java).apply {
                    putExtra(ARG_USER_NAME, repository.ownerName)
                    putExtra(ARG_REPOSITORY_NAME, repository.name)
                    putExtra(ARG_REPOSITORY_HTML_URL, repository.htmlUrl)
                }

                v.context.startActivity(intent)
            }
        }
    }

    fun setRepositories(repositories: List<GithubRepository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        val name = repository.name
        val fullname = repository.fullName
        holder.idView.text = name
        holder.contentView.text = fullname

        with(holder.itemView) {
            tag = repository
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = repositories.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.title
        val contentView: TextView = view.description
    }
}