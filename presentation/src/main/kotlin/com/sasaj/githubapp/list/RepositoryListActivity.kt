package com.sasaj.githubapp.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.arch.lifecycle.ViewModelProviders
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.githubapp.GitHubApplication
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity
import kotlinx.android.synthetic.main.activity_repository_list.*
import kotlinx.android.synthetic.main.repository_list.*
import javax.inject.Inject

class RepositoryListActivity : BaseActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    @Inject
    lateinit var listVMFactory: ListVMFactory

    private lateinit var vm: ListViewModel
    private lateinit var adapter: RepositoryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)

        (application as GitHubApplication).appComponent.inject(this)

        vm = ViewModelProviders.of(this, listVMFactory).get(ListViewModel::class.java)

        vm.listLiveData.observe(this, Observer { mainState -> handleResponse(mainState) })
        vm.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                renderErrorState(it)
            }
        })

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (repository_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(repository_list)

    }

    override fun onResume() {
        super.onResume()
        vm.getAllRepositories()
    }


    private fun handleResponse(listViewState: ListViewState?) {
        when (listViewState?.state) {
            ListViewState.LOADING -> renderLoadingState()
            ListViewState.REPOS_LOADED-> renderShowList(listViewState.repoList)
        }
    }

    private fun renderShowList(list: List<GithubRepository>?) {
        adapter.setRepositories(list!!)
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
        adapter = RepositoryRecyclerViewAdapter(this, twoPane)
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
        private val TAG = RepositoryListActivity::class.java.simpleName
    }
}
