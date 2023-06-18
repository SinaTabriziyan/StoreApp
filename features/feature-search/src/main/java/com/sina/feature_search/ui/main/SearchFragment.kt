package com.sina.feature_search.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.sina.common.constants.Constants
import com.sina.feature_search.databinding.FragmentSearchBinding
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.Loading
import com.sina.ui_components.BaseViewModel.UiState.Success
import com.sina.ui_components.BaseViewModel.UiState.Error
import com.sina.ui_components.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URLEncoder
import java.util.Locale

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var menuHost: MenuHost
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    private lateinit var navController: NavController
    private var searchOrderTypeChip = Constants.DEFAULT_SEARCH_ORDER_TYPE
    private var searchOrderTypeIdChip = Constants.DEFAULT_SEARCH_ORDER_TYPE_ID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        navController = findNavController()

        implRecyclerView()
        implObservers()
//        setPagination()
        setupViews()
    }

    override fun setupViews() {
        with(binding) {
            toolbar.apply {
                tvSearchFilter.setOnClickListener { navController.navigate(SearchFragmentDirections.actionSearchFragmentToSearchFiltersFragment()) }
                tvSearchOrder.setOnClickListener { navController.navigate(SearchFragmentDirections.actionSearchFragmentToSeachOrdersFragment()) }
                searchView.onQueryTextChanged {
//                    val searchQuery = "%$it%"
                    val encodeQuery = URLEncoder.encode(it, "UTF-8")
                    Timber.d("Query: $it")
                    Timber.d("Query: $encodeQuery")
                    searchAdapter.submitList(emptyList())
                    viewModel.getProductsBySearch(it)
                }
                orderTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->
                    val chip = group.findViewById<Chip>(checkedId)
                    val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                    searchOrderTypeChip = selectedMealType
                    searchOrderTypeIdChip = checkedId
                    viewModel.saveSearchOrderType(searchOrderTypeChip, searchOrderTypeIdChip)
                }
            }

            rvSearchProducts.apply {
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(root.context)
            }
        }
    }

    override fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            Success -> false
            Loading -> true
            Error -> false
        }
    }


    private fun implObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsBySearch.collectLatest {
                    searchAdapter.submitList(emptyList())
                    searchAdapter.submitList(it.toMutableList())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        Success -> animationStatus(it)
                        Loading -> animationStatus(it)
                        Error -> {
                            animationStatus(it)
                            showToast(it.name)

                        }
                    }
                }
            }
        }
    }

    private fun implRecyclerView() {
        searchAdapter = SearchAdapter {}
    }

    private fun setPagination() {
        binding.rvSearchProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == searchAdapter.itemCount - 1) {
                    viewModel.nextPage()
                }
            }
        })
    }
}