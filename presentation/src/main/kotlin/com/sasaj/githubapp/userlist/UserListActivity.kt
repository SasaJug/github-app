package com.sasaj.githubapp.userlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.arch.lifecycle.ViewModelProviders
import com.sasaj.domain.entities.GitHubUser
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.domain.usecases.RequestMoreUseCase
import com.sasaj.githubapp.GitHubApplication
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity
import com.sasaj.githubapp.detail.RepositoryDetailFragment
import com.sasaj.githubapp.detail.RepositoryDetailFragment.Companion.ARG_USER_NAME
import com.sasaj.githubapp.list.ListViewModel
import com.sasaj.githubapp.list.ListViewState
import com.sasaj.githubapp.list.RepositoryRecyclerViewAdapter
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

    private var type: Int = 0
    private lateinit var userName: String
    private lateinit var repoName: String

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

        type = intent.getIntExtra(USER_TYPE, 0)
        userName = intent.getStringExtra(USER_NAME)
        repoName = intent.getStringExtra(REPOSITORY_NAME)

        if (repository_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(repository_list)

    }

    override fun onStart() {
        super.onStart()
        if(type == CONTRIBUTORS){
            vm.getRepositoryContributors(userName, repoName)
        } else {
            vm.getRepositoryStargazers(userName, repoName)
        }
    }


    private fun handleResponse(listViewState: UserListViewState?) {
        when (listViewState?.state) {
            LOADING -> renderLoadingState()
            STARGAZERS_LOADED -> renderShowList(listViewState.stargazersList)
            CONTRIBUTORS_LOADED -> renderShowList(listViewState.contributorsList)
        }
    }

    private fun renderShowList(list: List<GitHubUser>?) {
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

                vm.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount, type, userName, repoName)
            }
        })
    }


    companion object {
        private val TAG = UserListActivity::class.java.simpleName
        const val USER_TYPE : String = "user_type"
        const val USER_NAME : String = "user_name"
        const val REPOSITORY_NAME : String = "repo_name"
        const val CONTRIBUTORS : Int = RequestMoreUseCase.CONST_CONTRIBUTOR
        const val STARGAZERS : Int = RequestMoreUseCase.CONST_STARGAZER
    }
}
