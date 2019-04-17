package com.sasaj.githubapp.userlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.sasaj.domain.entities.User
import com.sasaj.githubapp.GitHubApplication
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity
import com.sasaj.githubapp.userlist.UserListViewState.Companion.CONTRIBUTORS_LOADED
import com.sasaj.githubapp.userlist.UserListViewState.Companion.LOADING
import com.sasaj.githubapp.userlist.UserListViewState.Companion.STARGAZERS_LOADED
import kotlinx.android.synthetic.main.activity_repository_list.*
import kotlinx.android.synthetic.main.repository_list.*
import javax.inject.Inject

class UserListActivity : BaseActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    @Inject
    lateinit var listVMFactory: UserListVMFactory

    private lateinit var vm: UserListViewModel
    private lateinit var adapter: UserRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)

        (application as GitHubApplication).appComponent.inject(this)

        vm = ViewModelProviders.of(this, listVMFactory).get(UserListViewModel::class.java)

        vm.listLiveData.observe(this, Observer { state -> handleResponse(state) })
        vm.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                renderErrorState(it)
            }
        })

        setSupportActionBar(toolbar)
        toolbar.title = title

        vm.type = intent.getIntExtra(ARG_USER_TYPE, 0)
        vm.url = intent.getStringExtra(ARG_USER_URL)

        if (repository_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(repository_list)

    }

    override fun onStart() {
        super.onStart()
        vm.getRepositoryUsers()
    }


    private fun handleResponse(listViewState: UserListViewState?) {
        when (listViewState?.state) {
            LOADING -> renderLoadingState()
            STARGAZERS_LOADED -> renderShowList(listViewState.stargazersList)
            CONTRIBUTORS_LOADED -> renderShowList(listViewState.contributorsList)
        }
    }

    private fun renderShowList(list: List<User>?) {
        adapter.setUsers(list!!)
        hideProgress()
    }

    private fun renderLoadingState() {
        showProgress()
    }

    private fun renderErrorState(throwable: Throwable?) {
        hideProgress()
        Log.e(TAG, "Error ", throwable)
        showDialogMessage("Error ", throwable.toString())
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)
        adapter = UserRecyclerViewAdapter(this, twoPane)
        recyclerView.adapter = adapter
        setupScrollListener()
    }


    private fun setupScrollListener() {
        val layoutManager = repository_list.layoutManager as LinearLayoutManager
        repository_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                vm.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }


    companion object {
        private val TAG = UserListActivity::class.java.simpleName
    }
}
