package com.sina.feature_search.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.feature_search.R
import com.sina.feature_search.databinding.FragmentSearchBinding
import com.sina.ui_components.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuHost: MenuHost
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchProductsAdapter: SearchProductsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        implMenuToolbar()
        implRecyclerView()
        implObservers()
    }

    private fun implObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsBySearch.collectLatest {
                    searchProductsAdapter.submitList(it)
                }
            }
        }
    }

    private fun implRecyclerView() {
        searchProductsAdapter = SearchProductsAdapter {

        }
        with(binding) {
            rvSearchProducts.apply {
                adapter = searchProductsAdapter
                layoutManager = LinearLayoutManager(root.context)
            }
        }
    }

    private fun implMenuToolbar() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_act_bar_search_fragment, menu)
                val searchItem = menu.findItem(R.id.actionSearch)
                val searchView = searchItem.actionView as SearchView
                searchView.isSubmitButtonEnabled = true
                if (false) {
                    searchView.setQuery("", false)
                }
                searchView.onQueryTextChanged {
                    val searchQuery = "%$it%"
                    viewModel.getProductsBySearch(searchQuery)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}