package com.sina.feature_search.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.feature_search.databinding.FragmentSearchOrderByBinding
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchOrderByFragment : BaseFragment<FragmentSearchOrderByBinding>(FragmentSearchOrderByBinding::inflate) {
    private lateinit var searchOrderByAdapter: SearchOrderByAdapter
    private val viewModel: SearchOrderByViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun setupViews() {
        searchOrderByAdapter = SearchOrderByAdapter { orderBy ->
            viewModel.saveSearchOrderBy(orderBy)
            findNavController().navigateUp()
        }
        with(binding) {
            searchOrderByAdapter.submitList(viewModel.filterList)
            rvSearchOrders.adapter = searchOrderByAdapter
            rvSearchOrders.layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    override fun playAnimate() {
        TODO("Not yet implemented")
    }

    override fun cancelAnimate() {
        TODO("Not yet implemented")
    }

    override fun animationStatus(state: BaseViewModel.UiState) {

    }
}