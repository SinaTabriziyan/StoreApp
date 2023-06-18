package com.sina.feature_products.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_item.ItemActivity
import com.sina.feature_products.R
import com.sina.feature_products.databinding.FragmentProductsBinding
import com.sina.model.ui.products_item.ProductsItem
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>(FragmentProductsBinding::inflate) {
    override fun setupViews() {
        productsAdapter = ProductsAdapter {
            val intent = Intent(requireActivity(), ItemActivity::class.java)
            intent.putExtra("productId", it)
            startActivity(intent)
        }
        with(binding) {
            rvProducts.apply {
                adapter = productsAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }
        }
    }

    override fun playAnimate() {
        binding.lottie.lottie.playAnimation()
    }

    override fun cancelAnimate() {
        binding.lottie.lottie.cancelAnimation()
    }

    override fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            Success -> {
                cancelAnimate();false
            }

            Loading -> true
            Error -> {
                cancelAnimate();false
            }
        }
    }

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        setupViews()
        setPaging()
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest {
                    productsAdapter.submitList(it.toMutableList())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        Success -> animationStatus(it)
                        Loading -> animationStatus(it)
                        Error -> animationStatus(it)
                    }
                }
            }
        }
    }

    private fun setPaging() {
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findLastVisibleItemPosition()
                if (position == productsAdapter.itemCount - 1) {
                    viewModel.nextPage()
                }
            }
        })
    }
}