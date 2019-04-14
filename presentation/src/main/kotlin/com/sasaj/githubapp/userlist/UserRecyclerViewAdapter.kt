package com.sasaj.githubapp.userlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sasaj.domain.entities.User
import com.sasaj.githubapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_list_content.view.*


class UserRecyclerViewAdapter(private val parentActivity: UserListActivity,
                              private val twoPane: Boolean) :
        RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    //    private val onClickListener: View.OnClickListener
    private var users = listOf<User>()

//    init {
//        onClickListener = View.OnClickListener { v ->
//            val repository = v.tag as GithubRepository
//            if (twoPane) {
//                val fragment = RepositoryDetailFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_USER_NAME, repository.ownerName)
//                        putString(ARG_REPOSITORY_NAME, repository.name)
//                    }
//                }
//                parentActivity.supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.repository_detail_container, fragment)
//                        .commit()
//            } else {
//                val intent = Intent(v.context, RepositoryDetailActivity::class.java).apply {
//                    putExtra(ARG_USER_NAME, repository.ownerName)
//                    putExtra(ARG_REPOSITORY_NAME, repository.name)
//                    putExtra(ARG_REPOSITORY_HTML_URL, repository.htmlUrl)
//                }
//
//                v.context.startActivity(intent)
//            }
//        }
//    }

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Picasso.get().load(user.avatarUrl).into(holder.avatar)
        holder.login.text = user.login
        Picasso.get().load(user.avatarUrl).into(holder.avatar)
        holder.login.text = user.login
        holder.name.text = ""
        if (user.contributions > 0) {
            holder.contributions.visibility = View.VISIBLE
            holder.contributions.text = "Contibutions: ${user.contributions}"
        }

    }


//        with(holder.itemView) {
//            tag = repository
//            setOnClickListener(onClickListener)
//        }


override fun getItemCount() = users.size

inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val avatar: ImageView = view.avatar
    val login: TextView = view.login
    val name: TextView = view.name
    val contributions: TextView = view.contributions
}
}