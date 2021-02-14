package com.example.githubrepos.ui.repository

import com.example.githubrepos.R
import com.example.githubrepos.databinding.ActivityRepositoryBinding
import com.example.githubrepos.ui.base.BaseActivity
import com.example.githubrepos.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepositoryListActivity : BaseActivity<ActivityRepositoryBinding>() {

    private val viewModel: RepositoryViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = viewModel

    override var layoutId: Int = R.layout.activity_repository


//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
//    }
}