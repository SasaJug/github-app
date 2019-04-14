package com.sasaj.githubapp.common

import android.app.ProgressDialog
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.sasaj.domain.usecases.RequestMoreUseCase

open class BaseActivity : AppCompatActivity() {

    private var userDialog: AlertDialog? = null
    private var waitDialog: ProgressDialog? = null

    fun showProgress(message: String = "Wait") {
        hideProgress()
        waitDialog = ProgressDialog(this)
        waitDialog?.setTitle(message)
        waitDialog?.show()
    }

    fun showDialogMessage(title: String?, body: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(body).setNeutralButton("OK") { dialog, which ->
            try {
                userDialog?.dismiss()
                resetViewModel()
            } catch (e: Exception) {
                //
            }
        }
        userDialog = builder.create()
        userDialog?.show()
    }

    fun hideProgress() {
        try {
            waitDialog?.dismiss()
        } catch (e: Exception) {
            //
        }

    }

    open fun resetViewModel(){}

    companion object {
        const val ARG_USER_TYPE : String = "user_type"
        const val ARG_USER_NAME: String = "user_name"
        const val ARG_REPOSITORY_NAME: String = "repository_name"
        const val ARG_REPOSITORY_HTML_URL: String = "repository_html_url"
        const val ARG_USER_URL: String = "user_url"
        const val CONTRIBUTORS : Int = 1
        const val STARGAZERS : Int = 2
    }
}
