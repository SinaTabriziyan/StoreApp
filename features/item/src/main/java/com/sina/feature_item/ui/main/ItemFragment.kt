package com.sina.feature_item.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sina.domain_main.interactor.InteractState
import com.sina.feature_details.R
import com.sina.feature_details.databinding.FragmentItemBinding
import com.sina.model.ui.product_details_item.ProductDetails
import com.sina.ui_components.BaseFragment
import com.sina.ui_components.BaseViewModel
import com.sina.ui_components.BaseViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ItemFragment : BaseFragment<FragmentItemBinding>(FragmentItemBinding::inflate) {
    private val TAG = "ProductFragment"

    private val viewModel: ItemViewModel by viewModels()
    private lateinit var itemAdapter: ImageItemAdapter
    override fun setupViews() {
        with(binding) {
            rvItemImages.adapter = itemAdapter
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
                cancelAnimate()
                false
            }

            Loading -> {
                playAnimate()
                true
            }

            Error -> {
                cancelAnimate()
                false
            }
        }
    }

    private fun showViews(status: Boolean) {
        binding.btnAddToCart.isVisible = status
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        itemAdapter = ImageItemAdapter()
        setupViews()
        observers()
    }


    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDetails.collectLatest {
                    when (it) {
                        is InteractState.Error -> {
                            Timber.d(it.errorMessage)
                            viewModel.uiState.value = Error
                        }

                        is InteractState.Loading -> viewModel.uiState.value = Loading
                        is InteractState.Success -> {
                            viewModel.uiState.value = Success
                            implUi(it.data)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        Success -> showViews(true)
                        Loading -> showViews(false)
                        Error -> showViews(false)
                    }
                    animationStatus(it)
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