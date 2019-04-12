package com.sasaj.githubapp.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.sasaj.githubapp.R
import com.sasaj.githubapp.common.BaseActivity
import com.sasaj.githubapp.list.RepositoryListActivity
import kotlinx.android.synthetic.main.activity_repository_detail.*

/**
 * An activity representing a single Repository detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [RepositoryListActivity].
 */

class RepositoryDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)
        setSupportActionBar(detail_toolbar)


        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(ARG_REPOSITORY_NAME)
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = RepositoryDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_NAME,
                            intent.getStringExtra(ARG_USER_NAME))
                    putString(ARG_REPOSITORY_NAME,
                            intent.getStringExtra(ARG_REPOSITORY_NAME))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.repository_detail_container, fragment)
                    .commit()
        }

        fab.setOnClickListener { _ ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(intent.getStringExtra(ARG_REPOSITORY_HTML_URL))
            startActivity(i)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, RepositoryListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
