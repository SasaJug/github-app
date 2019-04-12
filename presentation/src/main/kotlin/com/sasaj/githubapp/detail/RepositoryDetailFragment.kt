package com.sasaj.githubapp.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sasaj.domain.entities.GithubRepository
import com.sasaj.githubapp.GitHubApplication
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_REPOSITORY_NAME
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_USER_NAME
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_USER_TYPE
import com.sasaj.githubapp.common.BaseActivity.Companion.ARG_USER_URL
import com.sasaj.githubapp.common.BaseActivity.Companion.CONTRIBUTORS
import com.sasaj.githubapp.common.BaseActivity.Companion.STARGAZERS
import com.sasaj.githubapp.userlist.UserListActivity
import kotlinx.android.synthetic.main.repository_detail.*
import javax.inject.Inject

/**
 * A fragment representing a single Repository detail screen.
 * This fragment is either contained in a [RepositoryListActivity]
 * in two-pane mode (on tablets) or a [RepositoryDetailActivity]
 * on handsets.
 */
class RepositoryDetailFragment : Fragment() {

    @Inject
    lateinit var detailVMFactory: DetailVMFactory
    lateinit var detailViewModel: DetailViewModel

    /**
     * The content this fragment is presenting.
     */
    private lateinit var repository: GithubRepository

    private lateinit var userName: String
    private lateinit var repositoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        (activity?.application as GitHubApplication).appComponent.inject(this)

        detailViewModel = ViewModelProviders.of(this, detailVMFactory).get(DetailViewModel::class.java)

        arguments?.let {
            if (it.containsKey(ARG_USER_NAME)) {
                userName = it.getString(ARG_USER_NAME)
            } else {
                throw IllegalArgumentException("ARG_USER_NAME must not be null.")
            }
            if (it.containsKey(ARG_REPOSITORY_NAME)) {
                repositoryName = it.getString(ARG_REPOSITORY_NAME)
            } else {
                throw IllegalArgumentException("ARG_REPOSTIORY_NAME must not be null.")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel.detailLiveData.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        detailViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let { handleError(throwable) }
        })
    }

    override fun onStart() {
        super.onStart()
        detailViewModel.getSingleRepository(userName, repositoryName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_detail, container, false)
    }

    private fun handleViewState(detailsViewState: DetailViewState) {
        if (detailsViewState.state == DetailViewState.LOADING)
            showProgress(true)
        else if (detailsViewState.state == DetailViewState.REPO_LOADED) {
            showProgress(false)
            val repository = detailsViewState.repository
            repository?.let {
                fullname.text = repository.fullName
                description.text = repository.description
                owner.text = repository.ownerName
                size.text = repository.size
                forks.text = repository.forksCount.toString()
                stargazersCount.text = "Stargazers count:  ${repository.stargazersCount}"
                contributorsCount.text = "View Contributors "

                stargazers.setOnClickListener { v ->
                    val intent = Intent(v.context, UserListActivity::class.java).apply {
                        putExtra(ARG_USER_TYPE, STARGAZERS)
                        putExtra(ARG_USER_URL, repository.stargazersUrl)
                    }
                    startActivity(intent)
                }

                contributors.setOnClickListener { v ->
                    val intent = Intent(v.context, UserListActivity::class.java).apply {
                        putExtra(ARG_USER_TYPE, CONTRIBUTORS)
                        putExtra(ARG_USER_URL, repository.contributorsUrl)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun handleError(throwable: Throwable?) {
        showProgress(false)
        renderError(throwable)
    }

    private fun showProgress(show: Boolean) {
        if (show)
            loadingProgress.visibility = View.VISIBLE
        else
            loadingProgress.visibility = View.GONE
    }

    private fun renderError(throwable: Throwable?) {
        (activity as RepositoryDetailActivity).showDialogMessage(throwable?.message, throwable.toString())
    }

    companion object {

    }
}
