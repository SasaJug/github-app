package com.sasaj.githubapp.list

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sasaj.domain.Repository
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.githubapp.R
import com.sasaj.githubapp.detail.RepositoryDetailActivity
import kotlinx.android.synthetic.main.repository_list_content.view.*


class SimpleItemRecyclerViewAdapter(private val parentActivity: RepositoryListActivity,
                                    private val twoPane: Boolean) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private var repositories = listOf<GithubRepository>()

    init {
        onClickListener = View.OnClickListener { v ->
            val repository = v.tag as Repository
            if (twoPane) {
//                val fragment = LocationDetailFragment().apply {
//                    arguments = Bundle().apply {
//                        putInt(LocationDetailFragment.ARG_CITY, repository.id)
//                    }
//                }
//                parentActivity.supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.location_detail_container, fragment)
//                        .commit()
            } else {
                val intent = Intent(v.context, RepositoryDetailActivity::class.java).apply {
//                    putExtra(LocationDetailFragment.ARG_CITY, repository)
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