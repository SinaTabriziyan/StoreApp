package com.sina.feature_search.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sina.feature_search.databinding.FragmentSearchFiltersBinding
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFiltersFragment : BaseFragment<FragmentSearchFiltersBinding>(FragmentSearchFiltersBinding::inflate) {
    private lateinit var searchFilterAdapter: SearchFilterAdapter
    private val viewModel: SearchFilterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun setupViews() {
        searchFilterAdapter = SearchFilterAdapter {

        }
//        searchFilterAdapter.submitList(viewModel.filterList)
//        binding.rvSearchFilers.adapter = searchFilterAdapter
//        binding.rvSearchFilers.layoutManager=LinearLayoutManager(binding.root.context)
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