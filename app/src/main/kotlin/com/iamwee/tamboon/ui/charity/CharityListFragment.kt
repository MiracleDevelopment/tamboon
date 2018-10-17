package com.iamwee.tamboon.ui.charity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.iamwee.tamboon.R
import com.iamwee.tamboon.base.BaseFragment
import com.iamwee.tamboon.common.*
import com.iamwee.tamboon.data.Charity
import kotlinx.android.synthetic.main.fragment_charity_list.*

class CharityListFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_charity_list

    private val viewModel by lazy {
        viewModelProvider<CharityViewModel>(CharityViewModelFactory(requireContext())) {
            observe(charities, ::setupCharities)
            observe(failure, ::handleFailure)
        }
    }

    private val adapter by lazy {
        CharityListAdapter {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewCharities.adapter = adapter
        swipeRefreshLayoutCharities.setOnRefreshListener { viewModel.didLoadCharities() }
        viewModel.didLoadCharities()
    }

    private fun setupCharities(charities: List<Charity>?) {
        adapter.submitList(charities)
        swipeRefreshLayoutCharities?.isRefreshing = false
    }

    private fun handleFailure(error: Exception?) {
        Toast.makeText(requireContext(), error?.errorMessage ?: return, Toast.LENGTH_SHORT).show()
    }
}