package com.sina.feature_products.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {
    private val TAG = "ProductsFragment "
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductsBinding.bind(view)
        Log.e(TAG, "onViewCreated: 333")
        observers()
        setDataToUi()
        setPaging()
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.productsByCategory.collectLatest {
//                    when (it) {
//                        is InteractState.Error -> {}
//                        is InteractState.Loading -> {}
//                        is InteractState.Success -> {
////                            binding.message.text = it.data.toString()
//                            productsAdapter.submitList(it.data)
//                        }
//                    }
//                }
                viewModel.products.collectLatest {
                    Log.e(TAG, "observers: $it")
                    productsAdapter.submitList(it.toMutableList())
                }
            }

        }
    }

    private fun setDataToUi() {
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

    private fun setPaging() {
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findLastVisibleItemPosition()
                if (position == productsAdapter.itemCount - 1) {
                    Log.e(TAG, "onScrolled: ")
                    viewModel.nextPage()
                }
            }
        })
    }
}