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

    private var users = listOf<User>()


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


    override fun getItemCount() = users.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.avatar
        val login: TextView = view.login
        val name: TextView = view.name
        val contributions: TextView = view.contributions
    }
}