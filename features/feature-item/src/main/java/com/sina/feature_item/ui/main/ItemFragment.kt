package com.sina.feature_item.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_details.R
import com.sina.feature_details.databinding.FragmentItemBinding
import com.sina.model.ui.product_details_item.ProductDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item) {
    private val TAG = "ProductFragment"
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var itemAdapter: ImageItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentItemBinding.bind(view)
        val args = arguments
        Log.e(TAG, "onViewCreated:333 ${args?.getInt("productId")}")
        itemAdapter = ImageItemAdapter()
        implRecyclerView()
        observers()
    }

    private fun implRecyclerView() {
        with(binding) {
            rvItemImages.adapter = itemAdapter
        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDetails.collectLatest {
                    when (it) {
                        is InteractState.Error -> {}
                        is InteractState.Loading -> {}
                        is InteractState.Success -> {
                            implUi(it.data)
                            Log.e(TAG, "observers: ${it.data}")
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemAdded.collectLatest {
                    when (it) {
                        true -> {
                            binding.btnAddToCart.text = "برو به سبد خرید"

                        }

                        false -> {
                            binding.btnAddToCart.text = "خرید"

                        }
                    }
                }
            }
        }
    }

    private fun implUi(data: ProductDetails) {
        with(binding) {
            itemAdapter.submitList(data.images)
            tvItemDescription.text = HtmlCompat.fromHtml(data.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvItemPrice.text = data.price
            tvItemTitle.text = data.name
            btnAddToCart.setOnClickListener { viewModel.addItemToCart(data.id) }
        }
    }
}